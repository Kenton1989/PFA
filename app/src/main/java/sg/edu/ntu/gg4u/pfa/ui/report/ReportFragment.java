package sg.edu.ntu.gg4u.pfa.ui.report;

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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import sg.edu.ntu.gg4u.pfa.R;

public class ReportFragment extends Fragment {

    private ReportViewModel reportViewModel;
    LineChart lineChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // //reportViewModel =
      //          ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
     //   final TextView textView = root.findViewById(R.id.text_report);
      //  reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
      //      @Override
       //     public void onChanged(@Nullable String s) {
       //         textView.setText(s);
      //      }
     //   });
        lineChart = (LineChart) root.findViewById(R.id.chart);
        PieChart pieChart = root.findViewById(R.id.pieChart);

        lineChart.setData(dataValues());

        drawChart(pieChart);

        return root;
    }

    private LineData dataValues(){
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 800));

        LineDataSet set1 = new LineDataSet(values, "Data set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        return data;
    }

    private void drawChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "January", 0));
        yvalues.add(new PieEntry(15f, "February", 1));
        yvalues.add(new PieEntry(12f, "March", 2));
        yvalues.add(new PieEntry(25f, "April", 3));
        yvalues.add(new PieEntry(23f, "May", 4));

        PieDataSet dataSet = new PieDataSet(yvalues, "Pie Chart");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
    }
}