package sg.edu.ntu.gg4u.pfa.ui;

import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;

public class InitViewModel extends ViewModel {

    private final ProfileDataSource mProfileDataSource;
    private final CategoryDataSource mCategoryDataSource;
    private final RecordDataSource mRecordDataSource;
    private final TargetDataSource mTargetDataSource;

    public InitViewModel(ProfileDataSource profileDataSource,
                         CategoryDataSource categoryDataSource,
                         RecordDataSource recordDataSource,
                         TargetDataSource targetDataSource) {
        mProfileDataSource = profileDataSource;
        mCategoryDataSource = categoryDataSource;
        mRecordDataSource = recordDataSource;
        mTargetDataSource = targetDataSource;
    }

    public Completable updateUserProfile(final UserProfile userProfile) {
        return mProfileDataSource.updateUserProfile(userProfile);
    }

    public Completable updateCategory(final Category category) {
        return mCategoryDataSource.createNewCategory(category);
    }

    public Completable updateRecord(final Record record) {
        return mRecordDataSource.addRecord(record);
    }

    public Completable updateTarget(final Target target) {
        return mTargetDataSource.insertOrUpdateTarget(target);
    }
}
