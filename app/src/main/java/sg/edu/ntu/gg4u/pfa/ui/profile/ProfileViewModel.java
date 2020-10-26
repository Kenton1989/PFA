package sg.edu.ntu.gg4u.pfa.ui.profile;

import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.ProfileDataSource;

public class ProfileViewModel extends ViewModel {

    private final ProfileDataSource mDataSource;

    public ProfileViewModel(ProfileDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<UserProfile> getUserProfile() {
        return mDataSource.getUserProfile();
    }

    public Flowable<Double> getIncomeSum() {
        return mDataSource.getIncomeSum();
    }

    public Completable updateUserProfile(final UserProfile userProfile) {
        return mDataSource.updateUserProfile(userProfile);
    }
}
