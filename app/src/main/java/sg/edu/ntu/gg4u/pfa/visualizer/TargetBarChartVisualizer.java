package sg.edu.ntu.gg4u.pfa.visualizer;

import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidplot.ui.HorizontalPosition;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.TextOrientation;
import com.androidplot.ui.VerticalPosition;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.github.mikephil.charting.charts.BarChart;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

public class TargetBarChartVisualizer {

    public void plot(XYPlot plot, final String[] category, Number[] target, Number[] cost) {

        int n = target.length;

        String[] categories = new String[n+2];
        Number[] currentList = new Number[n+2];
        Number[] predictedList = new Number[n+2];
        Number[] excessList = new Number[n+2];
        currentList[0] = 0;
        currentList[n+1] = 0;
        predictedList[0] = 0;
        predictedList[n+1] = 0;
        excessList[0] = 0;
        excessList[n+1] = 0;
        categories[0] = "";
        categories[n+1] = "";

        for (int i = 0; i < n; i++) {
            if (cost[i].doubleValue() > target[i].doubleValue()) {
                currentList[i + 1] = 0;
                excessList[i + 1] = cost[i];
            }
            else {
                currentList[i + 1] = cost[i];
                excessList[i + 1] = 0;
            }
            predictedList[i+1] = target[i];
            categories[i+1] = category[i];
        }

        XYSeries current = new SimpleXYSeries(Arrays.asList(currentList), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "current");
        XYSeries excess = new SimpleXYSeries(Arrays.asList(excessList), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "excess");
        XYSeries predicted = new SimpleXYSeries(Arrays.asList(predictedList), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "predicted");

        // obtaining max Y value for future calculations
        double maxY = 0;
        for (int j = 0; j < current.size(); j++) {
            if (maxY < current.getY(j).doubleValue())
                maxY = current.getY(j).doubleValue();
            if (maxY < excess.getY(j).doubleValue())
                maxY = excess.getY(j).doubleValue();
            if (maxY < predicted.getY(j).doubleValue())
                maxY = predicted.getY(j).doubleValue();
        }
        // calculating padding on top of tallest bar
        int copy = (int) maxY;
        int count = 0;
        int padding = 1;
        while (copy > 0) {
            copy /= 10;
            count++;
        }
        for (; count > 2; count--)
            padding *= 10;

        // adding bars into the XYplot
        BarFormatter saveFormatter = new BarFormatter(Color.GREEN, Color.BLACK);
        saveFormatter.setPointLabelFormatter(new PointLabelFormatter(Color.WHITE));
        saveFormatter.setPointLabeler(new PointLabeler(){
            @Override
            public String getLabel(XYSeries series, int index) {
                if ((int) series.getY(index) != 0)
                    return String.valueOf(series.getY(index));
                else return "";
            }
        });
        plot.addSeries(current, saveFormatter);

        BarFormatter exFormatter = new BarFormatter(Color.RED, Color.BLACK);
        exFormatter.setPointLabelFormatter(new PointLabelFormatter(Color.WHITE));
        exFormatter.setPointLabeler(new PointLabeler() {
            @Override
            public String getLabel(XYSeries series, int index) {
                if ((int)series.getY(index) != 0)
                    return String.valueOf(series.getY(index));
                else return "";
            }
        });
        plot.addSeries(excess, exFormatter);

        BarFormatter predFormatter = new BarFormatter(Color.BLUE, Color.BLACK);
        predFormatter.setPointLabelFormatter(new PointLabelFormatter(Color.WHITE));
        predFormatter.setPointLabeler(new PointLabeler() {
            @Override
            public String getLabel(XYSeries series, int index) {
                if ((int) series.getY(index) != 0)
                    return String.valueOf(series.getY(index));
                else return "";
            }
        });
        plot.addSeries(predicted, predFormatter);

        // graph modifications
        Paint bg = new Paint();
        bg.setColor(Color.TRANSPARENT);
        plot.setTitle("");
        plot.setDomainLabel("Category");
        plot.setRangeLabel("Expense ($)");
        plot.getLegend().setVisible(false);
        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);
        plot.getGraph().setRangeGridLinePaint(null);
        plot.getGraph().setDomainGridLinePaint(null);
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 100);
        plot.getRenderer(BarRenderer.class).setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(25));
        plot.setRangeUpperBoundary(maxY + padding, BoundaryMode.FIXED);
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setPaint(bg);
        plot.getRangeTitle().setOrientation(TextOrientation.HORIZONTAL);
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setRotation((float)-33.75);
        plot.getRangeTitle().getPositionMetrics().setXPositionMetric(new HorizontalPosition(PixelUtils.dpToPix(10), HorizontalPositioning.ABSOLUTE_FROM_LEFT));
        plot.getRangeTitle().getPositionMetrics().setYPositionMetric(new VerticalPosition(PixelUtils.dpToPix(20), VerticalPositioning.ABSOLUTE_FROM_TOP));
        plot.getDomainTitle().getPositionMetrics().setXPositionMetric(new HorizontalPosition(PixelUtils.dpToPix(60), HorizontalPositioning.ABSOLUTE_FROM_RIGHT));
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new NumberFormat() {
            @NonNull
            @Override
            public StringBuffer format(double number, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
                String labelString = categories[(int) number];
                return new StringBuffer(labelString);
            }

            @NonNull
            @Override
            public StringBuffer format(long number, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
                String labelString = categories[(int) number];
                return new StringBuffer(labelString);
            }

            @Nullable
            @Override
            public Number parse(@NonNull String source, @NonNull ParsePosition parsePosition) {
                return java.util.Arrays.asList(categories).indexOf(source);
            }
        });
    }
}
