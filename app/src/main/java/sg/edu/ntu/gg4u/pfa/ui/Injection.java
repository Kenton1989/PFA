package sg.edu.ntu.gg4u.pfa.ui;

import android.content.Context;

import sg.edu.ntu.gg4u.pfa.persistence.Database;
import sg.edu.ntu.gg4u.pfa.ui.profile.LocalProfileDataSource;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileDataSource;

public class Injection {

    public static ProfileDataSource provideProfileDataSource(Context context) {
        Database database = Database.getINSTANCE(context);
        return new LocalProfileDataSource(database.userProfileDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ProfileDataSource profileDataSource = provideProfileDataSource(context);
        return new ViewModelFactory(profileDataSource);
    }
}
