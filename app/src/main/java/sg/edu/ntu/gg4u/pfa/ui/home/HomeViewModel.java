package sg.edu.ntu.gg4u.pfa.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;
import sg.edu.ntu.gg4u.pfa.ui.TargetDataSource;

public class HomeViewModel extends ViewModel {

    private final RecordDataSource mRecordDataSource;
    private final TargetDataSource mTargetDataSource;

    public HomeViewModel(RecordDataSource recordDataSource, TargetDataSource targetDataSource) {
        mRecordDataSource = recordDataSource;
        mTargetDataSource = targetDataSource;
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getRecord(start, end);
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end, String name) {
        return mRecordDataSource.getRecord(start, end, name);
    }

    public Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getRecordSum(start, end);
    }

    public Completable addRecord(Record record) {
        return mRecordDataSource.addRecord(record);
    }

    public Completable deleteRecord(Record record) {
        return mRecordDataSource.deleteRecord(record);
    }

    public Flowable<Target> getCurrentTarget(String name) {
        return mTargetDataSource.getCurrentTarget(name);
    }

    public Completable insertOrUpdateTarget(Target target) {
        return mTargetDataSource.insertOrUpdateTarget(target);
    }

    public Completable deleteTarget(Target target) {
        return mTargetDataSource.deleteTarget(target);
    }
}