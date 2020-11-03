package sg.edu.ntu.gg4u.pfa.ui.record;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.DecimalFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecordFragment extends Fragment {

    EditText dateTXT_from;
    ImageView cal_from;

    EditText dateTXT_to;
    ImageView cal_to;
    Button record_go_btn;
    private static DecimalFormat df = new DecimalFormat("0.00");

    List<Record> r;
    List<String> cat_in_list = new ArrayList<>();
    List<String> dates_in_list = new ArrayList<>();
    List<String> amount_in_list = new ArrayList<>();
    TextView textview_displaying_date_range;
    LocalDate localDate_from;
    LocalDate localDate_to;
    private LocalDate displaying_date_from;
    private LocalDate displaying_date_to;

    ListView list;

    private TextView tv_totalExpense, tv_userIncome, tv_amount, tv_categoryName, tv_timestamp;
    UserProfile userProfile = new UserProfile();
    Category category = new Category();
    Activity activity;

    public RecordFragment() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    // Category category = new Category();

    private RecordViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_record, container, false);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //String date_n = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault()).format(new Date());
        textview_displaying_date_range = root.findViewById(R.id.record_current_date);
        textview_displaying_date_range.setText(date_n);

        record_go_btn = root.findViewById(R.id.record_go_btn) ;
        /*
        tv_userIncome = root.findViewById(R.id.record_mnthIncome);
        tv_userIncome.setText(String.valueOf(getUserProfile().getIncome()));
*/
        tv_totalExpense = root.findViewById(R.id.record_mnthExpense);
        tv_amount = root.findViewById(R.id.recordlist_amnt);
        tv_categoryName = root.findViewById(R.id.recordlist_category);
        tv_timestamp = root.findViewById(R.id.recordlist_date);


        list = root.findViewById(R.id.record_listView);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                EditRecordFragment editFrag = new EditRecordFragment(r.get(position));
                editFrag.show(getActivity().getSupportFragmentManager(), "editRec");
            }
        });


        dateTXT_from = root.findViewById(R.id.record_date_from);
        cal_from = root.findViewById(R.id.record_calpicker_from);

        cal_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                int mDate = Cal.get(Calendar.DATE);
                int mMonth = Cal.get(Calendar.MONTH);
                int mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        //to add in the '0' in front of single digit date, datepicker does not provide the 0
                        int month1 = month + 1;
                        String fm = "" + month1;
                        String fd = "" + date;
                        if (month1 < 10) {
                            fm = "0" + month;
                        }
                        if (date < 10) {
                            fd = "0" + date;
                        }

                        //String datez = "" + year + "-" + fm + "-" + fd;
                        String selected_date_begin = ""+year+"-"+fm+"-"+fd;

                        dateTXT_from.setText(selected_date_begin);
                        //dateTXT_from.setText(date + "-" + (month + 1) + "-" + year);
                        // String fulldate1 = (year + "-" + (month + 1) + "-" + date);
                        //dateTXT_from.setText(fulldate1);
                        // localDate_from = LocalDate.parse((datez), formatter);
                        localDate_from = LocalDate.parse((selected_date_begin), formatter);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        int month1 = month + 1;
                        String fm = "" + month1;
                        String fd = "" + date;
                        if (month1 < 10) {
                            fm = "0" + month;
                        }
                        if (date < 10) {
                            fd = "0" + date;
                        }

                        // String datez = "" + year + "-" + fm + "-" + fd;
                        // dateTXT_to.setText(datez);
                        // //dateTXT_to.setText(date + "-" + (month + 1) + "-" + year);
                        // //String fulldate2 = (year + "-" + (month + 1) + "-" + date);
                        // //dateTXT_to.setText(fulldate2);
                        // localDate_to = LocalDate.parse(datez, formatter);

                        // if (datez.isEmpty()) {
                        String selected_date_end = ""+year+"-"+fm+"-"+fd;
                        dateTXT_to.setText(selected_date_end);
                        //dateTXT_to.setText(date + "-" + (month + 1) + "-" + year);
                        //String fulldate2 = (year + "-" + (month + 1) + "-" + date);
                        //dateTXT_to.setText(fulldate2);
                        localDate_to = LocalDate.parse(selected_date_end, formatter);

                        if (selected_date_end.isEmpty()){
                            record_go_btn.setEnabled(false);
                        } else {
                            record_go_btn.setEnabled(true);
                        }


                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }


        });

        FloatingActionButton dummy = root.findViewById(R.id.addRecord);
        dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditRecordFragment editFrag = new EditRecordFragment();
                editFrag.show(getActivity().getSupportFragmentManager(), "editRec");
            }
        });
        record_go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDataRange(localDate_from, localDate_to, null);
                Log.d("display xx", getCategory().toString());

            }
        });


        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        mViewModel = new ViewModelProvider(this, factory)
                .get(RecordViewModel.class);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        // TODO: UI group : set default time interval here
        LocalDate beginDate = LocalDate.now().minusMonths(0),
                endDate = LocalDate.now();
        resetDataRange(beginDate, endDate, null);

        LocalDateTime todayBegin = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime todayEnd = todayBegin.plus(Duration.ofDays(1));
        mDisposable.add(mViewModel.getRecord(todayBegin, todayEnd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenRecordListUpdated));


    }

    private void printToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void resetDataRange(LocalDate beginDate, LocalDate endDate, Category selectedCategory) {
        if (beginDate.compareTo(endDate) > 0) {
            printToast("The the begin date cannot be later than then end date");
            return;
        }
        // When the selectedCategory is NULL, display all the record.
        // TODO: UI group: 1. implement this function, update the UI related to date
        //                 2. use this function when date range need to change

        displaying_date_from = beginDate;
        displaying_date_to = endDate;
        localDate_from = beginDate;
        localDate_to = endDate;
        textview_displaying_date_range.setText(displaying_date_from+" ~ "+displaying_date_to);

        dateTXT_from.setText(displaying_date_from.toString());
        dateTXT_to.setText(displaying_date_to.toString());

        // TODO: DB group: implement this function
        //                 re-select the data from the database

        mDisposable.clear();
        cat_in_list = new ArrayList<>();
        dates_in_list = new ArrayList<>();
        amount_in_list = new ArrayList<>();


        if (selectedCategory == null) {
            mDisposable.add(mViewModel.getRecord(beginDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::whenRecordListUpdated));
        } else {
            mDisposable.add(mViewModel.getRecordByCategory(beginDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1), selectedCategory.getName())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::whenRecordListUpdated));
        }
    }

    public void whenRecordListUpdated(List<Record> newRecords) {
        // this function will be called when the fragment is created.
        // TODO: UI group: implement this function

        r = newRecords;
        dates_in_list.clear();
        cat_in_list.clear();
        amount_in_list.clear();
        for (Record recordObj : newRecords) {
            String str_date = recordObj.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
            dates_in_list.add(str_date);
            cat_in_list.add(recordObj.categoryName);
            amount_in_list.add(String.valueOf(recordObj.amount));

        }

        double[] amount_doubleList = new double[amount_in_list.size()];
        double sum = 0;
        int sizes = amount_in_list.size();
        for (int i = 0; i < sizes; i++) {
            amount_doubleList[i] = Double.parseDouble(amount_in_list.get(i));
            sum += amount_doubleList[i];

        }

        String amount_stringdouble = ((df.format(sum)));
        tv_totalExpense.setText("$" + amount_stringdouble);

        CustomList adapter = new
                CustomList(activity, dates_in_list, cat_in_list, amount_in_list);
        list.setAdapter(adapter);
    }


    private void insertOrUpdateRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        mDisposable.add(mViewModel.addRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private void deleteRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
        mDisposable.add(mViewModel.deleteRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}