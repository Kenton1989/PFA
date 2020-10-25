package sg.edu.ntu.gg4u.pfa.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileDataSource;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProfileDataSource mProfileDataSource;

    public ViewModelFactory(ProfileDataSource profileDataSource) {
        mProfileDataSource = profileDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mProfileDataSource);
        }

        return null;
    }
}
