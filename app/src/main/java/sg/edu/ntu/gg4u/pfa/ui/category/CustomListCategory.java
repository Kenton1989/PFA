package sg.edu.ntu.gg4u.pfa.ui.category;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import sg.edu.ntu.gg4u.pfa.R;

public class CustomListCategory extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] catList;

    public CustomListCategory(Activity context,
                              String[] catList) {
        super(context, R.layout.activity_category_listview, catList);
        this.context = context;
        this.catList = catList;


    }
    @Override
   public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_category_listview, null, true);
        TextView catTitle = (TextView) rowView.findViewById(R.id.catList);
        catTitle.setText(catList[position]);

        return rowView;
    }
}