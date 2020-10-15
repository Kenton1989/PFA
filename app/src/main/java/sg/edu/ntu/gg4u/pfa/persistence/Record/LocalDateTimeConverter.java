package sg.edu.ntu.gg4u.pfa.persistence.Record;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDateTime toLocalDateTime(String str) {
        return LocalDateTime.parse(str);
    }
}
