package sg.edu.ntu.gg4u.pfa.ui.guide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import sg.edu.ntu.gg4u.pfa.R;
import sg.edu.ntu.gg4u.pfa.ui.profile.EditProfileActivity;

/**
 *
 */

public class GuideEndFragment extends Fragment {

    Drawable mImgRes;
    CharSequence mIntro;
    Activity mActivity;

    public GuideEndFragment(Drawable imgRes, CharSequence intro, Activity activity) {
        mImgRes = imgRes;
        mIntro = intro;
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_guide_end, container, false);

        TextView textView = (TextView) root.findViewById(R.id.intro_text);
        ImageView imgView = (ImageView) root.findViewById(R.id.intro_img);

        imgView.setImageDrawable(mImgRes);
        textView.setText(mIntro);

        Button startButton = (Button) root.findViewById(R.id.start_button);
        Button gotoProfileButton = (Button) root.findViewById(R.id.edit_profile_button);

        startButton.setOnClickListener(this::closeGuideActivity);
        gotoProfileButton.setOnClickListener(this::gotoEditProfile);
        return root;
    }

    public void closeGuideActivity(View view) {
        mActivity.finish();
    }

    public void gotoEditProfile(View view) {
        Intent intent = new Intent(mActivity, EditProfileActivity.class);

        mActivity.finish();

        startActivity(intent);
    }
}