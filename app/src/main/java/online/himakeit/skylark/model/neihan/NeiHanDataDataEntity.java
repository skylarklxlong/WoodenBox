package online.himakeit.skylark.model.neihan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/9 17:51
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanDataDataEntity implements Serializable {
    @SerializedName("display_time")
    private String display_time;
    @SerializedName("group")
    private NeiHanDataDataGroupEntity group;
    @SerializedName("online_time")
    private String online_time;
    @SerializedName("type")
    private int type;

    public String getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public String getOnline_time() {
        return online_time;
    }

    public void setOnline_time(String online_time) {
        this.online_time = online_time;
    }

    public NeiHanDataDataGroupEntity getGroup() {
        return group;
    }

    public void setGroup(NeiHanDataDataGroupEntity group) {
        this.group = group;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
