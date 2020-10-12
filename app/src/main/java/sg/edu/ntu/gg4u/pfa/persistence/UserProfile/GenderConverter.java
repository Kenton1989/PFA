package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import androidx.room.TypeConverter;

public class GenderConverter {
    @TypeConverter
    public static int fromGender(Gender gender) {
        return gender.ordinal();
    }

    @TypeConverter
    public static Gender toGender(int val) {
        return (Gender.values()[val]);
    }
}
