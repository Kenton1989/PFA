package sg.edu.ntu.gg4u.pfa.ui.target;

import android.graphics.Color;
import android.nfc.Tag;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import sg.edu.ntu.gg4u.pfa.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.edu.ntu.gg4u.pfa.MainActivity;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

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
            "Otvhers"
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

    private TargetViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(TargetViewModel.class);

        //View root = inflater.inflate(R.layout.fragment_target, container, false);git
        //final TextView textView = root.findViewById(R.id.actualAmount);
        final View root = inflater.inflate(R.layout.fragment_target, container, false);
        CustomListTarget adapter = new
                CustomListTarget(getActivity(),  targetCat_in_list , targetAmt_in_List, actualAmt_in_List);
        list=root.findViewById(R.id.listViewTarget);
        list.setAdapter(adapter);
        return root;
    }

    private void resetMonth(Calendar calendar) {


        // If the month is changed
        // TODO: UI group: 1. implement this function, update the UI related to date
        //                 2. use this function when month range need to change



        // TODO: DB group: implement this function
        //                 re-select the data from the database

    }

    public void whenDataChanged(List<Target> newTargetList, List<Double> newMonthlyCost) {
        // One target maps to one monthly cost
        // TODO: UI group: implement this function
        // TODO: DB group: use this function when data changes
    }


    private void insertOrUpdateTarget(Target record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }

    private void deleteTarget(Target record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }

}