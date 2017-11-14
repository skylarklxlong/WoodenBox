package online.himakeit.love.http;

import online.himakeit.love.Constants;
import online.himakeit.love.MyApplication;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 16:50
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BuildApi {
    private static Retrofit retrofit;

    public static APIService getAPIService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL) //设置Base的访问路径
                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
                    .client(MyApplication.defaultOkHttpClient())
                    .build();
        }
        return retrofit.create(APIService.class);
    }
}
