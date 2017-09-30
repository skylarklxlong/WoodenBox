package online.himakeit.skylark.model.topnews;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 16:08
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NewsList {
    // TODO: 2017/7/27 donot forget romove first
    @SerializedName("T1348647909107")
    ArrayList<NewsBean> newsList;

    public ArrayList<NewsBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<NewsBean> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "newsList=" + newsList +
                '}';
    }
}
