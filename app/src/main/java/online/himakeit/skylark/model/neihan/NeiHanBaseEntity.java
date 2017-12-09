package online.himakeit.skylark.model.neihan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/9 16:26
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanBaseEntity implements Serializable {
    private static final long serialVersionUID = -4553802208756427393L;

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private NeiHanDataEntity data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NeiHanDataEntity getData() {
        return data;
    }

    public void setData(NeiHanDataEntity data) {
        this.data = data;
    }
}
