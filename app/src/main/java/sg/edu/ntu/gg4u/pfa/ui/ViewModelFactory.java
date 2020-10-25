package sg.edu.ntu.gg4u.pfa.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;
import sg.edu.ntu.gg4u.pfa.ui.target.TargetViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProfileDataSource mProfileDataSource;
    private final TargetDataSource mTargetDataSource;

    public ViewModelFactory(ProfileDataSource profileDataSource,
                            TargetDataSource targetDataSource) {
        mProfileDataSource = profileDataSource;
        mTargetDataSource = targetDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mProfileDataSource);
        }
        if (modelClass.isAssignableFrom(TargetViewModel.class)) {
            return (T) new TargetViewModel(mTargetDataSource);
        }

        return null;
    }
}
