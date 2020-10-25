package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.androidplot.util.PixelUtils;

import sg.edu.ntu.gg4u.pfa.R;

public class CreateCategoryActivity extends AppCompatActivity {
    EditText mEdit = (EditText)findViewById(R.id.categoryInput);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
    }

    public void submitCategory(View view) {
        String categoryName = mEdit.getText().toString();
        Boolean isAvailable = false;
        // categoryIDs = getAllCategoryID()
        // for (i = 0; i < categoryIDs.size(); i++) {
        //     if (getCategoryInfo(categoryIDs[i]).name == categoryName)
        //         break;
        //     else isAvailable = true;
        // if (isAvailable) {
        //     createNewCategory(categoryName);
        /* in if block */ Toast.makeText(this, "Category created!", Toast.LENGTH_SHORT).show();
        // }
        // else
        /* in else block */ Toast.makeText(this, "Category already exists!", Toast.LENGTH_SHORT).show();
    }

}