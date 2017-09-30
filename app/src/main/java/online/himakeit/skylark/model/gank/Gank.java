package online.himakeit.skylark.model.gank;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 8:49
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class Gank {
    public String objectId;
    public String url;
    public String type;
    public String desc;
    public String who;
    public boolean used;
    public String createAt;
    public String updateAt;
    public String publicAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getPublicAt() {
        return publicAt;
    }

    public void setPublicAt(String publicAt) {
        this.publicAt = publicAt;
    }
}
