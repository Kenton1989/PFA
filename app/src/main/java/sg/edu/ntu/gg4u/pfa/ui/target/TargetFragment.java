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
import android.widget.ImageButton;
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

import com.androidplot.xy.XYPlot;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.edu.ntu.gg4u.pfa.MainActivity;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;
import sg.edu.ntu.gg4u.pfa.visualizer.LineChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.PieChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.TargetBarChartVisualizer;

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

    private TargetViewModel targetViewModel;

    private TargetViewModel mViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(TargetViewModel.class);

        //View root = inflater.inflate(R.layout.fragment_target, container, false);
        //final TextView textView = root.findViewById(R.id.actualAmount);
        final View root = inflater.inflate(R.layout.fragment_target, container, false);
        CustomListTarget adapter = new
                CustomListTarget(getActivity(),  targetCat_in_list , targetAmt_in_List, actualAmt_in_List);
        list=root.findViewById(R.id.listViewTarget);
        list.setAdapter(adapter);

        ImageButton decT, incT;
        final TextView month = root.findViewById(R.id.target_month);
        final Calendar calT = Calendar.getInstance();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy");
        String selectedMonth = month_date.format(calT.getTime());
        month.setText(selectedMonth);

        decT = root.findViewById(R.id.left_arrow_target);
        incT = root.findViewById(R.id.right_arrow_target);

        decT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calT.add(Calendar.MONTH, -1);
                String selectedMonth = month_date.format(calT.getTime());
                month.setText(selectedMonth);
            }
        });
        calT.getTime();

        incT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calT.add(Calendar.MONTH, 1);
                String selectedMonth = month_date.format(calT.getTime());
                month.setText(selectedMonth);

            }
        });



        XYPlot barChart = (XYPlot) root.findViewById(R.id.barChart);
        TargetBarChartVisualizer bar = new TargetBarChartVisualizer();

        String[] cat = new String[5];

        cat[0] = "Food";
        cat[1] = "Transportation";
        cat[2] = "Leisure";
        cat[3] = "Travel";
        cat[4] = "Others";

        Number[] target = new Number[5];
        target[0] = 500 ;
        target[1] = 200;
        target[2] = 400;
        target[3] = 1000;
        target[4] = 200;


        Number[] cost = new Number[5];
        cost[0] = 200 ;
        cost[1] = 300;
        cost[2] = 100;
        cost[3] = 100;
        cost[4] = 55;

        bar.plot(barChart, cat,target,cost);














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



}