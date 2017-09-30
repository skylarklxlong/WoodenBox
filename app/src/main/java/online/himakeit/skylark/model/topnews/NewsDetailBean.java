package online.himakeit.skylark.model.topnews;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 13:50
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NewsDetailBean implements Serializable {
    /**
     * docid
     */
    @SerializedName("docid")
    private String docid;
    /**
     * title
     */
    @SerializedName("title")
    private String title;
    /**
     * source
     */
    @SerializedName("source")
    private String source;
    /**
     * body
     */
    @SerializedName("body")
    private String body;
    /**
     * ptime
     */
    @SerializedName("ptime")
    private String ptime;
    /**
     * cover
     */
//    @SerializedName("cover")
//    private String cover;
    /**
     * 图片列表
     */
//    @SerializedName("imgList")
//    private List<String> imgList;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    /*public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
*/
    /*public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
*/
    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", body='" + body + '\'' +
                ", ptime='" + ptime + '\'' +
//                ", cover='" + cover + '\'' +
//                ", imgList=" + imgList +
                '}';
    }
}
