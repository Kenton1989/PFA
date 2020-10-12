package sg.edu.ntu.gg4u.pfa.persistence.GuideInfo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "GuideInfo")
public class GuideInfo {

    @NonNull
    @PrimaryKey
    private Integer pageNumber;

    private int imgPath;
    private String textIntro;

    @Ignore
    public GuideInfo() {
        pageNumber = -1;
        imgPath = 0;
        textIntro = "dummy";
    }

    public GuideInfo(int number, int imgPath, String intro) {
        this.imgPath = imgPath;
        textIntro = intro;
        pageNumber = number;
    }

    public int getImgPath() {
        return imgPath;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getTextIntro() {
        return textIntro;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTextIntro(String textIntro) {
        this.textIntro = textIntro;
    }
}
