package online.himakeit.skylark.api;

import online.himakeit.skylark.model.zhuhu.ZhiHuDaily;
import online.himakeit.skylark.model.zhuhu.ZhiHuStory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 16:34
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ZhiHuApi {
    // TODO: 2017/8/22 http://news-at.zhihu.com/api/4/news/latest 用于获取最新的
    @GET("/api/4/news/latest")
    Observable<ZhiHuDaily> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhiHuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhiHuStory> getZhiHuStory(@Path("id") String id);

    // TODO: 2017/8/22 从最美官网抓取壁纸用的
//    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
//    Observable<ImageResponse> getImage();
}
