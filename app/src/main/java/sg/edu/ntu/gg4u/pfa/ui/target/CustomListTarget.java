package sg.edu.ntu.gg4u.pfa.ui.target;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import sg.edu.ntu.gg4u.pfa.R;

public class CustomListTarget extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] targetCat_in_list;
    private final double[] targetAmt_in_List;
    private final double[] actualAmt_in_List;
    //private final String[] amount_in_list;

    public CustomListTarget(Activity context,
                            String[] targetCat_in_list, double[] targetAmt_in_List, double[] actualAmt_in_List) {
        super(context, R.layout.fragment_target_listview, targetCat_in_list);
        this.context = context;
        this.targetCat_in_list = targetCat_in_list;
        this.targetAmt_in_List= targetAmt_in_List;
        this.actualAmt_in_List = actualAmt_in_List;


    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.fragment_target_listview, null, true);
        TextView targetCat = (TextView) rowView.findViewById(R.id.targetCategory);
        TextView targetAmt = (TextView) rowView.findViewById(R.id.targetCategoryAmount);
        TextView actualAmt = (TextView) rowView.findViewById(R.id.actualCategoryAmount);

        //TextView amtTitle = (TextView) rowView.findViewById(R.id.amount);
        targetCat.setText(targetCat_in_list[position]);
        targetAmt.setText(String.valueOf(targetAmt_in_List[position]));
        actualAmt.setText(String.valueOf(actualAmt_in_List[position]));

        //amtTitle.setText(amount_in_list[position]);

        return rowView;
    }
}