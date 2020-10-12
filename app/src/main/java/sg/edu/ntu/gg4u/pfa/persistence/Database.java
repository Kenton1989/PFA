package sg.edu.ntu.gg4u.pfa.persistence;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.GenderConverter;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfileDao;

@androidx.room.Database(entities = {UserProfile.class}, version = 1)
@TypeConverters({GenderConverter.class})
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

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
