package sg.edu.ntu.gg4u.pfa.ui.target;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.MainActivity;

public class TargetFragment extends Fragment {


    ListView list;
    String[] targetCat_in_list = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others",
            "Vacation",
            "Transportation",
            "Others"
    } ;

    String[] targetAmt_in_List = {
            "1000",
            "2000",
            "5000",
            "6000",
            "7000",
            "8000",
            "6000",
            "7000"
    } ;

    String[] actualAmt_in_List= {
            "100",
            "200",
            "500",
            "600",
            "700",
            "800",
            "600",
            "700"


    };

    private TargetViewModel targetViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        targetViewModel =
                ViewModelProviders.of(this).get(TargetViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_target, container, false);
        //final TextView textView = root.findViewById(R.id.actualAmount);
        final View root = inflater.inflate(R.layout.fragment_target, container, false);
        targetViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
                CustomListTarget adapter = new
                        CustomListTarget(getActivity(),  targetCat_in_list , targetAmt_in_List, actualAmt_in_List);
                list=root.findViewById(R.id.listViewTarget);
                list.setAdapter(adapter);
            }
        });
        return root;
    }
}