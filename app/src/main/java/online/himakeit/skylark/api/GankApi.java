package online.himakeit.skylark.api;


import online.himakeit.skylark.model.gank.GankData;
import online.himakeit.skylark.model.gank.GankMeiZhiData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/5 20:22
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface GankApi {
    @GET("/api/data/福利/10/{page}")
    Observable<GankMeiZhiData> getMeiZhiData(@Path("page") int page);

    // TODO: 2017/9/6 type: 福利/Android/iOS/休息视频/拓展资源/前端
    @GET("/api/data/{type}/10/{page}")
    Observable<GankData> getGankData(@Path("type") String type,
                                     @Path("page") int page);
}
