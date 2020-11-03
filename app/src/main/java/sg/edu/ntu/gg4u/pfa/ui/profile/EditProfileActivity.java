package sg.edu.ntu.gg4u.pfa.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.AcademicQualification;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.Gender;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.JobField;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String[] genders;
    private String[] jobs;
    private String[] academicQualifications;
//    String[] income = {"1000-2000", "2000-3000", "3000-4000", "4000-5000", "above 5000", "no income"};
//    String[] familySize = {"1", "2", "3", "4", "5", "above 5"};

    private ProfileViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private Button confirmButton;
    private Spinner spinGender;
    private Spinner spinJob;
    private EditText textEditIncome;
    private EditText textEditFamSize;
    private EditText textEditName;
    private Spinner spinAcademicQualification;
    private EditText textEditAge;
    // TODO store widget for age and academic qualification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        genders = Gender.getAllGender();
        jobs = JobField.getAllJobField();
        academicQualifications = AcademicQualification.getAllAcademicQualification();

        // TODO add view for age and academic qualification
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        confirmButton = (Button) findViewById(R.id.profileConfirm);
        spinGender = (Spinner) findViewById(R.id.gender_spinner);
        spinJob = (Spinner) findViewById(R.id.job_spinner);
        spinAcademicQualification = (Spinner) findViewById(R.id.academicQualification_spinner);
        textEditIncome = (EditText) findViewById(R.id.income_edittext);
        textEditFamSize = (EditText) findViewById(R.id.family_size_edittext);
        textEditName = (EditText) findViewById(R.id.nameEdit);
        textEditAge = (EditText) findViewById(R.id.age_editText);

        spinGender.setOnItemSelectedListener(this);
        spinJob.setOnItemSelectedListener(this);
        spinAcademicQualification.setOnItemSelectedListener(this);
//        spinIncome.setOnItemSelectedListener(this);
//        spinFamSize.setOnItemSelectedListener(this);

        ArrayAdapter<String> gArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        gArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinGender.setAdapter(gArray);

        ArrayAdapter<String> jArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobs);
        jArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinJob.setAdapter(jArray);

        // TODO set up array adapter for academic qualification
        ArrayAdapter<String> aArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, academicQualifications);
        jArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinAcademicQualification.setAdapter(aArray);


        confirmButton.setOnClickListener(this::onConfirmClicked);

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(ProfileViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenProfileChanged));
    }

    @Override
    protected void onStop() {
        super.onStop();

        mDisposable.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(), genders[position], Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), jobs[position], Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), income[position], Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), familySize[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void whenProfileChanged(UserProfile newProfile) {
        if (newProfile.getIncome() != null)
            textEditIncome.setText(String.valueOf(newProfile.getIncome()));
        if (newProfile.getFamilySize() != null)
            textEditFamSize.setText(String.valueOf(newProfile.getFamilySize()));
        if (newProfile.getName() != null && !newProfile.getName().equals(UserProfile.NAMELESS))
            textEditName.setText(String.valueOf(newProfile.getName()));
        if (newProfile.getAge() != null)
            textEditAge.setText(String.valueOf(newProfile.getAge()));

        spinGender.setSelection(search(genders, newProfile.getGender().getFullName()));
        spinJob.setSelection(search(jobs, newProfile.getJobField().getFullName()));
        spinAcademicQualification.setSelection(search(academicQualifications, newProfile.getQualification().getFullName()));
    }

    private int search(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void updateProfile(UserProfile newUserProfile) {
        confirmButton.setEnabled(false);
        mDisposable.add(mViewModel.updateUserProfile(newUserProfile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> confirmButton.setEnabled(true)));
    }

    private void printToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    public void onConfirmClicked(View confirmButtom) {

        String name = textEditName.getText().toString().trim();
        Gender gender = null;
        JobField job = null;
        Double income = null;
        Integer famSize = null;
        Integer age = null;
        AcademicQualification academicQualification = null;

        if (name.length() == 0) {
            name = "Nameless";
        }

        int genderIndex = spinGender.getSelectedItemPosition();
        gender = Gender.toGender(genders[genderIndex]);

        int jobIndex = spinJob.getSelectedItemPosition();
        job = JobField.toJobField(jobs[jobIndex]);

        String incomeStr = textEditIncome.getText().toString();
        if (incomeStr.length() != 0) {
            income = Double.valueOf(incomeStr);
        }

        String famSizeStr = textEditFamSize.getText().toString();
        if (famSizeStr.length() != 0) {
            famSize = Integer.valueOf(famSizeStr);
        }

        // TODO read age string from UI
        String ageStr = textEditAge.getText().toString();
        if (ageStr.length() != 0) {
            age = Integer.valueOf(ageStr);
        }

        // TODO read academic qualification index from UI
        int acaQualiIndex = spinAcademicQualification.getSelectedItemPosition();
        academicQualification = AcademicQualification.toAcademicQualification(
                academicQualifications[acaQualiIndex]);

        UserProfile newProfile = new UserProfile(name, gender, job, famSize, income, age, academicQualification);

        updateProfile(newProfile);

        finish(); // end this activity and return to the last activity.
    }
}