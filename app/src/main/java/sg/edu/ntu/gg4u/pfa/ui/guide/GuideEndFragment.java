package sg.edu.ntu.gg4u.pfa.ui.guide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import sg.edu.ntu.gg4u.pfa.R;

/**
 *
 */

public class GuideEndFragment extends Fragment {

    Drawable mImgRes;
    CharSequence mIntro;

    public GuideEndFragment(Drawable imgRes, CharSequence intro) {
        mImgRes = imgRes;
        mIntro = intro;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_guide, container, false);

        TextView textView = (TextView) root.findViewById(R.id.intro_text);
        ImageView imgView = (ImageView) root.findViewById(R.id.intro_img);

        imgView.setImageDrawable(mImgRes);
        textView.setText(mIntro);
        return root;
    }
}