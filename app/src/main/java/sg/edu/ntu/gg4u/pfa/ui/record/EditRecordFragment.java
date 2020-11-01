package sg.edu.ntu.gg4u.pfa.ui.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.ui.target.EditTargetFragment;

public class EditRecordFragment extends DialogFragment {

    private View mView;
    EditText mEdit;
    Spinner mSpin;
    int position;
    List<String> catList;
    String amount;

    public EditRecordFragment(List<String> catList, int position, String amount) {
        this.amount = amount;
        this.position = position;
        this.catList = catList;
    }

    public EditRecordFragment(List<String> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.fragment_edit_record,null);
        mEdit = mView.findViewById(R.id.editRecord);
        mEdit.setText(amount);
        mSpin = (Spinner) mView.findViewById(R.id.targetSpinner);
        ArrayAdapter adapter = new ArrayAdapter(mView.getContext(), android.R.layout.simple_spinner_item, catList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpin.setAdapter(adapter);
        if (position != 0) {
            mSpin.setSelection(position);
        }
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
                        insertOrUpdateRecord(new Record(catName, newValue));
                        Log.d("record", String.valueOf(newValue));
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

    private void insertOrUpdateRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice
    }

    private void deleteRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: DO NOT implement this function until further notice
    }
}