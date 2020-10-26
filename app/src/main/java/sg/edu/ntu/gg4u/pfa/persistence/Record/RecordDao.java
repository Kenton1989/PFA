package sg.edu.ntu.gg4u.pfa.persistence.Record;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface RecordDao {
    @Query("SELECT * FROM Record WHERE timestamp > :start " +
            "AND timestamp < :end")
    Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end);

    @Query("SELECT * FROM Record WHERE timestamp > :start " +
            "AND timestamp < :end AND categoryName = :name")
    Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end,
                                          String name);

    @Query("SELECT SUM(amount) FROM Record WHERE timestamp > :start AND timestamp < :end")
    Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addRecord(Record record);

    @Query("DELETE FROM Record WHERE timestamp = :timestamp")
    Completable deleteRecord(LocalDateTime timestamp);
}
