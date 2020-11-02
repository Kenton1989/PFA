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
       /* Double targetAmount;
        String categoryName;
        Double cost;*/
       public String categoryName;
       public double targetAmount;
       public double cost;

        public TargetAndCost(String name, double amount , double cat_cost) {
            categoryName = name;
            targetAmount = amount;
            cost =cat_cost;
        }
        public TargetAndCost() {
            categoryName = "dummy";
            targetAmount = 10.0;
            cost =20.0;
        }
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

    @Query("With GroupedRecordSum AS (" +
            "    SELECT categoryName AS categoryName, SUM(amount) AS sum" +
            "    FROM Record " +
            "    WHERE timestamp > :startDate" +
            "      AND timestamp < :endDate" +
            "    GROUP BY categoryName" +
            ")" +
            "SELECT Target.amount AS targetAmount, Category.name AS categoryName," +
            " GroupedRecordSum.sum AS cost " +
            "FROM Category " +
            "LEFT JOIN GroupedRecordSum " +
            "ON Category.name = GroupedRecordSum.categoryName " +
            "LEFT JOIN Target " +
            "ON Target.categoryName = Category.name " +
            "   AND Target.startDate = :startDate " +
            "ORDER BY targetAmount DESC ")
    Flowable<List<TargetAndCost>> getTargetAndCost(LocalDate startDate, LocalDate endDate);
}
