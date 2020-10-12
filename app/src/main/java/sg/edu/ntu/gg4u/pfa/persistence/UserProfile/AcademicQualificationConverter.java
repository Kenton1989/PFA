package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import androidx.room.TypeConverter;

public class AcademicQualificationConverter {
    @TypeConverter
    public static int fromAcademicQualification(AcademicQualification academicQualification) {
        return academicQualification.ordinal();
    }

    @TypeConverter
    public static AcademicQualification toAcademicQualification(int val) {
        return (AcademicQualification.values()[val]);
    }
}
