package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;

public class CreateCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
    }

    public void insertCategoryToDB(Category newCategory) {
        // TODO UI group use this function to insert data after a Category is created
        // TODO database group will implement this function
    }
}