package sg.edu.ntu.gg4u.pfa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import sg.edu.ntu.gg4u.pfa.GuideActivity;
import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.MainActivity;
import sg.edu.ntu.gg4u.pfa.addIncome;

public class HomeFragment extends Fragment {

    ListView list;
    String[] cat_in_list = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others"


    } ;

    String[] amount_in_list = {
            "1000",
            "2000",
            "5000",
            "6000",
            "7000"
    } ;



    private HomeViewModel homeViewModel;

 public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
     //   View root = inflater.inflate(R.layout.fragment_home, container, false);
    //  final TextView textView = root.findViewById(R.id.totalExpense);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
                CustomListHome adapter = new
                        CustomListHome(getActivity(),  cat_in_list , amount_in_list);
                list= root.findViewById(R.id.listHome);
                list.setAdapter(adapter);
            }
        });
        FloatingActionButton fab =(FloatingActionButton)root.findViewById(R.id.addItemBtn);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i= new Intent(HomeFragment.this.getActivity(), addIncome.class);
                 startActivity(i);
             }
         });
        return root;
    }

}