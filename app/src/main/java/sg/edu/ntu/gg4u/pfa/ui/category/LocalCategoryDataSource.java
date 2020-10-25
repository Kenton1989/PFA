package sg.edu.ntu.gg4u.pfa.ui.category;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Category.CategoryDao;
import sg.edu.ntu.gg4u.pfa.ui.CategoryDataSource;

public class LocalCategoryDataSource implements CategoryDataSource {

    private final CategoryDao mCategoryDao;

    public LocalCategoryDataSource(CategoryDao categoryDao) {
        mCategoryDao = categoryDao;
    }

    @Override
    public Flowable<List<Category>> getAllCategory() {
        return mCategoryDao.getAllCategory();
    }

    @Override
    public Completable createNewCategory(Category category) {
        return mCategoryDao.createNewCategory(category);
    }

    @Override
    public Completable deleteCategory(Category category) {
        return mCategoryDao.deleteCategory(category.getName());
    }
}
