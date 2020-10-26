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

    public void onDialogPositiveClick(DialogFragment dialog) {
        mEdit = findViewById(R.id.createCategory);
        //        String categoryName = mEdit.getText().toString();
//        Boolean isAvailable = false;
        // categoryIDs = getAllCategoryID()
        // for (i = 0; i < categoryIDs.size(); i++) {
        //     if (getCategoryInfo(categoryIDs[i]).name == categoryName)
        //         break;
        //     else isAvailable = true;
        // if (isAvailable) {
        //     createNewCategory(categoryName);
//        /* in if block */ Toast.makeText(this, "Category created!", Toast.LENGTH_SHORT).show();
//        // }
//        // else
//        /* in else block */ Toast.makeText(this, "Category already exists!", Toast.LENGTH_SHORT).show();
//    }
        ArrayList<String> catList2 = new ArrayList<>(Arrays.asList("Food", "Tr", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        CustomListCategory adapter = new CustomListCategory(this, catList2);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);
    }

    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }

    private static final String TAG = "ui.CategoryActivity";

    private ViewModelFactory mViewModelFactory;

    private CategoryViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        CustomListCategory adapter = new CustomListCategory(this, catList );
        list = findViewById(R.id.listCategory);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(CategoryViewModel.class);


        Button cr8 = findViewById(R.id.createCategoryBtn);
        cr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment createFrag = new CreateCategoryFragment();
                createFrag.show(getSupportFragmentManager(), "create");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenCategoryListChanged));
    }

    public void whenCategoryListChanged(List<Category> newList) {
        // This function will be called when the activity is created.
        // TODO: UI group: implement this function
        // TODO: DB group: the function when data changes

    }
}