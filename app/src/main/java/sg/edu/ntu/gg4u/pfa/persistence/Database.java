package sg.edu.ntu.gg4u.pfa.persistence;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Category.CategoryDao;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfoDao;
import sg.edu.ntu.gg4u.pfa.persistence.Record.LocalDateTimeConverter;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.RecordDao;
import sg.edu.ntu.gg4u.pfa.persistence.Target.LocalDateConverter;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.Target.TargetDao;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.AcademicQualification;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.AcademicQualificationConverter;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.GenderConverter;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.JobFieldConverter;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfileDao;

@androidx.room.Database(entities = {Category.class, GuideInfo.class, Record.class, Target.class,
        UserProfile.class}, version = 1)
@TypeConverters({GenderConverter.class, JobFieldConverter.class,
        AcademicQualificationConverter.class, LocalDateTimeConverter.class,
        LocalDateConverter.class})
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract GuideInfoDao guideInfoDao();
    public abstract RecordDao recordDao();
    public abstract TargetDao targetDao();
    public abstract UserProfileDao userProfileDao();

    public static Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "MainDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
