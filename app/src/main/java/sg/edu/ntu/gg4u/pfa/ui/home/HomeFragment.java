package sg.edu.ntu.gg4u.pfa.ui.home;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.List;

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
    String[] cat_in_list = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others"
    };

    String[] amount_in_list = {
            "1000",
            "2000",
            "5000",
            "6000",
            "7000"
    };

    private HomeViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        CustomListHome adapter = new
                CustomListHome(getActivity(), cat_in_list, amount_in_list);
        list = root.findViewById(R.id.listHome);
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.addItemBtn);
        fab.setOnClickListener(view -> openRecordEditor());

        // Database view model initialization
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(HomeViewModel.class);

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
        // TODO: DB group: use this function when data changes

    }
}