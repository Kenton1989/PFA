package sg.edu.ntu.gg4u.pfa.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sg.edu.ntu.gg4u.pfa.R;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] gender={"Female","Male","others"};
    String[] job={"Engineer","Accountant","students","teacher","others"};
    String[] income={"1000-2000","2000-3000","3000-4000","4000-5000","above 5000","no income"};
    String[] familySize={"1","2","3","4","5","above 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Button btn =(Button)findViewById(R.id.profileConfirm);
        Spinner spinGender = (Spinner) findViewById(R.id.gender_spinner);
        Spinner spinJob = (Spinner) findViewById(R.id.job_spinner);
        Spinner spinIncome = (Spinner) findViewById(R.id.incomeGrp_spinner);
        Spinner spinFamSize = (Spinner) findViewById(R.id.familySize_spinner);
        spinGender.setOnItemSelectedListener(this);
        spinJob.setOnItemSelectedListener(this);
        spinIncome.setOnItemSelectedListener(this);
        spinFamSize.setOnItemSelectedListener(this);

        ArrayAdapter gArray = new ArrayAdapter(this,android.R.layout.simple_spinner_item,gender);
        gArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinGender.setAdapter(gArray);

        ArrayAdapter jArray = new ArrayAdapter(this,android.R.layout.simple_spinner_item,job);
        jArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinJob.setAdapter(jArray);

        ArrayAdapter iArray = new ArrayAdapter(this,android.R.layout.simple_spinner_item,income);
        iArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinIncome.setAdapter(iArray);

        ArrayAdapter fArray = new ArrayAdapter(this,android.R.layout.simple_spinner_item,familySize);
        fArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinFamSize.setAdapter(fArray);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), gender[position],Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), job[position],Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), income[position],Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), familySize[position],Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}