package online.himakeit.skylark.model.zhuhu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 19:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuStory {
    @SerializedName("body")
    private String body;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("share_url")
    private String mShareUrl;
    @SerializedName("css")
    private String[] css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getmShareUrl() {
        return mShareUrl;
    }

    public void setmShareUrl(String mShareUrl) {
        this.mShareUrl = mShareUrl;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
