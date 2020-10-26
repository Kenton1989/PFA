package sg.edu.ntu.gg4u.pfa.ui;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;

public interface ProfileDataSource {
    Flowable<UserProfile> getUserProfile();
    Flowable<Double> getIncomeSum();
    Completable updateUserProfile(UserProfile userProfile);
}
