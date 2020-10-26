package sg.edu.ntu.gg4u.pfa.ui.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import sg.edu.ntu.gg4u.pfa.R;

public class CustomListHome extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] cat_in_list;
    private final String[] amount_in_list;
    //private final String[] amount_in_list;

    public CustomListHome(Activity context,
                          String[] cat_in_list, String[] amount_in_list) {
        super(context, R.layout.fragment_home_listview, cat_in_list);
        this.context = context;
        this.cat_in_list = cat_in_list;
        this.amount_in_list = amount_in_list;


    }
    @Override
   public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.fragment_home_listview, null, true);
        TextView catTitle = (TextView) rowView.findViewById(R.id.homeCat);
        TextView amtTitle = (TextView) rowView.findViewById(R.id.homeAmount);
        //TextView amtTitle = (TextView) rowView.findViewById(R.id.amount);
        catTitle.setText(cat_in_list[position]);
        amtTitle.setText(amount_in_list[position]);
        //amtTitle.setText(amount_in_list[position]);

        return rowView;
    }
}