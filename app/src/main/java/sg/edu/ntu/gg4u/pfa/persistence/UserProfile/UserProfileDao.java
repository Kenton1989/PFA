package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface UserProfileDao {
    @Query("SELECT * FROM UserProfile")
    List<UserProfile> getUserProfile();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable updateUserProfile(UserProfile userProfile);

    @Query("DELETE FROM UserProfile")
    void deleteUserProfile();
}
