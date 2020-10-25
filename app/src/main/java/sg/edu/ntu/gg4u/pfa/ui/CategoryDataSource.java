package sg.edu.ntu.gg4u.pfa.ui;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;

public interface CategoryDataSource {

    Flowable<List<Category>> getAllCategory();

    Completable createNewCategory(Category category);

    Completable deleteCategory(Category category);
}
