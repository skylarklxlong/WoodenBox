package online.himakeit.skylark.model.neihan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/9 17:48
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanDataEntity implements Serializable {
    @SerializedName("data")
    private ArrayList<NeiHanDataDataEntity> data;
    @SerializedName("has_more")
    private boolean has_more;
    @SerializedName("has_new_message")
    private boolean has_new_message;
    @SerializedName("max_time")
    private double max_time;
    @SerializedName("min_time")
    private int min_time;
    @SerializedName("tip")
    private String tip;

    public ArrayList<NeiHanDataDataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<NeiHanDataDataEntity> data) {
        this.data = data;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public boolean isHas_new_message() {
        return has_new_message;
    }

    public void setHas_new_message(boolean has_new_message) {
        this.has_new_message = has_new_message;
    }

    public double getMax_time() {
        return max_time;
    }

    public void setMax_time(double max_time) {
        this.max_time = max_time;
    }

    public int getMin_time() {
        return min_time;
    }

    public void setMin_time(int min_time) {
        this.min_time = min_time;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
