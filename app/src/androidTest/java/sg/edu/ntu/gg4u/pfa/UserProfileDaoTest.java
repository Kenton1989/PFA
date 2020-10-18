package sg.edu.ntu.gg4u.pfa;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


import io.reactivex.Flowable;
import io.reactivex.Scheduler;

import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.persistence.Database;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfileDao;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserProfileDaoTest {
    private UserProfileDao userProfileDao;
    private Database db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, Database.class).build();
        userProfileDao = db.userProfileDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeAndReadUserProfileTest() {
          System.out.println("Test Started...");
        UserProfile userProfile = TestUtil.createUserProfile();
        String name = "Peter Wen";
        userProfile.setName(name);



        System.out.println("Updating profile...");
        userProfileDao.updateUserProfile(userProfile);

        System.out.println("Getting profile...");
        Flowable<UserProfile> returnFlowable = userProfileDao.getUserProfile();

        System.out.println("Loading profile...");
        UserProfile returnProfile = returnFlowable.blockingFirst();

        System.out.println("Checking correctness...");
        assertThat(returnProfile.getName(), equalTo(name));
    }
}
