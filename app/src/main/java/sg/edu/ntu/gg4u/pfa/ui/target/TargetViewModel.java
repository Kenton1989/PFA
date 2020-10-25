package sg.edu.ntu.gg4u.pfa.ui.target;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileDataSource;

public class TargetViewModel extends ViewModel {
    private final TargetDataSource mDataSource;

    public TargetViewModel(TargetDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<List<Target>> getTargetByPeriod
            (String name, LocalDate startDate, LocalDate endDate) {
        return mDataSource.getTarget(name, startDate, endDate);
    }

    public Flowable<List<Target>> getTargetByStartTime
            (String name, LocalDate startDate) {
        return mDataSource.getTarget(name, startDate);
    }

    public Flowable<Target> getCurrentTargetByName(String name) {
        return mDataSource.getCurrentTarget(name);
    }

    public Completable insertOrUpdateTarget(Target target) {
        return mDataSource.insertOrUpdateTarget(target);
    }

    public Completable deleteTarget(Target target) {
        return  mDataSource.deleteTarget(target);
    }
}