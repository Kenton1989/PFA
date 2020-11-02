package sg.edu.ntu.gg4u.pfa.persistence.Category;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.security.PublicKey;
import java.util.UUID;

@Entity(tableName = "Category")
public class Category {

    @NonNull
    @PrimaryKey
    private String name;

    @Ignore
    public Category(@NonNull String _name) {
        name = _name;
    }

    public Category() {
        name = "dummy";
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Ignore
    @Override
    public String toString() {
        return name;
    }
}
