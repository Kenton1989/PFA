package sg.edu.ntu.gg4u.pfa.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;

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

    private ViewModelFactory mViewModelFactory;

    private HomeViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //   View root = inflater.inflate(R.layout.fragment_home, container, false);
        //  final TextView textView = root.findViewById(R.id.totalExpense);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        CustomListHome adapter = new
                CustomListHome(getActivity(), cat_in_list, amount_in_list);
        list = root.findViewById(R.id.listHome);
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.addItemBtn);
        fab.setOnClickListener(view -> openRecordEditor());

        return root;
    }

    private void openRecordEditor(Category category) {

    }

    private void openRecordEditor() {

    }

    public void whenDataUpdated(List<SumByCategory> newDailyCost) {
        // one category maps to one daily cost
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        // TODO: DB group: use this function when data changes
    }
}