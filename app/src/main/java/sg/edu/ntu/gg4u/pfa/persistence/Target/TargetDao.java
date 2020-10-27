package sg.edu.ntu.gg4u.pfa.persistence.Target;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TargetDao {

    class TargetAndCost {
        Target target;
        Double cost;
    }

    @Query("SELECT * FROM Target WHERE categoryName = :name AND" +
            " startDate >= :startDate AND startDate <= :endDate")
    Flowable<List<Target>> getTarget(String name, LocalDate startDate,
                                     LocalDate endDate);

    @Query("SELECT * FROM Target WHERE categoryName = :name AND startDate >= :startDate")
    Flowable<List<Target>> getTarget(String name, LocalDate startDate);

    @Query("SELECT * FROM Target WHERE startDate = " +
            "(SELECT MAX(startDate) FROM Target WHERE categoryName = :name)" +
            "AND categoryName = :name LIMIT 1")
    Flowable<Target> getCurrentTarget(String name);

    @Query("SELECT * FROM Target WHERE startDate = :date")
    Flowable<List<Target>> getAllCurrentTarget(LocalDate date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable setCurrentTarget(Target target);

    @Query("DELETE FROM Target WHERE categoryName = :name AND startDate = :date")
    Completable deleteTarget(String name, LocalDate date);

    @Query("With GroupedRecordSum as (" +
            "    SELECT categoryName AS categoryName, SUM(amount) AS sum" +
            "    FROM Category" +
            "             left outer join Record on Record.categoryName = Category.name" +
            "    WHERE timestamp > :startDate" +
            "      AND timestamp < :endDate" +
            "    GROUP BY categoryName" +
            ")" +
            "select Target.amount, Target.categoryName, GroupedRecordSum.sum " +
            "from Target, GroupedRecordSum " +
            "WHERE Target.categoryName = GroupedRecordSum.categoryName " +
            "AND Target.startDate = :startDate")
    Flowable<List<TargetAndCost>> getTargetAndCost(LocalDate startDate, LocalDate endDate);
}
