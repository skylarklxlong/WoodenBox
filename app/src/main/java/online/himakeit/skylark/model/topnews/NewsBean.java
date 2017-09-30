package online.himakeit.skylark.model.topnews;

import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import online.himakeit.skylark.model.Soul;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 16:04
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:新闻实体类
 * @Serializable 是Gson解析器里面的
 */
@Table("topnews")
public class NewsBean extends Soul {

    /**
     * docid
     */
    @Column("docid")
    @SerializedName("docid")
    private String docid;
    /**
     * 标题
     */
    @Column("title")
    @SerializedName("title")
    private String title;
    /**
     * 小内容
     */
    @Column("digest")
    @SerializedName("digest")
    private String digest;
    /**
     * 图片地址
     */
    @Column("imgsrc")
    @SerializedName("imgsrc")
    private String imgsrc;
    /**
     * 来源
     */
    @Column("source")
    @SerializedName("source")
    private String source;
    /**
     * 时间
     */
    @Column("ptime")
    @SerializedName("ptime")
    private String ptime;
    /**
     * TAG
     */
    @Column("tag")
    @SerializedName("tag")
    private String tag;

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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
