package sg.edu.ntu.gg4u.pfa.ui.report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.CategoryDataSource;
import sg.edu.ntu.gg4u.pfa.ui.ProfileDataSource;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;
import sg.edu.ntu.gg4u.pfa.ui.TargetDataSource;

public class ReportViewModel extends ViewModel {

    private final RecordDataSource mRecordDataSource;
    private final TargetDataSource mTargetDataSource;
    private final ProfileDataSource mProfileDataSource;
    private final CategoryDataSource mCategoryDataSource;

    public ReportViewModel(RecordDataSource recordDataSource, TargetDataSource targetDataSource,
                           ProfileDataSource profileDataSource, CategoryDataSource categoryDataSource) {
        mRecordDataSource = recordDataSource;
        mTargetDataSource = targetDataSource;
        mProfileDataSource = profileDataSource;
        mCategoryDataSource = categoryDataSource;
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getRecord(start, end);
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end,
                                     String name) {
        return mRecordDataSource.getRecord(start, end, name);
    }

    public Flowable<List<SumByCategory>> getGroupedRecordSum(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getGroupedRecordSum(start, end);
    }


    public Flowable<List<Target>> getTarget(String name, LocalDate startDate,
                                     LocalDate endDate) {
        return mTargetDataSource.getTarget(name, startDate, endDate);
    }

    public Flowable<Target> getCurrentTarget(String name) {
        return mTargetDataSource.getCurrentTarget(name);
    }

    public Flowable<List<Target>> getAllCurrentTarget(LocalDate date) {
        return mTargetDataSource.getAllCurrentTarget(date);
    }

    public Flowable<UserProfile> getUserProfile() {
        return mProfileDataSource.getUserProfile();
    }

    public Flowable<List<Category>> getAllCategory() {
        return mCategoryDataSource.getAllCategory();
    }
}