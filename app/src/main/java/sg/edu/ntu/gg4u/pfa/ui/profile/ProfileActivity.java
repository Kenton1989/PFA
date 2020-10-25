package sg.edu.ntu.gg4u.pfa.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.addIncome;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeFragment;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeViewModel;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "This is a tag";

    private ProfileViewModel profileViewModel;
    private TextView userName,userGender,userJobfield,userIncome,userFamilySize;

    private ViewModelFactory mViewModelFactory;

    private ProfileViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO add view for age and academic qualification
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.profile_name);
        userGender=findViewById(R.id.profile_gender);
        userJobfield = findViewById(R.id.profile_job);
        userIncome=findViewById(R.id.profile_incomeGrp);
        userFamilySize=findViewById(R.id.profile_familySize);
        userName.setText("YeRy");
        userGender.setText("F");
        userJobfield.setText("Engineer");
        userIncome.setText("100-200");
        userFamilySize.setText("10");

        FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.btnProfile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(ProfileViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenProfileUpdated));
    }

    @Override
    protected void onStop() {
        super.onStop();

        mDisposable.clear();
    }

    private void whenProfileUpdated(UserProfile newProfile) {
        userName.setText(newProfile.getName());
        userGender.setText(newProfile.getGender().toString());
        userJobfield.setText(newProfile.getJobField().toString());
        userIncome.setText(String.valueOf(newProfile.getIncome()));
        userFamilySize.setText(String.valueOf(newProfile.getFamilySize()));
        // TODO two more text view required to display information
//        String.valueOf(newProfile.getFamilySize());
//        newProfile.getQualification().toString()
    }
}
