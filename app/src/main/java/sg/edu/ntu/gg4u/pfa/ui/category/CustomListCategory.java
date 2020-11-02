package sg.edu.ntu.gg4u.pfa.ui.category;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;

public class CustomListCategory extends ArrayAdapter<Category>{
    private static final String TAG = CustomListCategory.class.getSimpleName();

    private final FragmentActivity context;
    private final List<Category> catList;

    // Database view model stuffs
    CategoryViewModel mViewModel;
    CompositeDisposable mDisposable = new CompositeDisposable();
    // Database view model stuffs END

    public CustomListCategory(FragmentActivity context,
                              List<Category> catList,
                              CategoryViewModel viewModel) {
        super(context, R.layout.activity_category_listview, catList);
        this.context = context;
        this.catList = catList;

        this.mViewModel = viewModel;

    }
    @Override
   public View getView(final int position, final View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_category_listview, null, true);
        TextView delete = (TextView) rowView.findViewById(R.id.deleteCat);
        if (position > 3) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+position+" "+catList.get(position).getName());
                    deleteCategory(catList.get(position));
                    Toast.makeText(parent.getContext(), "Category deleted!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            delete.setVisibility(View.GONE);
        }
        TextView catTitle = (TextView) rowView.findViewById(R.id.catList);
        catTitle.setText(catList.get(position).getName());

        return rowView;
    }

    private void deleteCategory(Category category) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        mDisposable.add(mViewModel.deleteCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}