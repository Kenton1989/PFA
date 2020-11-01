package sg.edu.ntu.gg4u.pfa.ui.category;

import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.ui.CategoryDataSource;

public class CategoryViewModel extends ViewModel {

    private final CategoryDataSource mDataSource;

    public CategoryViewModel(CategoryDataSource categoryDataSource) {
        mDataSource = categoryDataSource;
    }

    public Flowable<List<Category>> getAllCategory() {
        return mDataSource.getAllCategory();
    }

    public Completable createNewCategory(Category category) {
        return mDataSource.createNewCategory(category);
    }

    public Completable deleteCategory(Category category) {
        return mDataSource.deleteCategory(category);
    }
}
