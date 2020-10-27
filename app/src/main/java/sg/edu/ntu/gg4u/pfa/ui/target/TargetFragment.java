package sg.edu.ntu.gg4u.pfa.ui.target;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;

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

public class TargetFragment extends Fragment {

    Button dummy;
    List<Target> targetList = null;
    List<SumByCategory> monthlyCostList = null;

    ListView list;
    String[] targetCat_in_list = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others",
            "Vacation",
            "Transportation",
            "Others"
    };

    String[] targetAmt_in_List = {
            "1000",
            "2000",
            "5000",
            "6000",
            "7000",
            "8000",
            "6000",
            "7000"
    } ;

    String[] actualAmt_in_List= {
            "100",
            "200",
            "500",
            "600",
            "700",
            "800",
            "600",
            "700"


    };

    private TargetViewModel mViewModel;

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
        CustomListTarget adapter = new
                CustomListTarget(getActivity(),  targetCat_in_list , targetAmt_in_List, actualAmt_in_List);
        list=root.findViewById(R.id.listViewTarget);
        list.setAdapter(adapter);


        dummy = root.findViewById(R.id.dummyTarget);
        dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTargetFragment editFrag = new EditTargetFragment();
                editFrag.show(getActivity().getSupportFragmentManager(), "editTar");
            }
        });

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