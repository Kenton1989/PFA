package sg.edu.ntu.gg4u.pfa.ui.target;

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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
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

   // List<Target> targetList = null;
   // List<SumByCategory> monthlyCostList = null;
    List<TargetAndCost> ls;
    List<String> cat_in_list =new ArrayList<>();
    List<Double> target_in_cat =new ArrayList<>();
    List<Double> amt_in_cat =new ArrayList<>();
    double [] temp_cost;
    double [] temp_target;
    View help;


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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(TargetViewModel.class);

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
                editFrag.show(getActivity().getSupportFragmentManager(), "editTar");
            }
        });


        ImageButton decT, incT;
        final TextView month = root.findViewById(R.id.target_month);
        final Calendar calT = Calendar.getInstance();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy");
        String selectedMonth = month_date.format(calT.getTime());
        month.setText(selectedMonth);

        decT = root.findViewById(R.id.left_arrow_target);
        incT = root.findViewById(R.id.right_arrow_target);

        decT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calT.add(Calendar.MONTH, -1);
                String selectedMonth = month_date.format(calT.getTime());
                month.setText(selectedMonth);
            }
        });
        calT.getTime();

        incT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calT.add(Calendar.MONTH, 1);
                String selectedMonth = month_date.format(calT.getTime());
                month.setText(selectedMonth);

            }
        });


        barChart =  root.findViewById(R.id.barChart);
        actualAmt = root.findViewById(R.id.actualAmount);
        targetAmt = root.findViewById(R.id.targetAmount);


        //XYPlot barChart = (XYPlot) root.findViewById(R.id.barChart);
      //  TargetBarChartVisualizer bar = new TargetBarChartVisualizer();
       // bar.plot(barChart, targetCat_in_list, targetAmt_in_List, actualAmt_in_List);

//        actualAmt = root.findViewById(R.id.actualAmount);
//        targetAmt = root.findViewById(R.id.targetAmount);
//        double actualTemp = 0;
//        for(int i=0;i<actualAmt_in_List.length;i++){
//            actualTemp = actualTemp +actualAmt_in_List[i];
//        }
//        //Log.d("display" , String.valueOf(expense));
//        actualAmt.setText(String.valueOf(actualTemp));
//
//        double targetTemp = 0;
//        for(int i=0;i<targetAmt_in_List.length;i++){
//            targetTemp = targetTemp +targetAmt_in_List[i];
//        }
//        //Log.d("display" , String.valueOf(expense));
//        targetAmt.setText(String.valueOf(targetTemp));
//        actualAmt.setText(String.valueOf(actualTemp));;






        return root;
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
        ls = targetAndCosts;
        TargetAndCost r1 = new TargetAndCost("food",100.0,70.0);
        TargetAndCost r2 = new TargetAndCost("transport",200.0,20.0);
        TargetAndCost r3 = new TargetAndCost("others",300.0,10.00);
        targetAndCosts.add(r1);
        targetAndCosts.add(r2);
        targetAndCosts.add(r3);
        Log.d("display xx" , targetAndCosts.get(1).categoryName);
        Log.d("display xx" , Double.toString(targetAndCosts.get(1).cost));
        Log.d("display xx" , Double.toString(targetAndCosts.get(1).targetAmount));
        for (TargetAndCost targetObj :targetAndCosts){
            cat_in_list.add(targetObj.categoryName);
            target_in_cat.add(targetObj.targetAmount);
            amt_in_cat.add(targetObj.cost);
        }
        double [] amt_in_cat_array = new double[amt_in_cat.size()];
        for (int i = 0; i < amt_in_cat.size(); i++) {
            amt_in_cat_array[i] = amt_in_cat.get(i);
        }
        double [] target_in_cat_array = new double[target_in_cat.size()];
        for (int i = 0; i < target_in_cat.size(); i++) {
            target_in_cat_array[i] = target_in_cat.get(i);
        }

        CustomListTarget adapter = new
                CustomListTarget(getActivity(),  cat_in_list.toArray(new String[0]) , target_in_cat_array, amt_in_cat_array);
        list.setAdapter(adapter);
        //Log.d("display xx" , String.valueOf(target_in_cat_array));



      //  Log.d("display xx" , Double.toString(target_in_cat_array[1]));
        double actualTemp = 0;
        for(int i=0;i<amt_in_cat_array.length;i++) {
            actualTemp = actualTemp + amt_in_cat_array[i];
        }

        double targetTemp = 0;
        for(int i=0;i<target_in_cat_array.length;i++){
            targetTemp = targetTemp +target_in_cat_array[i];
        }

        targetAmt.setText(String.valueOf(targetTemp));
        actualAmt.setText(String.valueOf(actualTemp));;
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