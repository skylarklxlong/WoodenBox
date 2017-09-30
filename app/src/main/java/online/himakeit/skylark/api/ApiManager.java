package online.himakeit.skylark.api;


import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
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
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            // TODO: 2017/7/27 如果网络可用
            if (true) {
                int maxAge = 60;//在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();

            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    private static ApiManager apiManager;
    private static File httpCacheDirectory = new File(AppContext.getAppContext().getCacheDir().getAbsolutePath(), "MyCache");
    private static int cacheSize = 10 * 1024 * 1024; //10MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();
    private TopNewsApi topNewsApi;
    private ZhiHuApi zhiHuApi;
    private GankApi gankApi;
    private ZuiMeiApi zuiMeiApi;
    private Object monitor = new Object();

    // TODO: 2017/7/27 采用双重检查模式的单列
    public static ApiManager getInstence() {
        if (apiManager == null) {
            synchronized (ApiManager.cache) {
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
                            .client(mOkHttpClient)
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
                            .client(mOkHttpClient)
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
                            .client(mOkHttpClient)
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
                            .client(mOkHttpClient)
                            .build()
                            .create(ZuiMeiApi.class);
                }
            }
        }
        return zuiMeiApi;
    }
}
