package sg.edu.ntu.gg4u.pfa.persistence;

import android.app.Activity;
import android.content.Context;

import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;

import sg.edu.ntu.gg4u.pfa.R;
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

    public GuideInfo[] getGuideInfoList() {
        return new GuideInfo[]{
                new GuideInfo(1, R.drawable.guide_img_1, R.string.guide_intro_1),
                new GuideInfo(2, R.drawable.guide_img_2, R.string.guide_intro_2),
                new GuideInfo(3, R.drawable.guide_img_3, R.string.guide_intro_3),
                new GuideInfo(4, R.drawable.guide_img_4, R.string.guide_intro_4),
                new GuideInfo(5, R.drawable.guide_img_5, R.string.guide_intro_5),
                new GuideInfo(6, R.drawable.guide_img_6, R.string.guide_intro_6)
        };
    }
}
