package sg.edu.ntu.gg4u.pfa.ui.record;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;

public class RecordViewModel extends ViewModel {

    private final RecordDataSource mDataSource;
    
    public RecordViewModel(RecordDataSource recordDataSource) {
        mDataSource = recordDataSource;
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end) {
        return mDataSource.getRecord(start, end);
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end, String name) {
        return mDataSource.getRecord(start, end, name);
    }

    public Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end) {
        return mDataSource.getRecordSum(start, end);
    }

    public Completable addRecord(Record record) {
        return mDataSource.addRecord(record);
    }

    public Completable deleteRecord(Record record) {
        return mDataSource.deleteRecord(record);
    }
}