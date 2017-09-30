package online.himakeit.skylark.model.gank;

import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 8:44
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class GankMeiZhiData extends GankBaseData {
    public ArrayList<GankMeiZhi> results;

    public ArrayList<GankMeiZhi> getResults() {
        return results;
    }

    public void setResults(ArrayList<GankMeiZhi> results) {
        this.results = results;
    }
}
