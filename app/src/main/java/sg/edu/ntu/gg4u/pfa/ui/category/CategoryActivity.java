package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View.MeasureSpec;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.ui.home.CustomListHome;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeFragment;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeViewModel;
import sg.edu.ntu.gg4u.pfa.ui.profile.EditProfileActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileActivity;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class CategoryActivity extends FragmentActivity implements CreateCategoryFragment.NoticeDialogListener {

    EditText mEdit;
    ListView list;
    ArrayList<String> catList = new ArrayList<>(Arrays.asList("Food", "Entertainment", "Leisure", "Transportation", "Others", "New"));

    private ViewModelFactory mViewModelFactory;
    private CategoryViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void onDialogPositiveClick(CreateCategoryFragment dialog) {
        mEdit = dialog.getMView().findViewById(R.id.createCategory);
        String categoryName = mEdit.getText().toString();
        Log.d("category", categoryName);
        if (categoryName.equals("Food") || categoryName.equals("Transportation") || categoryName.equals("Clothing") || categoryName.equals("Entertainment")) {}
        else {
            insertCategory(new Category(categoryName));
        }
    }

    public void onDialogNegativeClick(CreateCategoryFragment dialog) {
        dialog.getDialog().cancel();
    }

    private static final String TAG = "ui.CategoryActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        list = findViewById(R.id.listCategory);
        setListViewHeightBasedOnChildren(list);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(CategoryViewModel.class);


        Button cr8 = findViewById(R.id.createCategoryBtn);
        cr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateCategoryFragment createFrag = new CreateCategoryFragment();
                createFrag.show(getSupportFragmentManager(), "create");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDisposable.add(mViewModel.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenCategoryListChanged));
    }

    @Override
    protected void onPause() {
        super.onPause();

        mDisposable.clear();
    }


    public void whenCategoryListChanged(List<Category> newList) {
        // This function will be called when the activity is created.
        // TODO: UI group: implement this function
        // TODO: DB group: the function when data changes DONE
        CustomListCategory adapter = new CustomListCategory(this, newList, mViewModel);
        list.setAdapter(adapter);
        CategoryActivity.setListViewHeightBasedOnChildren(list);
    }

    private void insertCategory(Category newCategory) {
        // TODO: UI group: use this function
        // TODO: DB group: implement this function

        mDisposable.add(mViewModel.createNewCategory(newCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}