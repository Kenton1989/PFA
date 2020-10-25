package sg.edu.ntu.gg4u.pfa.ui;

import android.content.Context;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Database;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;
import sg.edu.ntu.gg4u.pfa.ui.category.LocalCategoryDataSource;
import sg.edu.ntu.gg4u.pfa.ui.guide.LocalGuideInfoDataSource;
import sg.edu.ntu.gg4u.pfa.ui.profile.LocalProfileDataSource;
import sg.edu.ntu.gg4u.pfa.ui.record.LocalRecordDataSource;
import sg.edu.ntu.gg4u.pfa.ui.target.LocalTargetDataSource;

public class Injection {

    public static ProfileDataSource provideProfileDataSource(Context context) {
        Database database = Database.getINSTANCE(context);
        return new LocalProfileDataSource(database.userProfileDao());
    }

    public static TargetDataSource provideTargetDataSource(Context context) {
        Database database = Database.getINSTANCE(context);
        return new LocalTargetDataSource(database.targetDao());
    }

    public static RecordDataSource provideRecordDataSource(Context context) {
        Database database = Database.getINSTANCE(context);
        return new LocalRecordDataSource(database.recordDao());
    }

    public static GuideInfoDataSource provideGuideInfoDataSource(Context context) {
        Database database = Database.getINSTANCE(context);
        return new LocalGuideInfoDataSource(database.guideInfoDao());
    }

    public static CategoryDataSource provideCategoryDataSource(Context context) {
        Database database = Database.getINSTANCE(context);
        return new LocalCategoryDataSource(database.categoryDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {

        ProfileDataSource profileDataSource = provideProfileDataSource(context);
        TargetDataSource targetDataSource = provideTargetDataSource(context);
        RecordDataSource recordDataSource = provideRecordDataSource(context);
        GuideInfoDataSource guideInfoDataSource = provideGuideInfoDataSource(context);
        CategoryDataSource categoryDataSource = provideCategoryDataSource(context);


        return new ViewModelFactory(profileDataSource, targetDataSource, recordDataSource,
                guideInfoDataSource, categoryDataSource);
    }
}
