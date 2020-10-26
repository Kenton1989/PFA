package sg.edu.ntu.gg4u.pfa.ui.category;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import sg.edu.ntu.gg4u.pfa.R;

public class CustomListCategory extends ArrayAdapter<String>{

    private final Activity context;
    private final ArrayList<String> catList;

    public CustomListCategory(Activity context,
                              ArrayList<String> catList) {
        super(context, R.layout.activity_category_listview, catList);
        this.context = context;
        this.catList = catList;


    }
    @Override
   public View getView(int position, final View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_category_listview, null, true);
        TextView delete = (TextView) rowView.findViewById(R.id.deleteCat);
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DialogFragment createFrag = new CreateCategoryFragment();
//                        createFrag.show(getSupportFragmentManager(), "create");
//                    }
//                });
        TextView catTitle = (TextView) rowView.findViewById(R.id.catList);
        catTitle.setText(catList.get(position));

        return rowView;
    }
}