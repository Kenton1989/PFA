package sg.edu.ntu.gg4u.pfa.ui.target;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;

public class EditTargetFragment extends DialogFragment {

    private View mView;
    EditText mEdit;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.fragment_edit_target,null);
        builder.setView(mView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mEdit = mView.findViewById(R.id.editTarget);
                        double newValue = Double.parseDouble(mEdit.getText().toString());
                        Log.d("target", String.valueOf(newValue));
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

    private void insertOrUpdateTarget(Target record) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice
    }

    private void deleteTarget(Target record) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice
    }
}