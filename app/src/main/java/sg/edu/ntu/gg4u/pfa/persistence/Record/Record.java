package sg.edu.ntu.gg4u.pfa.persistence.Record;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;

@Entity(tableName = "Record")
@TypeConverters({LocalDateTimeConverter.class})
public class Record {

    public Double sum;
    @NonNull
    @PrimaryKey
    public LocalDateTime timestamp;

    public String categoryName;
    public double amount;

    @RequiresApi(api = Build.VERSION_CODES.O)

    public Record() {
        timestamp = LocalDateTime.now();
        categoryName = "dummy";
        amount = 0;
    }
    @Ignore
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Record(@NonNull LocalDateTime localDateTime, String name, double value) {
        timestamp = localDateTime;
        categoryName = name;
        amount = value;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Record(String name, double value) {
        this(LocalDateTime.now(), name, value);
    }


    @NonNull
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTimestamp(@NonNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
