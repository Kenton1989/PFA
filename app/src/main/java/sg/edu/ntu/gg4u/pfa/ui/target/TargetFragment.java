package sg.edu.ntu.gg4u.pfa.ui.target;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;


import com.androidplot.xy.XYPlot;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.edu.ntu.gg4u.pfa.MainActivity;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.Target.TargetDao;
import sg.edu.ntu.gg4u.pfa.persistence.Target.TargetDao.TargetAndCost;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.home.CustomListHome;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;
import sg.edu.ntu.gg4u.pfa.visualizer.LineChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.PieChartVisualizer;
import sg.edu.ntu.gg4u.pfa.visualizer.TargetBarChartVisualizer;

public class TargetFragment extends Fragment {
    private static final String TAG = TargetFragment.class.getSimpleName();

   // List<Target> targetList = null;
   // List<SumByCategory> monthlyCostList = null;
  //  List<TargetAndCost> ls;
    List<String> cat_in_list =new ArrayList<>();
    List<Double> target_in_cat =new ArrayList<>();
    List<Double> amt_in_cat =new ArrayList<>();
    ImageButton decT, incT;
    double [] temp_cost;
    double [] temp_target;
    View help;
    FragmentActivity activity;


    ListView list;
//    String[] targetCat_in_list = {
//            "Food",
//            "Entertainment",
//            "leisure",
//            "Transportation",
//            "Others",
//            "Hello"
//
//    } ;
//
//    double[] targetAmt_in_List = {
//            100,
//            200,
//            300,
//            400,
//            500,
//            600
//    } ;
//
//    double[] actualAmt_in_List= {
//           200, 300, 11, 55, 200, 499
//
//    };

    TargetViewModel mViewModel;
    TextView actualAmt,targetAmt;

