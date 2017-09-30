package online.himakeit.skylark.model.zuimei;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 19:47
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZuiMeiImageData {
    @SerializedName("images")
    private ArrayList<ZuiMeiImageItem> images;

    public ArrayList<ZuiMeiImageItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<ZuiMeiImageItem> images) {
        this.images = images;
    }
}
