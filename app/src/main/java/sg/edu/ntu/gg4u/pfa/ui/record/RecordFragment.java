package sg.edu.ntu.gg4u.pfa.ui.record;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecordFragment extends Fragment {

    EditText dateTXT_from;
    ImageView cal_from;

    EditText dateTXT_to;
    ImageView cal_to;

    CustomList adapter;
    List<Record> r;
    List<String> cat_in_list = new ArrayList<>();
    List<LocalDateTime> dates_in_list = new ArrayList<LocalDateTime>();
    List<Double> amount_in_list = new ArrayList<>();
    List<Double> sum_in_cat =new ArrayList<>();

    ListView list;

    //String[] dates_in_list = {
    //        "20-10-2020",
    //        "10-01-2020",
    //        "20-03-2020",
    //        "30-10-2020",
    //        "22-02-2020",
    //        "31-02-2021",
    //        "01-20-2021"
    //};

    //String[] cat_in_list = {
    //        "Food",
    //        "Transportation",
    //        "Leisure",
    //        "Entertainment",
    //        "Restaurant",
    //        "Birthday",
    //        "Vacation"
    //};

    //String[] amount_in_list= {
    //        "10",
    //        "14",
    //        "50",
    //        "144",
    //        "77",
    //        "88",
    //        "900"
   // };


    private TextView tv_totalExpense,  tv_userIncome , tv_amount, tv_categoryName, tv_timestamp;
    UserProfile userProfile = new UserProfile();
    public UserProfile getUserProfile() {
        return userProfile;
    }

    private RecordViewModel recordViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_record, container, false);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        final TextView todaydate = root.findViewById(R.id.record_current_date);
        todaydate.setText(date_n);


        tv_userIncome = root.findViewById(R.id.record_mnthIncome);
        tv_userIncome.setText(String.valueOf(getUserProfile().getIncome()));

        tv_totalExpense = root.findViewById(R.id.record_mnthExpense);
        /*double[]amount_doubleList= new double[amount_in_list.size()];
        double sum=0;
        int sizes=amount_in_list.size();
        for(int i=0;i<sizes;++i){
            amount_doubleList[i]=Double.parseDouble(amount_in_list.get(i));
            sum+=amount_doubleList[i];

        }
        String amount_stringdouble=Double.toString(Math.round(sum));
        tv_totalExpense.setText(amount_stringdouble);



         */

        tv_amount = root.findViewById(R.id.recordlist_amnt);
        tv_categoryName = root.findViewById(R.id.recordlist_category);
        tv_timestamp = root.findViewById(R.id.recordlist_date);
/*
        CustomList adapter = new
                CustomList(getActivity(), dates_in_list.toArray(), cat_in_list, amount_in_list);
        list = root.findViewById(R.id.record_listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + dates_in_list.get(+position), Toast.LENGTH_SHORT).show();
            }
        });


 */

        dateTXT_from = root.findViewById(R.id.record_date_from);
        cal_from = root.findViewById(R.id.record_calpicker_from);

        cal_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                int mDate = Cal.get(Calendar.DATE);
                int mMonth = Cal.get(Calendar.MONTH);
                int mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        dateTXT_from.setText(date + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });


        dateTXT_to = root.findViewById(R.id.record_date_to);
        cal_to = root.findViewById(R.id.record_calpicker_to);

        cal_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal1 = Calendar.getInstance();
                int mDate = Cal1.get(Calendar.DATE);
                int mMonth = Cal1.get(Calendar.MONTH);
                int mYear = Cal1.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        dateTXT_to.setText(date + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDate);
                //datePickerDialog.getDatePicker().setMinDate(Cal1.getTimeInMillis());
                datePickerDialog.show();
            }

        });
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();



    }




    private void resetDataRange(LocalDate beginDate, LocalDate endDate, Category selectedCategory) {
        // When the selectedCategory is NULL, display all the record.
        // TODO: UI group: 1. implement this function, update the UI related to date
        //                 2. use this function when date range need to change

        List<LocalDate> dates = new ArrayList<LocalDate>(25);

        LocalDate current = beginDate;
        //current = current.plusDays(1); // If you don't want to include the start date
        //toDate = toDate.plusDays(1); // If you want to include the end date
        while (current.isBefore(endDate)) {
            dates.add(current);
            current = current.plusDays(1);
        }


        // TODO: DB group: implement this function
        //                 re-select the data from the database


    }

    public void whenRecordListUpdated(List<Record> newRecords) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function
        //Record r=newRecord(Stimestamp,ScategoryName,Samount);
       /*
        r = newRecords;
        Record r1 = (new Record(LocalDateTime.now(),"Food",10.0));
        Record r2=new Record(LocalDateTime.now(),"Food",20.0);
        Record r3=new Record(LocalDateTime.now(),"Transport",30.0);
        newRecords.add(r1);
        newRecords.add(r2);
        newRecords.add(r3);
*/
       // Log.d("display xx" , String.valueOf(newRecords.get(1).timestamp));
       // Log.d("display xx" , newRecords.get(1).categoryName);
      //  Log.d("display xx" , Double.toString(newRecords.get(1).amount));

        for ( Record recordObj : newRecords){
            dates_in_list.add(recordObj.timestamp);
            cat_in_list.add(recordObj.categoryName);
            amount_in_list.add(recordObj.amount);

        }
        double [] amt_in_cat_array = new double[amount_in_list.size()];
        for (int i = 0; i < amount_in_list.size(); i++) {
            amt_in_cat_array[i] = amount_in_list.get(i);
        }

        CustomList adapter = new
                CustomList(getActivity(),  dates_in_list.toArray(new String[0]), cat_in_list.toArray(new String[0]) , amt_in_cat_array);
        list.setAdapter(adapter);
        Log.d("display xx" , dates_in_list.toArray(new String[0])[0]);





        // TODO: DB group: call this function when data changes
    }

    private void insertOrUpdateRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }

    private void deleteRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }
}