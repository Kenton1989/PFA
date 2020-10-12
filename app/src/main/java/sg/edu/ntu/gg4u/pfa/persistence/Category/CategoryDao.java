package sg.edu.ntu.gg4u.pfa.persistence.Category;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

import io.reactivex.Flowable;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    Flowable<ArrayList<Category>> getAllCategory();

    @Query("DELETE FROM Category")
    void deleteCategory(String name);
}
