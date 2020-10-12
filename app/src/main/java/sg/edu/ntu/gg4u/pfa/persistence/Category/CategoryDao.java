package sg.edu.ntu.gg4u.pfa.persistence.Category;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    Flowable<List<Category>> getAllCategory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable createNewCategory(Category category);

    @Query("DELETE FROM Category WHERE name = :name")
    void deleteCategory(String name);
}
