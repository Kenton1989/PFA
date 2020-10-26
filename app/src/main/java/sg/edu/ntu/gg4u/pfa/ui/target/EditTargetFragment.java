package sg.edu.ntu.gg4u.pfa.ui.target;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTargetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTargetFragment extends DialogFragment {

    public EditTargetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_target, container, false);
    }


    private void insertOrUpdateTarget(Target record) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice
    }

    private void deleteTarget(Target record) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice
    }
}