package sg.edu.ntu.gg4u.pfa.ui.report;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import sg.edu.ntu.gg4u.pfa.R;

public class CustomListReport extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] cat_in_list;
    private final String[] percent_in_list;
    private final String[] sugg_in_list;

    public CustomListReport(Activity context,
                            String[] cat_in_list, String[] percent_in_list, String[] sugg_in_list){
        super(context, R.layout.fragment_report_listview, cat_in_list);
        this.context = context;
        this.cat_in_list = cat_in_list;
        this.percent_in_list = percent_in_list;
        this.sugg_in_list = sugg_in_list;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fragment_report_listview, null, true);
        TextView catTitle = (TextView) rowView.findViewById(R.id.reportlist_category);
        catTitle.setText(cat_in_list[position]);
        TextView percentTitle = (TextView) rowView.findViewById(R.id.reportlist_percentile);
        percentTitle.setText(percent_in_list[position]);
        TextView suggList = (TextView) rowView.findViewById(R.id.reportlist_suggestion);
        suggList.setText(sugg_in_list[position]);

        return rowView;
    }


}
