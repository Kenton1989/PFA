package sg.edu.ntu.gg4u.pfa.ui.category;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;

public class CustomListCategory extends ArrayAdapter<String>{

    private final FragmentActivity context;
    private final ArrayList<String> catList;

    public CustomListCategory(FragmentActivity context,
                              ArrayList<String> catList) {
        super(context, R.layout.activity_category_listview, catList);
        this.context = context;
        this.catList = catList;


    }
    @Override
   public View getView(final int position, final View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_category_listview, null, true);
        TextView delete = (TextView) rowView.findViewById(R.id.deleteCat);
        if (position > 4) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // categoryIDs = getAllCategoryID()
                    // for (i = 0; i < categoryIDs.length(); i++)
                    //     if (getCategoryInfo(categoryIDs[i]).name == catList.get(position)))
                    //         removeCategory(categoryIDs[i])
                    ArrayList<String> catList2 = new ArrayList<>(Arrays.asList("Food", "short"));
                    CustomListCategory adapter = new CustomListCategory(context, catList2);
                    ListView list = context.findViewById(R.id.listCategory);
                    list.setAdapter(adapter);
                    CategoryActivity.setListViewHeightBasedOnChildren(list);
                }
            });
        }
        else {
            delete.setVisibility(View.GONE);
        }
        TextView catTitle = (TextView) rowView.findViewById(R.id.catList);
        catTitle.setText(catList.get(position));

        return rowView;
    }

    private void deleteCategory(Category category) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function
    }
}