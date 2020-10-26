package sg.edu.ntu.gg4u.pfa.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.RecordDao;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.ui.CategoryDataSource;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;
import sg.edu.ntu.gg4u.pfa.ui.TargetDataSource;

public class HomeViewModel extends ViewModel {

    private final RecordDataSource mRecordDataSource;
    private final TargetDataSource mTargetDataSource;
    private final CategoryDataSource mCategoryDataSource;

    public HomeViewModel(RecordDataSource recordDataSource, TargetDataSource targetDataSource,
                         CategoryDataSource categoryDataSource) {
        mRecordDataSource = recordDataSource;
        mTargetDataSource = targetDataSource;
        mCategoryDataSource = categoryDataSource;
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

    public Flowable<Double> getRecordSumByCategory
            (LocalDateTime start, LocalDateTime end, String name){
        return mRecordDataSource.getRecordSum(start, end, name);
    }

    public Flowable<List<RecordDao.SumByCategory>> getGroupedRecordSum
            (LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getGroupedRecordSum(start, end);
    }

    public Flowable<Target> getCurrentTarget(String name) {
        return mTargetDataSource.getCurrentTarget(name);
    }

    public Flowable<List<Category>> getAllCategory() {
        return mCategoryDataSource.getAllCategory();
    }

}