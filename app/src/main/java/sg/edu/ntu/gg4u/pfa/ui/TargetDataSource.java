package sg.edu.ntu.gg4u.pfa.ui;

import java.time.LocalDate;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;

public interface TargetDataSource {
    Flowable<List<Target>> getTarget(String name, LocalDate startDate,
                                     LocalDate endDate);

    Flowable<List<Target>> getTarget(String name, LocalDate startDate);

    Flowable<Target> getCurrentTarget(String name);

    Completable insertOrUpdateTarget(Target target);

    Completable deleteTarget(Target target);
}
