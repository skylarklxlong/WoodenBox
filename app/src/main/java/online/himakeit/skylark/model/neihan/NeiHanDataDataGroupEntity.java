package online.himakeit.skylark.model.neihan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/9 18:50
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 *
 * 详情连接
 * http://m.neihanshequ.com/share/group/80584525434/?iid=3216590132&app=joke_essay
 * http://m.neihanshequ.com/group/80584525434/
 */
public class NeiHanDataDataGroupEntity implements Serializable {
    private String DETAIL = "http://m.neihanshequ.com/group/";
    @SerializedName("allow_dislike")
    private boolean allow_dislike;
    @SerializedName("bury_count")
    private int bury_count;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("category_type")
    private String category_type;
    @SerializedName("category_visible")
    private boolean category_visible;
    @SerializedName("comment_count")
    private String comment_count;
    @SerializedName("content")
    private String content;
    @SerializedName("create_time")
    private Long create_time;
    @SerializedName("digg_count")
    private int digg_count;
    @SerializedName("duration")
    private double duration;
    @SerializedName("favorite_count")
    private String favorite_count;
    @SerializedName("is_video")
    private String is_video;
    @SerializedName("label")
    private String label;
    @SerializedName("mp4_url")
    private String mp4_url;
    @SerializedName("share_url")
    private String share_url;
    @SerializedName("text")
    private String text;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("uri")
    private String uri;
    @SerializedName("user")
    private UserBean user;
    @SerializedName("video_id")
    private String video_id;
    @SerializedName("video_height")
    private int video_height;
    @SerializedName("video_width")
    private int video_width;
    @SerializedName("middle_image")
    private Mimage middle_image;
    @SerializedName("large_image")
    private Limage large_image;
    @SerializedName("gifvideo")
    private GifVideo gifvideo;
    @SerializedName("medium_cover")
    private MCover medium_cover;
    @SerializedName("large_cover")
    private LCover large_cover;
    @SerializedName("group_id")
    private String group_id;


    public static class Mimage implements Serializable {
        private String suffix = ".webp";
        private String url = "http://p9.pstatp.com/";
        @SerializedName("r_height")
        private int height;
        @SerializedName("r_width")
        private int width;
        @SerializedName("uri")
        private String uri;

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class Limage implements Serializable {
        private String suffix = ".webp";
        private String url = "http://p9.pstatp.com/";
        @SerializedName("r_height")
        private int height;
        @SerializedName("r_width")
        private int width;
        @SerializedName("uri")
        private String uri;

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class UserBean implements Serializable {
        @SerializedName("avatar_url")
        private String avatar_url;
        @SerializedName("name")
        private String name;
        @SerializedName("user_id")
        private long user_id;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }
    }

    public static class GifVideo implements Serializable {
        @SerializedName("mp4_url")
        private String mp4_url;
        @SerializedName("video_height")
        private int video_height;
        @SerializedName("video_width")
        private int video_width;
        @SerializedName("video_id")
        private String video_id;

        public String getMp4_url() {
            return mp4_url;
        }

        public void setMp4_url(String mp4_url) {
            this.mp4_url = mp4_url;
        }

        public int getVideo_height() {
            return video_height;
        }

        public void setVideo_height(int video_height) {
            this.video_height = video_height;
        }

        public int getVideo_width() {
            return video_width;
        }

        public void setVideo_width(int video_width) {
            this.video_width = video_width;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }

    public static class MCover implements Serializable {
        private String suffix = ".webp";
        private String url = "http://p3.pstatp.com/";
        @SerializedName("uri")
        private String uri;

        public String getSuffix() {
            return suffix;
        }


        public String getUrl() {
            return url;
        }


        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public static class LCover implements Serializable {
        private String suffix = ".webp";
        private String url = "http://p3.pstatp.com/";
        @SerializedName("uri")
        private String uri;

        public String getSuffix() {
            return suffix;
        }

        public String getUrl() {
            return url;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public MCover getMedium_cover() {
        return medium_cover;
    }

    public void setMedium_cover(MCover medium_cover) {
        this.medium_cover = medium_cover;
    }

    public LCover getLarge_cover() {
        return large_cover;
    }

    public void setLarge_cover(LCover large_cover) {
        this.large_cover = large_cover;
    }

    public GifVideo getGifvideo() {
        return gifvideo;
    }

    public void setGifvideo(GifVideo gifvideo) {
        this.gifvideo = gifvideo;
    }

    public Mimage getMiddle_image() {
        return middle_image;
    }

    public void setMiddle_image(Mimage middle_image) {
        this.middle_image = middle_image;
    }

    public Limage getLarge_image() {
        return large_image;
    }

    public void setLarge_image(Limage large_image) {
        this.large_image = large_image;
    }

    public boolean isAllow_dislike() {
        return allow_dislike;
    }

    public void setAllow_dislike(boolean allow_dislike) {
        this.allow_dislike = allow_dislike;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public boolean isCategory_visible() {
        return category_visible;
    }

    public void setCategory_visible(boolean category_visible) {
        this.category_visible = category_visible;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(String favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getIs_video() {
        return is_video;
    }

    public void setIs_video(String is_video) {
        this.is_video = is_video;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public int getVideo_height() {
        return video_height;
    }

    public void setVideo_height(int video_height) {
        this.video_height = video_height;
    }

    public int getVideo_width() {
        return video_width;
    }

    public void setVideo_width(int video_width) {
        this.video_width = video_width;
    }

    public int getBury_count() {
        return bury_count;
    }

    public void setBury_count(int bury_count) {
        this.bury_count = bury_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }
}
