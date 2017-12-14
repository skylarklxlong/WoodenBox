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
    private long display_time;
    @SerializedName("group")
    private NeiHanDataDataGroupEntity group;
    @SerializedName("online_time")
    private long online_time;
    @SerializedName("type")
    private int type;

    public long getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(long display_time) {
        this.display_time = display_time;
    }

    public long getOnline_time() {
        return online_time;
    }

    public void setOnline_time(long online_time) {
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
