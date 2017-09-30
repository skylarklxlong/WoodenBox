package online.himakeit.skylark.model.zuimei;

import com.google.gson.annotations.SerializedName;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 19:47
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZuiMeiImageResponse {
    @SerializedName("data")
    private ZuiMeiImageData data;

    public ZuiMeiImageData getData() {
        return data;
    }

    public void setData(ZuiMeiImageData data) {
        this.data = data;
    }
}
