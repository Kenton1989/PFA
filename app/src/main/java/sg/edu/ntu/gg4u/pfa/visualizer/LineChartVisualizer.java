package sg.edu.ntu.gg4u.pfa.visualizer;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LineChartVisualizer {
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12", "13",
            "14", "15", "16","17", "18", "19", "20", "21", "22", "23", "24", "25", "26","27", "28", "29", "30", "31" ));
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
    }
 /*   public class FooFormatter extends ValueFormatter {
        private long referenceTimestamp; // minimum timestamp in your data set
        private DateFormat mDataFormat;
        private Date mDate;

        public FooFormatter(long referenceTimestamp) {
            this.referenceTimestamp = referenceTimestamp;
            this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.mDate = new Date();
        }

        @Override
        public String getFormattedValue(float value) {
            // convertedTimestamp = originalTimestamp - referenceTimestamp
            long convertedTimestamp = (long) value;

            // Retrieve original timestamp
            long originalTimestamp = referenceTimestamp + convertedTimestamp;

            // Convert timestamp to hour:minute
            return getDateString(originalTimestamp);
        }

        private String getDateString(long timestamp) {
            try {
                mDate.setTime(timestamp);
                return mDataFormat.format(mDate);
            } catch(Exception ex) {
                return "xx";
            }
        }
    }*/
}
