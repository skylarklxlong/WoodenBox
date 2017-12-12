package online.himakeit.skylark.model.neihan;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/12 20:26
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanRequestData {
    String type;
    int count;
    int pWidth;
    String iid;
    String device_id;
    String device_type;
    String device_brand;
    int os_api;
    String os_version;
    String uuid;
    String openudid;
    String resolution;
    int dpi;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getpWidth() {
        return pWidth;
    }

    public void setpWidth(int pWidth) {
        this.pWidth = pWidth;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public int getOs_api() {
        return os_api;
    }

    public void setOs_api(int os_api) {
        this.os_api = os_api;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenudid() {
        return openudid;
    }

    public void setOpenudid(String openudid) {
        this.openudid = openudid;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }
}
