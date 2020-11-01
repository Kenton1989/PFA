package sg.edu.ntu.gg4u.pfa.ui.category;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.CategoryDataSource;
import sg.edu.ntu.gg4u.pfa.ui.TargetDataSource;

public class CategoryViewModel extends ViewModel {

    private final CategoryDataSource mCategoryDataSource;
    private final TargetDataSource mTargetDataSource;

    public CategoryViewModel(CategoryDataSource categoryDataSource,
                             TargetDataSource targetDataSource) {
        mCategoryDataSource = categoryDataSource;
        mTargetDataSource = targetDataSource;
    }

    public Flowable<List<Category>> getAllCategory() {
        return mCategoryDataSource.getAllCategory();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Completable createNewCategory(Category category) {
        return mCategoryDataSource.createNewCategory(category)
                .concatWith(mTargetDataSource.insertOrUpdateTarget(
                        new Target(category.getName(), 0)));
    }

    public Completable deleteCategory(Category category) {
        return mCategoryDataSource.deleteCategory(category);
    }
}
