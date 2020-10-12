package sg.edu.ntu.gg4u.pfa.persistence;

import androidx.room.Dao;
import androidx.room.Query;

import io.reactivex.Flowable;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    Flowable<Category> getAllCategory();

    @Query("DELETE FROM Category")
    void deleteCategory(String name);
}
