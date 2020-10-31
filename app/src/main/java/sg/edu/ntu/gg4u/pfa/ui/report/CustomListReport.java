package sg.edu.ntu.gg4u.pfa.ui.report;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

import sg.edu.ntu.gg4u.pfa.R;

public class CustomListReport extends ArrayAdapter<String> {

    private final Activity context;

    private  List<String> cat_in_list;
    private  List<String> percent_in_list;
    private  List<String> sugg_in_list;

    public CustomListReport(Activity context,
                            List<String> cat_in_list, List<String> percent_in_list, List<String> sugg_in_list){
        super(context, R.layout.fragment_report_listview, cat_in_list);
        this.context = context;
        this.cat_in_list = cat_in_list;
        this.percent_in_list = percent_in_list;
        this.sugg_in_list = sugg_in_list;

    }

    public CustomListReport(Activity context,
                            List<String> cat_in_list, List<String> sugg_in_list){
        super(context, R.layout.fragment_report_listview, cat_in_list);
        this.context = context;
        this.cat_in_list = cat_in_list;
        this.sugg_in_list = sugg_in_list;

    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fragment_report_listview, null, true);
        TextView catTitle = (TextView) rowView.findViewById(R.id.reportlist_category);
        catTitle.setText(cat_in_list.get(position));
  //      TextView percentTitle = (TextView) rowView.findViewById(R.id.reportlist_percentile);
//        percentTitle.setText(String.valueOf(percent_in_list.get(position)));
        TextView suggList = (TextView) rowView.findViewById(R.id.reportlist_suggestion);
        suggList.setText(String.valueOf(sugg_in_list.get(position)));

        return rowView;
    }


}
