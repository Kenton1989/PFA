package sg.edu.ntu.gg4u.pfa.ui.home;

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

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class HomeFragment extends Fragment {
    ListView list;
    CustomListHome adapter;
    List<String> cat_in_list = new ArrayList<>();
    List<String> sum_in_cat =new ArrayList();
   /* String[] cat_in_list = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others"
    };

    String [] amount_in_list = {
            "100",
            "200",
            "300",
            "400",
    };*/

    double [] amount_in_list = new double[]{
            100,
            300,
            200,
            400,
    };

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
        mDisposable.add(mViewModel.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayCat));


       //Log.d("display" , cat_in_list2.toString());

        cat_in_list.add("test");
        cat_in_list.add("test2");
        cat_in_list.add("test3");
        /* adapter = new
                CustomListHome(getActivity(),  cat_in_list2.toArray(new String[0]), amount_in_list);*/
        list = root.findViewById(R.id.listHome);
        //list.setAdapter(adapter);


        //set total expense
        //totalIncome = root.findViewById(R.id.totalIncome);
        totalExpense = root.findViewById(R.id.totalExpense_home);
        double expense = 0;
        for(int i=0;i<amount_in_list.length;i++){
           expense = expense +amount_in_list[i];
        }
        //Log.d("display" , String.valueOf(expense));
        totalExpense.setText(String.valueOf(expense));



        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.addItemBtn);
        fab.setOnClickListener(view -> openRecordEditor());



        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();


        LocalDateTime todayBegin = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime todayEnd = todayBegin.plus(Duration.ofDays(1));
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

    private void openRecordEditor(Category category) {

    }

    private void openRecordEditor() {

    }

    public void whenDataUpdated(List<SumByCategory> newDailyCost) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
     //   for (SumByCategory catSum :newDailyCost){
      //      sum_in_cat.add(catSum.());
       // }
        // TODO: DB group: use this function when data changes


    }
    public void displayCat(List<Category> categoryList){


        for(Category category : categoryList){
            cat_in_list.add(category.getName());

        }
        cat_in_list.add("Others");

        adapter = new
                CustomListHome(getActivity(),  cat_in_list.toArray(new String[0]), amount_in_list);
        list.setAdapter(adapter);



    }
}