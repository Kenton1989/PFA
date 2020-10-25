package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.addIncome;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.ui.home.CustomListHome;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeFragment;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeViewModel;
import sg.edu.ntu.gg4u.pfa.ui.profile.EditProfileActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileActivity;

public class CategoryActivity extends AppCompatActivity {

    private CategoryViewModel categoryViewModel;
    ListView list;
    String[] catList = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others",
            "fa",
            "fdfw",
            "FDSFAS",
            "fdsfafdas",
            "fdsaf"
    } ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        CustomListCategory adapter = new CustomListCategory(this, catList );
        list = findViewById(R.id.listCategory);
        list.setAdapter(adapter);

        Button cr8 = findViewById(R.id.createCategoryBtn);
        cr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(CategoryActivity.this, CreateCategoryActivity.class);
                startActivity(i);
            }
        });
    }
}