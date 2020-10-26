package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    public void whenCategoryChanged(Category newCategory) {
        // TODO: UI group: implement this function
        // TODO: DB group: call the function when data changes

    }

    public void whenCategoryListChanged(List<Category> newList) {
        // TODO: UI group: implement this function
        // TODO: DB group: the function when data changes

    }
}