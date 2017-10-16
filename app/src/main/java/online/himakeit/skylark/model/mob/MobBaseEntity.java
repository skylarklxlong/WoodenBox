package online.himakeit.skylark.model.mob;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/16 19:17
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * mob 返回的数据格式：{"msg":"","result":[],"retCode":""}或{"msg":"","result":{},"retCode":""}
 */
public class MobBaseEntity<T> implements Serializable{
    private static final long serialVersionUID = -4553802208756427393L;

    @SerializedName("msg")
    private String msg;
    @SerializedName("retCode")
    private String retCode;
    @SerializedName("result")
    private T result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MobBaseEntity{" +
                "msg='" + msg + '\'' +
                ", retCode='" + retCode + '\'' +
                ", result=" + result +
                '}';
    }
}
