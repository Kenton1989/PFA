package sg.edu.ntu.gg4u.pfa.ui.report;

import android.graphics.Color;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.record.CustomList;
import sg.edu.ntu.gg4u.pfa.visualizer.LineChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.PieChartVisualizer;

public class ReportFragment extends Fragment {


    ListView list;

    String[] cat_in_list = {
            "Food",
            "Transportation",
            "Leisure",
            "Entertainment"
    };

    String[] percent_in_list = {
            " ",
            " ",
            " ",
            " "
    };

    String[] sugg_in_list = {
            " ",
            " ",
            " ",
            " "
    };

    private ReportViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();


    //LineChart lineChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_report, container, false);


        LineChart lineChart = (LineChart) root.findViewById(R.id.chart);


        LineChartVisualizer lcv = new LineChartVisualizer();

        float[] tempData = new float[5];

        tempData[0] = 100;
        tempData[1] = 200;
        tempData[2] = 400;
        tempData[3] = 100;
        tempData[4] = 200;


        lcv.createLine(lineChart, tempData, "temp chart");

        PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart);

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

        pcv.drawPie(pieChart, labels, data);

        ImageButton dec, inc;

        final TextView month = root.findViewById(R.id.report_month);
        final Calendar cal = Calendar.getInstance();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy");
        String selectedMonth = month_date.format(cal.getTime());
        month.setText(selectedMonth);

        dec = root.findViewById(R.id.left_arrow);
        inc = root.findViewById(R.id.right_arrow);

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, -1);
                String selectedMonth = month_date.format(cal.getTime());
                month.setText(selectedMonth);
            }
        });
        cal.getTime();

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                String selectedMonth = month_date.format(cal.getTime());
                month.setText(selectedMonth);

            }
        });


        CustomListReport adapter = new
                CustomListReport(getActivity(), cat_in_list, percent_in_list, sugg_in_list);
        list = root.findViewById(R.id.report_listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + cat_in_list[+position], Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        // TODO: UI group: put default calendar here
        Calendar calendar = Calendar.getInstance();
        resetMonth(calendar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void resetMonth(Calendar calendar) {
        // TODO: UI group: 1. implement this function, update the UI related to date
        //                 2. use this function when month range need to change



        // TODO: DB group: implement this function
        //                 re-select the data from the database

        mDisposable.clear();

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        localDateTime = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

        mDisposable.add(mViewModel.getAllCurrentTarget(localDateTime.toLocalDate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenTargetOfThisMonthUpdated));

        mDisposable.add(mViewModel.getRecord(localDateTime, localDateTime.plusMonths(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenRecordListUpdated));

        mDisposable.add(mViewModel.getGroupedRecordSum(localDateTime, localDateTime.plusMonths(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenMonthlyCostSumUpdated));
    }

    void whenTargetOfThisMonthUpdated(List<Target> newTargets) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        // TODO: DB group: use this function
    }

    void whenMonthlyCostSumUpdated(List<SumByCategory> newMonthlyCost) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        // TODO: DB group: use this function
    }

    void whenRecordListUpdated(List<Record> newRecord) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        // TODO: DB group: use this function
    }


}