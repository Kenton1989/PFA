package sg.edu.ntu.gg4u.pfa.ui.record;

import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.RecordDao;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;

public class LocalRecordDataSource implements RecordDataSource {

    private final RecordDao mDao;

    public LocalRecordDataSource(RecordDao recordDao) {
        mDao = recordDao;
    }

    @Override
    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end) {
        return mDao.getRecord(start, end);
    }

    @Override
    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end, String name) {
        return mDao.getRecord(start, end, name);
    }

    @Override
    public Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end) {
        return mDao.getRecordSum(start, end);
    }

    @Override
    public Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end, String name){
        return mDao.getRecordSum(start, end, name);
    }

    @Override
    public Completable addRecord(Record record) {
        return mDao.addRecord(record);
    }

    @Override
    public Completable deleteRecord(Record record) {
        return mDao.deleteRecord(record.getTimestamp());
    }
}
