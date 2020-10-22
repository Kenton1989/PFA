package sg.edu.ntu.gg4u.pfa.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.addIncome;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeFragment;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ProfileViewModel profileViewModel;
    TextView userName,userGender,userJobfield,userIncome,userFamilySize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    }
}
