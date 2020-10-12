package sg.edu.ntu.gg4u.pfa.persistence;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {UserProfile.class}, version = 1)
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
