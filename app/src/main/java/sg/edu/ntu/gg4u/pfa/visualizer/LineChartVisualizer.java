package sg.edu.ntu.gg4u.pfa.visualizer;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

public class LineChartVisualizer {

    public void createLine(LineChart lineChart, float[] data, String chartName){

        lineChart.getDescription().setEnabled(false);

        float[] currentData = data;
        ArrayList<Entry> values = new ArrayList<>();

        for(int i =0; i < currentData.length;i++)
            values.add(new Entry(i, currentData[i]));


        LineDataSet set1 = new LineDataSet(values, chartName);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1);
        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
    }
}
