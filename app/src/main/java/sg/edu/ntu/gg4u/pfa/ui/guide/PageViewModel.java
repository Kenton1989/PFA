package sg.edu.ntu.gg4u.pfa.ui.guide;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;
import sg.edu.ntu.gg4u.pfa.ui.GuideInfoDataSource;

public class PageViewModel extends ViewModel {

    private final GuideInfoDataSource mDataSource;

    public PageViewModel(GuideInfoDataSource guideInfoDataSource) {
        mDataSource = guideInfoDataSource;
    }

    public Flowable<List<GuideInfo>> getGuideInfo() {
        return mDataSource.getGuideInfo();
    }
}