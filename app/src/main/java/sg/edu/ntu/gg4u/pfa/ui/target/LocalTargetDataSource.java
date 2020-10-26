package sg.edu.ntu.gg4u.pfa.ui.target;

import java.time.LocalDate;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.Target.TargetDao;
import sg.edu.ntu.gg4u.pfa.ui.TargetDataSource;

public class LocalTargetDataSource implements TargetDataSource {

    private final TargetDao mTargetDao;

    public LocalTargetDataSource(TargetDao targetDao) {
        mTargetDao = targetDao;
    }

    @Override
    public Flowable<List<Target>> getTarget(String name, LocalDate startDate, LocalDate endDate) {
        return mTargetDao.getTarget(name, startDate, endDate);
    }

    @Override
    public Flowable<List<Target>> getTarget(String name, LocalDate startDate) {
        return mTargetDao.getTarget(name, startDate);
    }

    @Override
    public Flowable<Target> getCurrentTarget(String name) {
        return mTargetDao.getCurrentTarget(name);
    }

    @Override
    public Flowable<List<Target>> getAllCurrentTarget(LocalDate date) {
        return mTargetDao.getAllCurrentTarget(date);
    }

    @Override
    public Completable insertOrUpdateTarget(Target target) {
        return mTargetDao.setCurrentTarget(target);
    }

    @Override
    public Completable deleteTarget(Target target) {
        return mTargetDao.deleteTarget(target.getCategoryName(), target.getStartDate());
    }
}
