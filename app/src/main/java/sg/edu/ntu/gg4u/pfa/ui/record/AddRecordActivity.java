package sg.edu.ntu.gg4u.pfa.ui.record;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;

public class AddRecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);


    }

    public void insertRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }

    public void deleteRecord(Record record) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }
}
