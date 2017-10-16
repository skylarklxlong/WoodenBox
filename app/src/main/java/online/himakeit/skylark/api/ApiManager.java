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
    private WebServiceApi webServiceApi;
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

    public WebServiceApi getWebServiceApi(){
        if (webServiceApi == null){
            synchronized (monitor){
                if (webServiceApi == null){
                    webServiceApi = new Retrofit.Builder()
                            .baseUrl(Config.MOB_BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(WebServiceApi.class);
                }
            }
        }
        return webServiceApi;
    }

    public WebServiceApi getZhiHuApiService() {
        if (webServiceApi == null) {
            synchronized (monitor) {
                if (webServiceApi == null) {
                    webServiceApi = new Retrofit.Builder()
                            .baseUrl(Config.ZHIHU_BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(WebServiceApi.class);
                }
            }
        }
        return webServiceApi;
    }

    public WebServiceApi getTopNewsService() {
        if (webServiceApi == null) {
            synchronized (monitor) {
                if (webServiceApi == null) {
                    webServiceApi = new Retrofit.Builder()
                            .baseUrl(Config.TOPNEWS_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(WebServiceApi.class);
                }
            }
        }
        return webServiceApi;
    }

    public WebServiceApi getGankService() {
        if (webServiceApi == null) {
            synchronized (monitor) {
                if (webServiceApi == null) {
                    webServiceApi = new Retrofit.Builder()
                            .baseUrl(Config.GANKIO_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(WebServiceApi.class);
                }
            }
        }

        return webServiceApi;
    }

    public WebServiceApi getZuiMeiService(){
        if (webServiceApi == null){
            synchronized (monitor){
                if (webServiceApi == null){
                    webServiceApi = new Retrofit.Builder()
                            .baseUrl(Config.ZUIMEI_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(AppContext.defaultOkHttpClient())
                            .build()
                            .create(WebServiceApi.class);
                }
            }
        }
        return webServiceApi;
    }
}
