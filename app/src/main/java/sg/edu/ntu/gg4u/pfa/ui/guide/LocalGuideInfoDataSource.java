package sg.edu.ntu.gg4u.pfa.ui.guide;

import java.util.List;

import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfoDao;
import sg.edu.ntu.gg4u.pfa.ui.GuideInfoDataSource;

public class LocalGuideInfoDataSource implements GuideInfoDataSource {

    private final GuideInfoDao mDao;

    public LocalGuideInfoDataSource(GuideInfoDao guideInfoDao) {
        mDao = guideInfoDao;
    }

    @Override
    public Flowable<List<GuideInfo>> getGuideInfo() {
        return mDao.getGuideInfo();
    }
}
