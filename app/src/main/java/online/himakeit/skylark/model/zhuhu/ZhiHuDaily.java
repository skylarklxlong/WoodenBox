package online.himakeit.skylark.model.zhuhu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 16:38
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuDaily {
    @SerializedName("date")
    private String date;
    @SerializedName("top_stories")
    private ArrayList<ZhiHuDailyItem> mZhiHuDailyItems;
    @SerializedName("stories")
    private ArrayList<ZhiHuDailyItem> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhiHuDailyItem> getmZhiHuDailyItems() {
        return mZhiHuDailyItems;
    }

    public void setmZhiHuDailyItems(ArrayList<ZhiHuDailyItem> mZhiHuDailyItems) {
        this.mZhiHuDailyItems = mZhiHuDailyItems;
    }

    public ArrayList<ZhiHuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhiHuDailyItem> stories) {
        this.stories = stories;
    }
}
