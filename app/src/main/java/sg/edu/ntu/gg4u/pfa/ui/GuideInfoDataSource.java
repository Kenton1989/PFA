package sg.edu.ntu.gg4u.pfa.ui;

import java.util.List;

import io.reactivex.Flowable;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;

public interface GuideInfoDataSource {
    Flowable<List<GuideInfo>> getGuideInfo();
}
