package sg.edu.ntu.gg4u.pfa.ui.target;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

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
    BarChart stackedChart;
    int[] currentOverColor = new int[]{Color.BLUE, Color.GREEN};
    int[] targetOverColor = new int[]{Color.RED, Color.BLUE};

    //test
    ArrayList<Integer> target = new ArrayList<>();
    ArrayList<Integer> current = new ArrayList<>();

    List<IBarDataSet> bars = new ArrayList<IBarDataSet>();
    List<BarDataSet> barDatasets = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        targetViewModel =
                ViewModelProviders.of(this).get(TargetViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_target, container, false);
        //final TextView textView = root.findViewById(R.id.actualAmount);
        final View root = inflater.inflate(R.layout.fragment_target, container, false);
        CustomListTarget adapter = new
                CustomListTarget(getActivity(),  targetCat_in_list , targetAmt_in_List, actualAmt_in_List);
        list=root.findViewById(R.id.listViewTarget);
        list.setAdapter(adapter);
        return root;
    }

}