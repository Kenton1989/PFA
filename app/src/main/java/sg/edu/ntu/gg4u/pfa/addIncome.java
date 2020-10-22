package sg.edu.ntu.gg4u.pfa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import sg.edu.ntu.gg4u.pfa.ui.guide.SectionsPagerAdapter;

public class addIncome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incomeexpense);

        TabLayout tabs = findViewById(R.id.addTab);


    }
}
