package online.himakeit.skylark.model.zhuhu;

import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 19:07
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
@Table("zhihu")
public class ZhiHuDailyItem implements Serializable{

    @Column("images")
    @SerializedName("images")
    private String[] images;
    @Column("type")
    @SerializedName("type")
    private int type;
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("_id")
    @SerializedName("id")
    private String id;
    @Column("title")
    @SerializedName("title")
    private String title;
    @Column("date")
    @SerializedName("date")
    private String date;

    public boolean hasFadedIn = false;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
