package sg.edu.ntu.gg4u.pfa.ui;

import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;

public interface RecordDataSource {

    Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end);

    Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end,
                                     String name);

    Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end, String name);

    Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end);

    Flowable<List<SumByCategory>> getGroupedRecordSum(LocalDateTime start, LocalDateTime end);

    Completable addRecord(Record record);

    Completable deleteRecord(Record record);
}
