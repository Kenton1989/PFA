package sg.edu.ntu.gg4u.pfa.persistence;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.security.PublicKey;
import java.util.UUID;

@Entity(tableName = "Category")
    public class Category {

    private static int maxId = 10; // change to dynamic calculation after...

    @PrimaryKey
    private int categoryId;

    private String name;

    @Ignore
    public Category(String _name) {
        categoryId = ++maxId;
        name = _name;
    }

    public Category() {
        categoryId = Integer.MAX_VALUE;
        name = "dummy";
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
