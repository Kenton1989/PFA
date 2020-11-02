package sg.edu.ntu.gg4u.pfa.ui.guide;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import sg.edu.ntu.gg4u.pfa.persistence.Database;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Activity mActivity;
    private final GuideInfo[] mGuideInfoList;

    public SectionsPagerAdapter(Activity activity, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mActivity = activity;
        mGuideInfoList = Database.getINSTANCE(mActivity).getGuideInfoList();
    }

    @Override
    public Fragment getItem(int position) {
        GuideInfo page = mGuideInfoList[position];

        Drawable drawable = ResourcesCompat.getDrawable(mActivity.getResources(),
                page.getImgRes(), null);
        CharSequence introString = mActivity.getString(page.getTextIntroRes());


        Fragment fragment = null;

        if (position == mGuideInfoList.length - 1) {
            fragment = new GuideEndFragment(drawable, introString, mActivity);
        } else {
            fragment = new ImgStrFragment(drawable, introString);
        }
        
        return fragment;
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