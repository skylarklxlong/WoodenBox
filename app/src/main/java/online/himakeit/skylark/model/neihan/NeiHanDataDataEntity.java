package online.himakeit.skylark.model.neihan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/9 17:51
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanDataDataEntity implements Serializable {
    @SerializedName("comments")
    private ArrayList<String> comments;
    @SerializedName("display_time")
    private int display_time;
    @SerializedName("group")
    private NeiHanDataDataGroupEntity group;
    @SerializedName("online_time")
    private int online_time;
    @SerializedName("type")
    private int type;

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public int getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(int display_time) {
        this.display_time = display_time;
    }

    public NeiHanDataDataGroupEntity getGroup() {
        return group;
    }

    public void setGroup(NeiHanDataDataGroupEntity group) {
        this.group = group;
    }

    public int getOnline_time() {
        return online_time;
    }

    public void setOnline_time(int online_time) {
        this.online_time = online_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
