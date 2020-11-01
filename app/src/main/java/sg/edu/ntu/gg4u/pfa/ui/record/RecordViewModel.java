package sg.edu.ntu.gg4u.pfa.ui.record;

import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Record.SumByCategory;
import sg.edu.ntu.gg4u.pfa.ui.CategoryDataSource;
import sg.edu.ntu.gg4u.pfa.ui.RecordDataSource;

public class RecordViewModel extends ViewModel {

    private final RecordDataSource mRecordDataSource;
    private final CategoryDataSource mCategoryDataSource;
    
    public RecordViewModel(RecordDataSource recordDataSource, CategoryDataSource categoryDataSource) {
        mRecordDataSource = recordDataSource;
        mCategoryDataSource = categoryDataSource;
    }

    public Flowable<List<Record>> getRecord(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getRecord(start, end);
    }

    public Flowable<List<Record>> getRecordByCategory(LocalDateTime start, LocalDateTime end, String name) {
        return mRecordDataSource.getRecord(start, end, name);
    }

    public Flowable<Double> getRecordSum(LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getRecordSum(start, end);
    }

    public Flowable<Double> getRecordSumByCategory
            (LocalDateTime start, LocalDateTime end, String name){
        return mRecordDataSource.getRecordSum(start, end, name);
    }

    public Flowable<List<SumByCategory>> getGroupedRecordSum
            (LocalDateTime start, LocalDateTime end) {
        return mRecordDataSource.getGroupedRecordSum(start, end);
    }

    public Completable addRecord(Record record) {
        return mRecordDataSource.addRecord(record);
    }

    public Completable deleteRecord(Record record) {
        return mRecordDataSource.deleteRecord(record);
    }

    public Flowable<List<Category>> getAllCategory() {
        return mCategoryDataSource.getAllCategory();
    }
}