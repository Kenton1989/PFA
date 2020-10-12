package sg.edu.ntu.gg4u.pfa.persistence.Category;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.security.PublicKey;
import java.util.UUID;

@Entity(tableName = "Category")
    public class Category {

    @PrimaryKey
    private String name;

    @Ignore
    public Category(String _name) {
        name = _name;
    }

    public Category() {
        name = "dummy";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