    XYPlot barChart;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDateTime cal2LocalDateTime(Calendar calendar) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        return localDateTime.truncatedTo(ChronoUnit.DAYS).withDayOfMonth(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //View root = inflater.inflate(R.layout.fragment_target, container, false);
        //final TextView textView = root.findViewById(R.id.actualAmount);
        final View root = inflater.inflate(R.layout.fragment_target, container, false);
        help = root;

        list=root.findViewById(R.id.listViewTarget);
        list.setClickable(true);

        //list.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                EditTargetFragment editFrag = new EditTargetFragment(cat_in_list.get(position));
                editFrag.show(activity.getSupportFragmentManager(), "editTar");
            }
        });


        final TextView month = root.findViewById(R.id.target_month);
        final Calendar calT = Calendar.getInstance();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy");
        String selectedMonth = month_date.format(calT.getTime());
        month.setText(selectedMonth);

        decT = root.findViewById(R.id.left_arrow_target);
        incT = root.findViewById(R.id.right_arrow_target);

        decT.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                calT.add(Calendar.MONTH, -1);
                String selectedMonth = month_date.format(calT.getTime());
                month.setText(selectedMonth);
               // Log.d("display xx" , calT.toString());
                resetMonth(calT);
                String currentMonth = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear();
                currentMonth = currentMonth.toLowerCase();
                Log.d("datehelp", currentMonth);
                Log.d("datehelp", month_date.format(calT.getTime()).toLowerCase());
                if ( month_date.format(calT.getTime()).toLowerCase().equalsIgnoreCase(currentMonth))
                {
                    incT.setClickable(false);
                    incT.setVisibility(View.INVISIBLE);
                }
                else {
                    incT.setClickable(true);
                    incT.setVisibility(View.VISIBLE);
                }
            }
        });
        calT.getTime();
        String currentMonth = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear();
        currentMonth = currentMonth.toLowerCase();
        Log.d("datehelp", currentMonth);
        Log.d("datehelp", month_date.format(calT.getTime()).toLowerCase());
        if ( month_date.format(calT.getTime()).toLowerCase().equalsIgnoreCase(currentMonth))
        {
            incT.setClickable(false);
            incT.setVisibility(View.INVISIBLE);
        }
        else {
            incT.setClickable(true);
            incT.setVisibility(View.VISIBLE);
        }

        incT.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                calT.add(Calendar.MONTH, 1);
                String selectedMonth = month_date.format(calT.getTime());
                month.setText(selectedMonth);
               // Log.d("display xx" , calT.toString());
                resetMonth(calT);
                String currentMonth = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear();
                currentMonth = currentMonth.toLowerCase();
                Log.d("datehelp", currentMonth);
                Log.d("datehelp", month_date.format(calT.getTime()).toLowerCase());
                if ( month_date.format(calT.getTime()).toLowerCase().equalsIgnoreCase(currentMonth))
                {
                    incT.setClickable(false);
                    incT.setVisibility(View.INVISIBLE);
                }
                else {
                    incT.setClickable(true);
                    incT.setVisibility(View.VISIBLE);
                }
            }
        });



        barChart =  root.findViewById(R.id.barChart);
        actualAmt = root.findViewById(R.id.actualAmount);
        targetAmt = root.findViewById(R.id.targetAmount);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        // database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(activity);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(TargetViewModel.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        Calendar defaultCal = Calendar.getInstance();
        LocalDateTime localDateTime = cal2LocalDateTime(defaultCal);

        mDisposable.add(mViewModel.getTargetAndCost(localDateTime.toLocalDate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenTargetAndCostChanged));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void resetMonth(Calendar calendar) {


        // If the month is changed
        // TODO: UI group: 1. implement this function, update the UI related to date
        //                 2. use this function when month range need to change

        // TODO: DB group: implement this function
        //                 re-select the data from the database

        LocalDateTime localDateTime = cal2LocalDateTime(calendar);

        mDisposable.clear();
        barChart.clear();
        cat_in_list =new ArrayList<>();
        target_in_cat =new ArrayList<>();
        amt_in_cat =new ArrayList<>();

        mDisposable.add(mViewModel.getTargetAndCost(localDateTime.toLocalDate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenTargetAndCostChanged));

    }

    public void whenTargetAndCostChanged(List<TargetAndCost> targetAndCosts) {
        // One target maps to one monthly cost
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        // TODO: DB group: use this function when data changes
        cat_in_list.clear();
        target_in_cat.clear();
        amt_in_cat.clear();

//        boolean check = true;
//        for (int i = 0; i < targetAndCosts.size(); i++) {
//            if (targetAndCosts.get(i).cost == 0) {}
//            else {
//                check =  false;
//            }
//            if (check) {
//                decT.setClickable(false);
//                decT.setVisibility(View.INVISIBLE);
//                Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
//                activity.findViewById(R.id.linearLayout3).setVisibility(View.GONE);
//                activity.findViewById(R.id.listViewTarget).setVisibility(View.GONE);
//            }
//            else {
//                decT.setClickable(true);
//                decT.setVisibility(View.VISIBLE);
//                activity.findViewById(R.id.linearLayout3).setVisibility(View.VISIBLE);
//                activity.findViewById(R.id.listViewTarget).setVisibility(View.VISIBLE);
//            }
//        }
        Log.d(TAG, "whenTargetAndCostChanged: targets number: "+targetAndCosts.size());

        for (TargetAndCost targetObj :targetAndCosts){
            cat_in_list.add(targetObj.categoryName);
            target_in_cat.add(Math.round(targetObj.targetAmount*10)/10.0);
            amt_in_cat.add(Math.round(targetObj.cost*10)/10.0);
        }
       // Log.d("display" , String.valueOf(cat_in_list));
        double [] amt_in_cat_array = new double[amt_in_cat.size()];
        for (int i = 0; i < amt_in_cat.size(); i++) {
            amt_in_cat_array[i] = amt_in_cat.get(i);
        }
        double [] target_in_cat_array = new double[target_in_cat.size()];
        for (int i = 0; i < target_in_cat.size(); i++) {
            target_in_cat_array[i] = target_in_cat.get(i);
        }

        Log.d(TAG, "whenTargetAndCostChanged: Context = "+String.valueOf(activity));
        CustomListTarget adapter = new
                CustomListTarget(activity,  cat_in_list.toArray(new String[0]) , target_in_cat_array, amt_in_cat_array);
        list.setAdapter(adapter);
        //Log.d("display xx" , String.valueOf(target_in_cat_array));


        double actualTemp = 0;
        for(int i=0;i<amt_in_cat_array.length;i++) {
            actualTemp = actualTemp + amt_in_cat_array[i];
        }

        double targetTemp = 0;
        for(int i=0;i<target_in_cat_array.length;i++){
            targetTemp = targetTemp +target_in_cat_array[i];
        }

        targetAmt.setText(String.valueOf(Math.round(targetTemp*10)/10.0));
        actualAmt.setText(String.valueOf(Math.round(actualTemp*10)/10.0));;
//        XYPlot barChart = (XYPlot) help.findViewById(R.id.barChart);
        TargetBarChartVisualizer bar = new TargetBarChartVisualizer();
        barChart.clear();
        bar.plot(barChart,  cat_in_list.toArray(new String[0]), target_in_cat_array, amt_in_cat_array);
        barChart.redraw();

    }

    private void insertOrUpdateTarget(Target target) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        mDisposable.add(mViewModel.insertOrUpdateTarget(target)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private void deleteTarget(Target target) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        mDisposable.add(mViewModel.deleteTarget(target)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}