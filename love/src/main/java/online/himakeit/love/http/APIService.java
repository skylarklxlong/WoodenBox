package online.himakeit.love.http;

import online.himakeit.love.Constants;
import online.himakeit.love.bean.AppUpdateInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 16:50
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface APIService {

    //获取fir.im中的Love的最新版本
    @Headers("Cache-Control: public, max-age=3600")
    @GET(Constants.URL_AppUpdateInfo)
    Call<AppUpdateInfo> getTheLastAppInfo();

}
