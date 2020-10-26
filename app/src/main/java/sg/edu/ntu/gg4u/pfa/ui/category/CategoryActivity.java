package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "ui.CategoryActivity";

    private ViewModelFactory mViewModelFactory;

    private CategoryViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(CategoryViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getAllCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenCategoryListChanged));
    }

    public void whenCategoryChanged(Category newCategory) {
        // TODO UI group implement this function
        // TODO database activate the function when data chenges

    }

    public void whenCategoryListChanged(List<Category> newList) {
        // TODO UI group implement this function
        // TODO database activate the function when data chenges

    }
}