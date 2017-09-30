package online.himakeit.skylark.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 9:26
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:封装一个retrofit集成0kHttp3的抽象基类
 */
public abstract class RetrofitUtils {

    private static final String API_SERVER= "http://c.m.163.com/";
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取Retrofit对象
     * @return
     */
    protected static Retrofit getRetrofit(){
        if (mRetrofit == null){
            if (mOkHttpClient == null){
                mOkHttpClient = OkHttp3Utils.getOkHttpClient();
            }
            //Retrofit2后使用Build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器默认路径
                    .baseUrl(API_SERVER)
                    //添加转换库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加回调库,采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置使用OkHttp请求网络
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
