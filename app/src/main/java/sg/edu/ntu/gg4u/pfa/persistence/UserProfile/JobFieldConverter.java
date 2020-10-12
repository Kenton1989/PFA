package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import androidx.room.TypeConverter;


public class JobFieldConverter {
    @TypeConverter
    public static int fromJobField(JobField jobField) {
        return jobField.ordinal();
    }

    @TypeConverter
    public static JobField toJobField(int val) {
        return (JobField.values()[val]);
    }

}
