package sg.edu.ntu.gg4u.pfa.ui.home;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.ArrayList;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.guide.LocalGuideInfoDataSource;
import sg.edu.ntu.gg4u.pfa.ui.record.EditRecordFragment;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class HomeFragment extends Fragment {
  //  private final static String TAG = HomeFragment.class.getSimpleName();
    ListView list;
    CustomListHome adapter;
    List<String> cat_in_list = new ArrayList<>();
    List<Double> sum_in_cat =new ArrayList<>();
    private static DecimalFormat df = new DecimalFormat("0.00");
   /*

    String [] amount_in_list = {
            "100",
            "200",
            "300",
            "400",
    };*/

  /*  double [] amount_in_list = new double[]{
            100,
            300,
            200,
            400,
    };*/



    private HomeViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private TextView totalIncome,totalExpense,amount,categoryName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Database view model initialization
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(HomeViewModel.class);
        //Log.d("home output",mViewModel.getAllCategory().toString());
     /*   mDisposable.add(mViewModel.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayCat));*/


       //Log.d("display" , cat_in_list2.toString());

        list = root.findViewById(R.id.listHome);
        totalExpense = root.findViewById(R.id.totalExpense_home);





        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.addItemBtn);
        fab.setOnClickListener(view -> openRecordEditor());



        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        LocalDateTime todayBegin = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)).minusMonths(10);
        LocalDateTime todayEnd = todayBegin.plus(Duration.ofDays(1000));
      /*  mDisposable.add(mViewModel.getRecord(todayBegin, todayEnd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenDataUpdated));*/

        mDisposable.add(mViewModel.getGroupedRecordSum(todayBegin, todayEnd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenDataUpdated));

    }

    @Override
    public void onStop() {
        super.onStop();

        mDisposable.clear();
    }

   /* private void openRecordEditor(Category category) {

    }*/

    private void openRecordEditor() {
        // TODO link to the Edit Record Fragment
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void whenDataUpdated(List<SumByCategory> newDailyCost) {
       // TODO: UI group: implement this function
        // TODO: DB group: use this function when data changes

     /*   Log.d(TAG, "whenDataUpdated: "+newDailyCost.size());
        for (SumByCategory sum: newDailyCost) {
            Log.d(TAG, "whenDataUpdated: "+sum.categoryName+" "+sum.sum);
        }*/
//
//        SumByCategory r1 = new SumByCategory("food",10.0);
//        SumByCategory r2 = new SumByCategory("food",10.0);
//        SumByCategory r3 = new SumByCategory("others",10.0);
//        newDailyCost.add(r1);
//        newDailyCost.add(r2);
//        newDailyCost.add(r3);

        if (newDailyCost.size() == 0){
            //display category name .
        }
        else {
            for (SumByCategory catSum : newDailyCost) {

                sum_in_cat.add(Math.round(catSum.sum * 10) / 10.0);
                cat_in_list.add(catSum.categoryName);

            }

            double[] sum_in_cat_array = new double[sum_in_cat.size()];
            for (int i = 0; i < sum_in_cat.size(); i++) {
                sum_in_cat_array[i] = sum_in_cat.get(i);
            }


            adapter = new
                    CustomListHome(getActivity(), cat_in_list.toArray(new String[0]), sum_in_cat_array);
            list.setAdapter(adapter);


            double expense = 0;
            for (int i = 0; i < sum_in_cat.size(); i++) {
                expense = expense + sum_in_cat.get(i);
            }
            totalExpense.setText(String.valueOf(Math.round(expense * 10) / 10.0));

        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public void whenDataUpdated(List<Record> newRecords) {
        // TODO: UI group: implement this function
        // TODO: DB group: use this function when data changes

        Record r1 = new Record("food",10.0);
        Record r2 = new Record("food",10.0);
        Record r3 = new Record("others",10.0);
        newRecords.add(r1);
        newRecords.add(r2);
        newRecords.add(r3);

        for (Record record :newRecords){
                cat_in_list.add(record.getCategoryName());
                sum_in_cat.add(record.getAmount());
            }


        double [] sum_in_cat_array = new double[sum_in_cat.size()];
        for (int i = 0; i < sum_in_cat.size(); i++) {
            sum_in_cat_array[i] = sum_in_cat.get(i);
        }

        adapter = new
                CustomListHome(getActivity(),  cat_in_list.toArray(new String[0]), sum_in_cat_array);
        list.setAdapter(adapter);

    }*/

 /*   public void displayCat(List<Category> categoryList){


        for(Category category : categoryList){
            cat_in_list.add(category.getName());

        }
        cat_in_list.add("test");
        cat_in_list.add("test2");
        cat_in_list.add("test3");
        cat_in_list.add("Others");

        adapter = new
                CustomListHome(getActivity(),  cat_in_list.toArray(new String[0]), amount_in_list);
        list.setAdapter(adapter);



    }*/
}