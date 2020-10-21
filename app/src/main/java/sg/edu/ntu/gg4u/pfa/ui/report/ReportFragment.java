package sg.edu.ntu.gg4u.pfa.ui.report;

import android.graphics.Color;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.visualizer.LineChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.PieChartVisualizer;

public class ReportFragment extends Fragment {



    private ReportViewModel reportViewModel;
    //LineChart lineChart;
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
      // lineChart = (LineChart) root.findViewById(R.id.chart);


       /* LineChartVisualizer lcv = new LineChartVisualizer();

        float[] tempData = new float[5];

        tempData[0] = 100;
        tempData[1] = 200;
        tempData[2] = 400;
        tempData[3] = 100;
        tempData[4] = 200;


        lcv.createLine(lineChart, tempData, "temp chart");*/

       /* PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart);

        PieChartVisualizer pcv = new PieChartVisualizer();

        String[] labels = new String[5];
        float[] data = new float[5];

        labels[0] = "January";
        labels[1] = "February";
        labels[2] = "March";
        labels[3] = "April";
        labels[4] = "May";

        data[0] = 8f;
        data[1] = 15f;
        data[2] = 12f;
        data[3] = 25f;
        data[4] = 23f;

        pcv.drawPie(pieChart, labels, data);*/

        return root;
    }


   /* public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        //final TextView textView = root.findViewById(R.id.text_report);
        reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
          //      textView.setText(s);
            }
        });

        ImageButton dec, inc;

        final TextView month = root.findViewById(R.id.month);
        final Calendar cal= Calendar.getInstance();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy");
        String selectedMonth=month_date.format(cal.getTime());
        month.setText(selectedMonth);

        dec = root.findViewById(R.id.left_arrow);
        inc = root.findViewById(R.id.right_arrow);

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, -1);
                String selectedMonth=month_date.format(cal.getTime());
                month.setText(selectedMonth);
            }
        });

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                String selectedMonth=month_date.format(cal.getTime());
                month.setText(selectedMonth);

            }
        });




        return root;
    }*/
}