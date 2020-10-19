package sg.edu.ntu.gg4u.pfa.ui.guide;

import android.graphics.drawable.Drawable;
import android.media.ImageWriter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sg.edu.ntu.gg4u.pfa.R;

/**
 * A fragment containing a image and a piece of text.
 */
public class ImgStrFragment extends Fragment {

    Drawable mImgRes;
    CharSequence mIntro;

    public ImgStrFragment(Drawable imgRes, CharSequence intro) {
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