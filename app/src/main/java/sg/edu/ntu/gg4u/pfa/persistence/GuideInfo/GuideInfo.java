package sg.edu.ntu.gg4u.pfa.persistence.GuideInfo;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import sg.edu.ntu.gg4u.pfa.R;

@Entity(tableName = "GuideInfo")
public class GuideInfo {

    @NonNull
    @PrimaryKey
    private Integer pageNumber;

    @DrawableRes
    private Integer imgRes;

    @StringRes
    private Integer textIntroRes;


    public GuideInfo() {
        pageNumber = -1;
        imgRes = R.drawable.dummy;
        textIntroRes = R.string.dummy;
    }

    @Ignore
    public GuideInfo(@NonNull Integer number, Integer imgRes, Integer intro) {
        this.imgRes = imgRes;
        textIntroRes = intro;
        pageNumber = number;
    }

    @DrawableRes
    public Integer getImgRes() {
        return imgRes;
    }

    @NonNull
    public Integer getPageNumber() {
        return pageNumber;
    }

    @StringRes
    public Integer getTextIntroRes() {
        return textIntroRes;
    }

    public void setImgRes(Integer imgRes) {
        this.imgRes = imgRes;
    }

    public void setPageNumber(@NonNull Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTextIntroRes(Integer textIntroRes) {
        this.textIntroRes = textIntroRes;
    }
}
