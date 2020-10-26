package sg.edu.ntu.gg4u.pfa.ui.profile;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfileDao;
import sg.edu.ntu.gg4u.pfa.ui.ProfileDataSource;

public class LocalProfileDataSource implements ProfileDataSource {

    private final UserProfileDao mUserProfileDao;

    public LocalProfileDataSource(UserProfileDao dao) {
        mUserProfileDao = dao;
    }

    @Override
    public Flowable<UserProfile> getUserProfile() {
        return mUserProfileDao.getUserProfile();
    }

    @Override
    public Completable updateUserProfile(UserProfile userProfile) {
        return mUserProfileDao.updateUserProfile(userProfile);
    }
}
