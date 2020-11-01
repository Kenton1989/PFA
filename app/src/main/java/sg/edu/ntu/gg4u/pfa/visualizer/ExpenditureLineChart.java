package sg.edu.ntu.gg4u.pfa.visualizer;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExpenditureLineChart {

    List<String> xAxisValues = new ArrayList<>(Arrays.asList("0 - 1k", "1k - 1.9k", "2k - 2.9k", "3k - 3.9k", "4k - 4.9k", "5k - 5.9k","6k - 7.9k", "8k - 9.9k", "10k - 11.9k", "12k - 14.9k", ">=15,000"));
    public void createLine(LineChart lineChart,ArrayList<Double> currentData, String chartName){

        lineChart.getDescription().setEnabled(false);


        ArrayList<Entry> values = new ArrayList<>();

        for(int i =0; i < currentData.size();i++)
            values.add(new Entry(i, currentData.get(i).floatValue()));


        LineDataSet set1 = new LineDataSet(values, chartName);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1);
        LineData lineData = new LineData(dataSets);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        // xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        lineChart.getLegend().setEnabled(false);

        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
        // Date date = new Date();

        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        lineChart.setData(lineData);
        // to refresh the chart
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
}
