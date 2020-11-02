package sg.edu.ntu.gg4u.pfa.ui.report;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.*;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.ValueComparator;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.visualizer.ExpenditureLineChart;
import sg.edu.ntu.gg4u.pfa.visualizer.LineChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.PieChartVisualizer;
import sg.edu.ntu.gg4u.pfa.persistence.Predictor;

public class ReportFragment extends Fragment {
    private static final String TAG = ReportFragment.class.getSimpleName();

    ListView list;

    List<Record> recordList;
    List<String> cat_in_list = new ArrayList<>();
    List<String> dates_in_list = new ArrayList<>();
    List<String> amount_in_list = new ArrayList<>();
    List<String> t_cat_in_list = new ArrayList<>();
    List<String> t_amount_in_list = new ArrayList<>();
    List<String> percent_in_list = new ArrayList<>();
    List<String> sugg_in_list = new ArrayList<>();
    List<Double> sum_in_cat = new ArrayList<>();

    // List<String> scat_in_list = Arrays.asList("Food" , "Transportation" , "Leisure" , "Entertainment");
    // List<String> spercent_in_list = Arrays.asList("10" , "20" , "20" , "10");
    // List<String> ssugg_in_list = Arrays.asList("-10" , "-10" , "-10" , "-12");

    /*

     */

    private ReportViewModel mViewModel;

    PieChart pieChart;

    LineChart lineChart;
    LineChart ExpChart;
    ExpenditureLineChart ecv;

