package sg.edu.ntu.gg4u.pfa.ui.record;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.ui.guide.SectionsPagerAdapter;

public class AddRecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incomeexpense);

        TabLayout tabs = findViewById(R.id.addTab);


    }

    public void insertRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }

    public void updateRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }
}
