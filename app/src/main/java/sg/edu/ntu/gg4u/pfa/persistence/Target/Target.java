package sg.edu.ntu.gg4u.pfa.persistence.Target;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "Target", primaryKeys = {"categoryName", "startDate"})
public class Target {

    @NonNull
    private String categoryName;

    @NonNull
    private LocalDate startDate;

    private double amount;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public Target() {
        categoryName = "dummy";
        startDate = LocalDate.now();
        amount = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Target(@NonNull String name, double value) {
        categoryName = name;
        startDate = LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(), 1);
        amount = value;
    }

    @NonNull
    public String getCategoryName() {
        return categoryName;
    }

    @NonNull
    public LocalDate getStartDate() {
        return startDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStartDate(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }
}
