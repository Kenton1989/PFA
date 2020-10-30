package sg.edu.ntu.gg4u.pfa.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import sg.edu.ntu.gg4u.pfa.ui.category.CategoryViewModel;
import sg.edu.ntu.gg4u.pfa.ui.guide.GuideInfoViewModel;
import sg.edu.ntu.gg4u.pfa.ui.home.HomeViewModel;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;
import sg.edu.ntu.gg4u.pfa.ui.record.RecordViewModel;
import sg.edu.ntu.gg4u.pfa.ui.report.ReportViewModel;
import sg.edu.ntu.gg4u.pfa.ui.target.TargetViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProfileDataSource mProfileDataSource;
    private final TargetDataSource mTargetDataSource;
    private final RecordDataSource mRecordDataSource;
    private final GuideInfoDataSource mGuideInfoDataSource;
    private final CategoryDataSource mCategoryDataSource;

    public ViewModelFactory(ProfileDataSource profileDataSource,
                            TargetDataSource targetDataSource,
                            RecordDataSource recordDataSource,
                            GuideInfoDataSource guideInfoDataSource,
                            CategoryDataSource categoryDataSource) {
        mProfileDataSource = profileDataSource;
        mTargetDataSource = targetDataSource;
        mRecordDataSource = recordDataSource;
        mGuideInfoDataSource = guideInfoDataSource;
        mCategoryDataSource = categoryDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mProfileDataSource);
        }
        if (modelClass.isAssignableFrom(TargetViewModel.class)) {
            return (T) new TargetViewModel(mTargetDataSource, mRecordDataSource);
        }
        if (modelClass.isAssignableFrom(RecordViewModel.class)) {
            return (T) new RecordViewModel(mRecordDataSource);
        }
        if (modelClass.isAssignableFrom(GuideInfoViewModel.class)) {
            return (T) new GuideInfoViewModel(mGuideInfoDataSource);
        }
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(mCategoryDataSource);
        }
        if (modelClass.isAssignableFrom(ReportViewModel.class)) {
            return (T) new ReportViewModel
                    (mRecordDataSource, mTargetDataSource, mProfileDataSource, mCategoryDataSource);
        }
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel
                    (mRecordDataSource, mTargetDataSource, mCategoryDataSource);
        }
        if (modelClass.isAssignableFrom(InitViewModel.class)) {
            return (T) new InitViewModel(mProfileDataSource, mCategoryDataSource,
                    mRecordDataSource, mTargetDataSource);
        }
        return null;
    }
}
