package sg.edu.ntu.gg4u.pfa.ui.guide;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.Database;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final GuideInfo[] mGuideInfoList;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mGuideInfoList = Database.getINSTANCE(mContext).getGuideInfoList();
    }

    @Override
    public Fragment getItem(int position) {
        GuideInfo page = mGuideInfoList[position];

        Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(),
                page.getImgRes(), null);
        CharSequence introString = mContext.getString(page.getTextIntroRes());
        
        return new ImgStrFragment(drawable, introString);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mGuideInfoList.length;
    }
}