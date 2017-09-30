package online.himakeit.skylark.model.zuimei;

import com.google.gson.annotations.SerializedName;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 19:46
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZuiMeiImageItem {
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String mImageUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }
}
