package sg.edu.ntu.gg4u.pfa.ui.record;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import sg.edu.ntu.gg4u.pfa.MainActivity;
import sg.edu.ntu.gg4u.pfa.R;

public class RecordFragment extends Fragment {

    EditText dateTXT_from;
    ImageView cal_from;

    EditText dateTXT_to;
    ImageView cal_to;

    ListView list;
    String[] dates_in_list = {
            "20-10-2020",
            "10-01-2020",
            "20-03-2020",
            "30-10-2020",
            "22-02-2020",
            "31-02-2021",
            "01-20-2021"
    } ;

    String[] cat_in_list= {
            "Food",
            "Transportation",
            "Leisure",
            "Entertainment",
            "Restaurant",
            "Birthday",
            "Vacation"
    };

    //String[] amount_in_list= {
    //        "10",
    //        "14",
    //        "50",
    //        "144",
    //        "77",
    //        "88",
    //        "900"
    //};




    private RecordViewModel recordViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recordViewModel =
                ViewModelProviders.of(this).get(RecordViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_record, container, false);
        final View root = inflater.inflate(R.layout.fragment_record, container, false);
        //final TextView textView = root.findViewById(R.id.text_record);
        recordViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);


        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        final TextView todaydate  = root.findViewById(R.id.date);
                todaydate.setText(date_n);


                CustomList adapter = new
                        CustomList(getActivity(), dates_in_list , cat_in_list);
                list=root.findViewById(R.id.listView);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(getActivity(), "You Clicked at " + dates_in_list[+position], Toast.LENGTH_SHORT).show();
                    }
                });



            dateTXT_from = root.findViewById(R.id.date_from);
            cal_from = root.findViewById(R.id.calpicker_from);

            cal_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar Cal = Calendar.getInstance();
                    int mDate = Cal.get(Calendar.DATE);
                    int mMonth= Cal.get(Calendar.MONTH);
                    int mYear= Cal.get(Calendar.YEAR);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int date) {
                            dateTXT_from.setText(date+"-" + (month + 1)  + "-" + year);
                        }
                    }, mYear,mMonth,mDate);
                    datePickerDialog.show();
                }
            });

            dateTXT_to = root.findViewById(R.id.date_to);
            cal_to = root.findViewById(R.id.calpicker_to);


            cal_to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar Cal1 = Calendar.getInstance();
                        int mDate = Cal1.get(Calendar.DATE);
                        int mMonth= Cal1.get(Calendar.MONTH);
                        int mYear= Cal1.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int date) {
                                dateTXT_to.setText(date+"-" + month + "-" + year);
                            }
                        }, mYear,mMonth,mDate);
                        //datePickerDialog.getDatePicker().setMinDate(Cal1.getTimeInMillis());
                        datePickerDialog.show();
                    }
                });



            }
        });
        return root;
    }
}