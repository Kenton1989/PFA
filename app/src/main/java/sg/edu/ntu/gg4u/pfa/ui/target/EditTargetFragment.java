package sg.edu.ntu.gg4u.pfa.ui.target;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;

public class EditTargetFragment extends DialogFragment {

    private TargetViewModel mViewModel;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    
    private View mView;
    private EditText mEdit;
    private TextView mText;
    private String catName;

    public EditTargetFragment(String catName) {
        this.catName = catName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(TargetViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.fragment_edit_target, null);
        mEdit = mView.findViewById(R.id.editTarget);
        mText = mView.findViewById(R.id.editTargetCat);
        mText.setText("Please enter a target for " + catName + ":");
        builder.setView(mView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        double newValue = Double.parseDouble(mEdit.getText().toString());
                        Log.d("target", String.valueOf(newValue));
                        insertOrUpdateTarget(new Target(catName, newValue));
                        Toast.makeText(getActivity(), "Target updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private void insertOrUpdateTarget(Target target) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice

        mDisposable.add(mViewModel.insertOrUpdateTarget(target)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private void deleteTarget(Target target) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice

        mDisposable.add(mViewModel.deleteTarget(target)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}