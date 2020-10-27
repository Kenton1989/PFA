package sg.edu.ntu.gg4u.pfa.ui;

import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;

public class InitViewModel extends ViewModel {

    private final ProfileDataSource mProfileDataSource;

    public InitViewModel(ProfileDataSource profileDataSource) {
        mProfileDataSource = profileDataSource;
    }

    public Completable updateUserProfile(final UserProfile userProfile) {
        return mProfileDataSource.updateUserProfile(userProfile);
    }
}
