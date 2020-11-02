package sg.edu.ntu.gg4u.pfa.ui.record;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private List<String> dates_in_list;
    private List<String> cat_in_list;
    private List<String> amount_in_list;

    public CustomList(Activity context,
                      List<String> dates_in_list, List<String> cat_in_list, List<String> amount_in_list) {
        super(context, R.layout.fragment_record_listview, dates_in_list);
        this.context = context;
        this.dates_in_list = dates_in_list;
        this.cat_in_list = cat_in_list;
        this.amount_in_list = amount_in_list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fragment_record_listview, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.recordlist_date);
        TextView catTitle = (TextView) rowView.findViewById(R.id.recordlist_category);
        TextView amtTitle = (TextView) rowView.findViewById(R.id.recordlist_amnt);
        txtTitle.setText(dates_in_list.get(position));
        catTitle.setText(cat_in_list.get(position));
        amtTitle.setText(amount_in_list.get(position));

        return rowView;
    }
}