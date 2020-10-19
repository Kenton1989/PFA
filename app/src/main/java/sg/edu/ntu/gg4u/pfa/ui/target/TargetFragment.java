package sg.edu.ntu.gg4u.pfa.ui.target;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class TargetFragment extends Fragment {

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
        //targetViewModel =
       //         ViewModelProviders.of(this).get(TargetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_target, container, false);
      //  final TextView textView = root.findViewById(R.id.text_target);
   //     targetViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
     //       @Override
       //     public void onChanged(@Nullable String s) {
      //          textView.setText(s);
       //     }
       // });
        stackedChart = (BarChart) root.findViewById(R.id.stacked_Barchart);

        //test
        target.add(30);
        target.add(20);
        target.add(50);
        current.add(9);
        current.add(25);
        current.add(38);


        for (int i=0;i<target.size();i++)
        {
            barDatasets.add(new BarDataSet(dataValues1(target.get(i), current.get(i), 2*i),  "Test"));

            if(target.get(i) >current.get(i)) //if current is lesser than target than green blue
                barDatasets.get(i).setColors(currentOverColor);
            else // else red blue
                barDatasets.get(i).setColors(targetOverColor);
            bars.add(barDatasets.get(i));
        }
       // BarDataSet barDataSet = new BarDataSet(dataValues1(target[0], current[0], 0),  "BarDataSet");
        //barDataSet.setColors(currentOverColor);
        BarData data = new BarData(bars);
        data.setBarWidth(1f); // set custom bar width
      //  data.groupBars(0, 0.06f, 0);
       // stackedChart.getLegend().setEnabled(false);
        stackedChart.setData(data);
        stackedChart.setFitBars(true);
        stackedChart.setTouchEnabled(false);

        XAxis xAxis = stackedChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        final String[] labels = new String[] {"Food", "Transport", "Utilities"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        return root;
    }

    private ArrayList<BarEntry> dataValues1(int target, int current, int pos)
    {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
      /*  dataVals.add(new BarEntry(0, new float[]{2,5.5f}));
        dataVals.add(new BarEntry(1, new float[]{2,8f}));
        dataVals.add(new BarEntry(2, new float[]{2,3}));
        dataVals.add(new BarEntry(0, 300));
        dataVals.add(new BarEntry(0, 28f));
        dataVals.add(new BarEntry(1, 450));
        dataVals.add(new BarEntry(1, 57f));*/
        if(target > current)
        {
            dataVals.add(new BarEntry(pos, target));
            dataVals.add(new BarEntry(pos, current));
        }
        else {
            dataVals.add(new BarEntry(pos, current));
            dataVals.add(new BarEntry(pos, target));
        }
        return dataVals;
    }
}