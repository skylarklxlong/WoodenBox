package online.himakeit.skylark.api;


import online.himakeit.skylark.model.zuimei.ZuiMeiImageResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 19:39
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ZuiMeiApi {
    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<ZuiMeiImageResponse> getImage();
}
