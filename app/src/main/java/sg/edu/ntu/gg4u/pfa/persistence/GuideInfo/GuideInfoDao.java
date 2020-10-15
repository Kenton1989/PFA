package sg.edu.ntu.gg4u.pfa.persistence.GuideInfo;

import androidx.constraintlayout.helper.widget.Flow;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface GuideInfoDao {
    @Query("SELECT * FROM GuideInfo")
    Flowable<List<GuideInfo>> getGuideInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable updateGuideInfo(GuideInfo guideInfo);

    @Query("DELETE FROM GuideInfo WHERE pageNumber = :pageNum")
    void deleteGuideInfo(int pageNum);
}
