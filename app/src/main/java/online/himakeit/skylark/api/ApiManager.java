package online.himakeit.skylark.api;


import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.model.Config;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 15:52
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ApiManager {
    private static ApiManager apiManager;
    private TopNewsApi topNewsApi;
    private ZhiHuApi zhiHuApi;
    private GankApi gankApi;
    private ZuiMeiApi zuiMeiApi;
    private static Object monitor = new Object();

    // TODO: 2017/7/27 采用双重检查模式的单列
    public static ApiManager getInstence() {
        if (apiManager == null) {
            synchronized (monitor) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    public ZhiHuApi getZhiHuApiService() {
        if (zhiHuApi == null) {
            synchronized (monitor) {
                if (zhiHuApi == null) {
                    zhiHuApi = new Retrofit.Builder()
                            .baseUrl(Config.ZHIHU_BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(ZhiHuApi.class);
                }
            }
        }
        return zhiHuApi;
    }

    public TopNewsApi getTopNewsService() {
        if (topNewsApi == null) {
            synchronized (monitor) {
                if (topNewsApi == null) {
                    topNewsApi = new Retrofit.Builder()
                            .baseUrl(Config.TOPNEWS_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(TopNewsApi.class);
                }
            }
        }
        return topNewsApi;
    }

    public GankApi getGankService() {
        if (gankApi == null) {
            synchronized (monitor) {
                if (gankApi == null) {
                    gankApi = new Retrofit.Builder()
                            .baseUrl(Config.GANKIO_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(GankApi.class);
                }
            }
        }

        return gankApi;
    }

    public ZuiMeiApi getZuiMeiService(){
        if (zuiMeiApi == null){
            synchronized (monitor){
                if (zuiMeiApi == null){
                    zuiMeiApi = new Retrofit.Builder()
                            .baseUrl(Config.ZUIMEI_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(ZuiMeiApi.class);
                }
            }
        }
        return zuiMeiApi;
    }
}