    private Activity activity;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    //LineChart lineChart;
    @RequiresApi(api = Build.VERSION_CODES.R)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_report, container, false);

        //to find the chart
        lineChart = (LineChart) root.findViewById(R.id.chart);

        pieChart = (PieChart) root.findViewById(R.id.pieChart);

        ExpChart = (LineChart) root.findViewById(R.id.Expchart);


        ImageButton dec, inc;

        final TextView month = root.findViewById(R.id.report_month);
        final Calendar cal = Calendar.getInstance();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy");
        String selectedMonth = month_date.format(cal.getTime());
        month.setText(selectedMonth);

        dec = root.findViewById(R.id.left_arrow);
        inc = root.findViewById(R.id.right_arrow);

        dec.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, -1);
                String selectedMonth = month_date.format(cal.getTime());
                month.setText(selectedMonth);
                resetMonth(cal);

                //to re-insert then add the data into the charts again
                //lcv.createLine(lineChart, tempData, "temp chart");
                //pcv.drawPie(pieChart, labels, data);
            }
        });
        cal.getTime();

        inc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                String selectedMonth = month_date.format(cal.getTime());
                month.setText(selectedMonth);
                resetMonth(cal);
                //to re-insert then add the data into the charts again
                //lcv.createLine(lineChart, tempData2, "temp chart");
                //pcv.drawPie(pieChart, labels2, data2);
            }
        });

        list = root.findViewById(R.id.report_listView);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
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

        mDisposable.add(mViewModel.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenUserProfileChanged));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void resetMonth(Calendar calendar) {
        // TODO: UI group: 1. implement this function, update the UI related to date
        //                 2. use this function when month range need to change


        lineChart.clear();
        pieChart.clear();
        lineChart.refreshDrawableState();
        pieChart.refreshDrawableState();
        cat_in_list.clear();;
        dates_in_list.clear();
        amount_in_list.clear();
        percent_in_list.clear();
        sugg_in_list.clear();
        sum_in_cat.clear();

        // TODO: DB group: implement this function
        //                 re-select the data from the database

        mDisposable.clear();

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        localDateTime = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

     /*   mDisposable.add(mViewModel.getAllCurrentTarget(localDateTime.toLocalDate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenTargetOfThisMonthUpdated));*/

        mDisposable.add(mViewModel.getRecord(localDateTime, localDateTime.plusMonths(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenRecordListUpdated));

        mDisposable.add(mViewModel.getGroupedRecordSum(localDateTime, localDateTime.plusMonths(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenMonthlyCostSumUpdated));


    }

 /*   void whenTargetOfThisMonthUpdated(List<Target> newTargets) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        for(Target newT:newTargets){
            t_cat_in_list.add(newT.getCategoryName());
            t_amount_in_list.add(String.valueOf(newT.getAmount()));
        }

        CustomListReport adapter = new
                CustomListReport(activity, t_cat_in_list , t_amount_in_list);
        list.setAdapter(adapter);

        // TODO: DB group: call this function when data changes
    }*/

    void whenMonthlyCostSumUpdated(List<SumByCategory> newMonthlyCost) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        boolean existData = false;
        for (SumByCategory catSum : newMonthlyCost) {
            existData = existData || catSum.sum > 0;
            sum_in_cat.add(catSum.sum);
            cat_in_list.add(catSum.categoryName);
        }

        double[] sum_in_cat_array = new double[sum_in_cat.size()];
        for (int i = 0; i < sum_in_cat.size(); i++) {
            sum_in_cat_array[i] = sum_in_cat.get(i);
        }

        float[] sum_in_cat_float = new float[sum_in_cat_array.length];
        int j = 0;
        for (double value : sum_in_cat_array) {
            sum_in_cat_float[j++] = (float) value;
        }

        String [] cat_and_total = new String[sum_in_cat_array.length];
        for (int k = 0 ; k < sum_in_cat_array.length ; k++ )
        {
            cat_and_total[k] = cat_in_list.get(k) + "   " + "$" + sum_in_cat_float[k];
        }


        pieChart.clear();

        if (existData) {
            PieChartVisualizer pcv = new PieChartVisualizer();
            pcv.drawPie(pieChart, cat_and_total, sum_in_cat_float);
        }


        // TODO: DB group: call this function when data changes
    }

    void whenRecordListUpdated(List<Record> newRecords) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
//        recordList = newRecords;
//
//        for (Record recordObj : newRecords) {
////            Log.d(recordObj.toString(), "test");
//            String str_date = (String.valueOf(recordObj.timestamp).substring(0, 10));
//            dates_in_list.add(str_date);
//            cat_in_list.add(recordObj.categoryName);
//            amount_in_list.add(String.valueOf(recordObj.amount));
//        }
//
        if (newRecords.isEmpty()) {
            lineChart.clear();
            lineChart.notifyDataSetChanged();
            return;
        }

        LocalDate begDate = newRecords.get(0).getTimestamp().toLocalDate().withDayOfMonth(1);
        LocalDate endDate = (LocalDate) min(LocalDate.now(), begDate.plusMonths(1).minusDays(1));
        endDate = (LocalDate) max(endDate, newRecords.get(0).getTimestamp().toLocalDate());
        int totalDisplayedDays = (int) begDate.until(endDate, ChronoUnit.DAYS) + 1;
        
        Log.d(TAG, "whenRecordListUpdated: begDate="+begDate);
        Log.d(TAG, "whenRecordListUpdated: endDate="+endDate);
        Log.d(TAG, "whenRecordListUpdated: Current Month has "+totalDisplayedDays+" days.");
        float[] amount_in_list_float = new float[totalDisplayedDays];

        for (Record record: newRecords) {
            Log.d(TAG, "whenRecordListUpdated: record date"+record.getTimestamp());
            int dayInMonth = record.getTimestamp().toLocalDate().getDayOfMonth();
            amount_in_list_float[dayInMonth-1] += record.getAmount();
        }

        LineChartVisualizer lcv = new LineChartVisualizer();
        lineChart.clear();
        lcv.createLine(lineChart, amount_in_list_float, "daily expenditure");

/*
        CustomList adapter = new
                CustomList(activity, cat_in_list, percent_in_list, sugg_in_list);;
        list.setAdapter(adapter);
*/
        // TODO: DB group: call this function when data changes
    }

    private <T extends Comparable<T>> T min(T obj1, T obj2) {
        if (obj2.compareTo(obj1) < 0)
            return obj2;
        return obj1;
    }

    private <T extends Comparable<T>> T max(T obj1, T obj2) {
        if (obj2.compareTo(obj1) > 0)
            return obj2;
        return obj1;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    void whenUserProfileChanged(UserProfile newProfile) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        Predictor p = new Predictor(activity);
        UserProfile up = newProfile;

        // Get Prediction Result
        HashMap<String, Double> categoryPrediction = p.predictDistributionByCategory(up);
        HashMap<String, Double> expenditurePrediction = p.predictDistributionByIncomeGroup(up);

        //Create a treemap for sorted list of distribution
        ValueComparator vc = new ValueComparator(expenditurePrediction);
        TreeMap<String, Double> sortedExpediture = new TreeMap<String, Double>(vc);
        sortedExpediture.putAll(expenditurePrediction);
        //to store all the values for prediction
        ArrayList<Double> prediction = new ArrayList<Double>(p.getValue(sortedExpediture));

        // update expenditure chart
        ecv = new ExpenditureLineChart();
        ExpChart.clear();
        ExpChart.refreshDrawableState();
        ecv.createLine(ExpChart, prediction, "temp chart");

        // to store all the category prediction
        for (Map.Entry<String, Double> entry : categoryPrediction.entrySet()) {
            t_cat_in_list.add(entry.getKey());

            t_amount_in_list.add(entry.getValue().toString());
        }

        //set the data for category prediction
        CustomListReport adapter = new
                CustomListReport(activity, t_cat_in_list, t_amount_in_list);
        list.setAdapter(adapter);
        // TODO: DB group: call this function when data changes
    }
}