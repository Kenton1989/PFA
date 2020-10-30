package sg.edu.ntu.gg4u.pfa.ui.report;

import android.graphics.Color;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;
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
import java.util.*;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.LocalDateTimeConverter;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.record.CustomList;
import sg.edu.ntu.gg4u.pfa.ui.record.RecordViewModel;
import sg.edu.ntu.gg4u.pfa.visualizer.LineChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.PieChartVisualizer;

public class ReportFragment extends Fragment {


    ListView list;
    CustomListReport adapter;

    List<String> cat_in_list = new ArrayList<>();
    List<String> percent_in_list = new ArrayList<>();
    List<String> sugg_in_list = new ArrayList<>();
    List<Double> sum_in_cat =new ArrayList<>();
/*
    String[] cat_in_list = {
            "Food",
            "Transportation",
            "Leisure",
            "Entertainment"
    };

    String[] percent_in_list = {
            "50",
            "50",
            "50",
            "50"
    };

    String[] sugg_in_list = {
            "10",
            "10",
            "10",
            "10"
    };

    private ReportViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    LineChartVisualizer lcv;

    //LineChart lineChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_report, container, false);




        final LineChart lineChart = (LineChart) root.findViewById(R.id.chart);


        lcv = new LineChartVisualizer();

        final float[] tempData = new float[5];

        tempData[0] = 100;
        tempData[1] = 200;
        tempData[2] = 400;
        tempData[3] = 100;
        tempData[4] = 200;

        final float[] tempData2 = new float[5];

        tempData2[0] = 600;
        tempData2[1] = 100;
        tempData2[2] = 500;
        tempData2[3] = 300;
        tempData2[4] = 800;


        lcv.createLine(lineChart, tempData, "temp chart");

  
       final PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart);


        final PieChartVisualizer pcv = new PieChartVisualizer();

        final String[] labels = new String[5];
        final float[] data = new float[5];

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

        final String[] labels2 = new String[5];
        final float[] data2 = new float[5];

        labels2[0] = "March";
        labels2[1] = "January";
        labels2[2] = "May";
        labels2[3] = "December";
        labels2[4] = "September";

        data2[0] = 18f;
        data2[1] = 85f;
        data2[2] = 62f;
        data2[3] = 35f;
        data2[4] = 83f;

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
                //to re-insert then add the data into the charts again
                lcv.createLine(lineChart, tempData, "temp chart");
                pcv.drawPie(pieChart, labels, data);
            }
        });
        cal.getTime();

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                String selectedMonth = month_date.format(cal.getTime());
                month.setText(selectedMonth);
                //to re-insert then add the data into the charts again
                lcv.createLine(lineChart, tempData2, "temp chart");
                pcv.drawPie(pieChart, labels2, data2);
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
                Toast.makeText(getActivity(), "You Clicked at " + cat_in_list.get(+position), Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = Injection.provideViewModelFactory(this.getActivity());
        mViewModel = new ViewModelProvider(this, factory)
                .get(ReportViewModel.class);
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


        // TODO: DB group: call this function when data changes
    }

    void whenMonthlyCostSumUpdated(List<SumByCategory> newMonthlyCost) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        

            // TODO: DB group: call this function when data changes
    }

    void whenRecordListUpdated(List<Record> newRecords) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function


        // TODO: DB group: call this function when data changes
    }

    void whenUserProfileChanged(UserProfile newProfile) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        newProfile.getName();
        newProfile.getGender();
        newProfile.getJobField();
        newProfile.getIncome();
        newProfile.getFamilySize();

        // TODO: DB group: call this function when data changes
    }
}