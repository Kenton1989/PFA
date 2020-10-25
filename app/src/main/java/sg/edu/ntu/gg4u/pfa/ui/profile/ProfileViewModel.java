package sg.edu.ntu.gg4u.pfa.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;

public class ProfileViewModel extends ViewModel {

    private final ProfileDataSource mDataSource;

    private UserProfile mUserProfile;

    public ProfileViewModel(ProfileDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<UserProfile> getUserProfile() {
        return mDataSource.getUserProfile();
    }

    public Completable updateUserProfile(final UserProfile userProfile) {
        mUserProfile = userProfile;
        return mDataSource.updateUserProfile(userProfile);
    }
}
