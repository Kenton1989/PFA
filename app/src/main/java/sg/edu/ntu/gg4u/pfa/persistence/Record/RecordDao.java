package sg.edu.ntu.gg4u.pfa.persistence.Record;

import androidx.room.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;

import io.reactivex.Flowable;

public interface RecordDao {
    @Query("SELECT * FROM Record WHERE timestamp > :start " +
            "AND timestamp < :end")
    Flowable<ArrayList<Record>> getRecord(LocalDateTime start, LocalDateTime end);

    @Query("SELECT * FROM Record WHERE timestamp > :start " +
            "AND timestamp < :end AND categoryName = :name")
    Flowable<ArrayList<Record>> getRecord(LocalDateTime start, LocalDateTime end,
                                          String name);
}
