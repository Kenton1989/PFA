package sg.edu.ntu.gg4u.pfa.visualizer;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartVisualizer {


    public void drawPie(PieChart pieChart, String[] label, float[] data)
    {
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        for(int i =0; i < data.length;i++)
            yvalues.add(new PieEntry(data[i], label[i], i));


        PieDataSet dataSet = new PieDataSet(yvalues, "Target");
        PieData pieData = new PieData(dataSet);

        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        //Description description = new Description();
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setHoleRadius(45f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //pieData.setValueTextSize(8f);
        pieData.setDrawValues(false);

        pieData.setValueTextColor(Color.BLACK);
        pieChart.setEntryLabelColor(Color.BLACK);
        // to refresh the chart
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }
}
