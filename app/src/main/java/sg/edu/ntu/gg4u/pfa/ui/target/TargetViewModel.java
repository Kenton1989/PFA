package sg.edu.ntu.gg4u.pfa.ui.target;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.Target.TargetDao;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;
import sg.edu.ntu.gg4u.pfa.ui.TargetDataSource;

public class TargetViewModel extends ViewModel {
    private final TargetDataSource mTargetDataSource;
    private final RecordDataSource mRecordDataSource;

    public TargetViewModel(TargetDataSource targetDataSource, RecordDataSource recordDataSource) {
        mTargetDataSource = targetDataSource;
        mRecordDataSource = recordDataSource;
    }

    public Flowable<List<Target>> getTargetByPeriod
            (String name, LocalDate startDate, LocalDate endDate) {
        return mTargetDataSource.getTarget(name, startDate, endDate);
    }

    public Flowable<List<Target>> getTargetByStartTime
            (String name, LocalDate startDate) {
        return mTargetDataSource.getTarget(name, startDate);
    }

    public Flowable<Target> getCurrentTargetByName(String name) {
        return mTargetDataSource.getCurrentTarget(name);
    }

    public Flowable<List<Target>> getAllCurrentTarget(LocalDate date) {
        return mTargetDataSource.getAllCurrentTarget(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Flowable<List<TargetDao.TargetAndCost>> getTargetAndCost(LocalDate startDate) {
        return mTargetDataSource.getTargetAndCost(startDate, startDate.plusMonths(1));
    }

    public Completable insertOrUpdateTarget(Target target) {
        return mTargetDataSource.insertOrUpdateTarget(target);
    }

    public Completable deleteTarget(Target target) {
        return  mTargetDataSource.deleteTarget(target);
    }

    public Flowable<List<SumByCategory>> getGroupedRecordSum(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getGroupedRecordSum(start, end);
    }
}