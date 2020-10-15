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

    private Integer imgPath;
    private String textIntro;


    public GuideInfo() {
        pageNumber = -1;
        imgPath = 0;
        textIntro = "dummy";
    }

    @Ignore
    public GuideInfo(@NonNull Integer number, Integer imgPath, String intro) {
        this.imgPath = imgPath;
        textIntro = intro;
        pageNumber = number;
    }

    public Integer getImgPath() {
        return imgPath;
    }

    @NonNull
    public Integer getPageNumber() {
        return pageNumber;
    }

    public String getTextIntro() {
        return textIntro;
    }

    public void setImgPath(Integer imgPath) {
        this.imgPath = imgPath;
    }

    public void setPageNumber(@NonNull Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTextIntro(String textIntro) {
        this.textIntro = textIntro;
    }
}
