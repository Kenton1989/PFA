package sg.edu.ntu.gg4u.pfa.ui.target;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTargetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTargetFragment extends DialogFragment {

    private TargetViewModel mViewModel;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    public EditTargetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(TargetViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_target, container, false);
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