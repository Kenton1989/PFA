package sg.edu.ntu.gg4u.pfa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;

import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.ui.record.AddRecordActivity;

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


    private HomeViewModel homeViewModel;

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
        fab.setOnClickListener(view -> openEditorView());

        return root;
    }

    private void openEditorView() {
        Intent i = new Intent(HomeFragment.this.getActivity(), AddRecordActivity.class);
        startActivity(i);
    }

    public void whenTodayRecordListUpdated(List<Record> newRecordInToday) {
        // TODO: UI group: implement this function
        // TODO: DB group: use this function when data changes
    }
}