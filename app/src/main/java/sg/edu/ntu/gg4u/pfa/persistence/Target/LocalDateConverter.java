package sg.edu.ntu.gg4u.pfa.persistence.Target;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateConverter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String fromLocalDate(LocalDate localDate) {
        return localDate.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate toLocalDate(String str) {
        return LocalDate.parse(str);
    }
}
