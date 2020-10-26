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

    public void whenCategoryListChanged(List<Category> newList) {
        // This function will be called when the activity is created.
        // TODO: UI group: implement this function
        // TODO: DB group: the function when data changes

    }
    private void deleteCategory(Category category) {
        // TODO: UI group: use this function to delete category
        // TODO: DB group: implement this function
    }
}