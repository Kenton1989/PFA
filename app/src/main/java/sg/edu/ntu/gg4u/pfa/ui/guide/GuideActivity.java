package sg.edu.ntu.gg4u.pfa.ui.guide;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.persistence.GuideInfo.GuideInfo;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.guide.SectionsPagerAdapter;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class GuideActivity extends AppCompatActivity {

    private static final String TAG = "ui.GuideActivity";

    private ViewModelFactory mViewModelFactory;

    private GuideInfoViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.guide_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.guide_tabs);
        tabs.setupWithViewPager(viewPager);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(GuideInfoViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getGuideInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::whenGuideInfoUpdated));
    }

    private void whenGuideInfoUpdated(List<GuideInfo> guideInfos) {

    }
}