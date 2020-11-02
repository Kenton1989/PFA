package sg.edu.ntu.gg4u.pfa.ui.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.CompositeDisposable;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.target.EditTargetFragment;

public class EditRecordFragment extends DialogFragment {

    private View mView;
    EditText mEdit;
    Spinner mSpin;
    TextView mText;
    Record oldRecord;
    int spinnerPosition;

    private RecordViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public EditRecordFragment(Record oldRecord) {
        this.oldRecord = oldRecord;
        this.spinnerPosition = 0;
    }

    public EditRecordFragment() {
        this.oldRecord = null;
        this.spinnerPosition = 0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.fragment_edit_record,null);
        mEdit = mView.findViewById(R.id.editRecord);
        if (oldRecord != null) {
            mEdit.setText(String.valueOf(oldRecord.getAmount()));
        }
        else {
            mText = mView.findViewById(R.id.editRecordTitle);
            mText.setText("   Add Record");
        }
        mSpin = (Spinner) mView.findViewById(R.id.targetSpinner);
        mSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        builder.setView(mView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String catName = mSpin.getSelectedItem().toString();
                        double newValue = Double.parseDouble(mEdit.getText().toString());
                        if (newValue == 0) {
                            if (oldRecord == null) {}
                            else {
                                deleteRecord(oldRecord);
                            }
                        }
                        else {
                            if (oldRecord == null) {
                                oldRecord = new Record(catName, newValue);
                            } else {
                                oldRecord.setAmount(newValue);
                                oldRecord.setCategoryName(catName);
                            }
                            insertOrUpdateRecord(oldRecord);
                        }
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = Injection.provideViewModelFactory(this.getActivity());
        mViewModel = new ViewModelProvider(this, factory)
                .get(RecordViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenCategoryUpdated));
    }

    private void whenCategoryUpdated(List<Category> newCategoryList) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        List<String> categoryNameList = new ArrayList<>();
        for (int i = 0; i < newCategoryList.size(); i++) {
            categoryNameList.add(newCategoryList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mView.getContext(), android.R.layout.simple_spinner_item, categoryNameList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpin.setAdapter(adapter);
        if (oldRecord != null) {
            mSpin.setSelection(categoryNameList.indexOf(oldRecord.getCategoryName()));
        }
    }

    private void insertOrUpdateRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        mDisposable.add(mViewModel.addRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private void deleteRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
        mDisposable.add(mViewModel.deleteRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}