package online.himakeit.love;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.himakeit.love.utils.LogUtils;
import online.himakeit.love.utils.NetUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 13:43
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    static MyApplication mContext;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mHandler = new Handler();

        //初始化Log
        initLog();
    }

    private void initLog() {
        KLog.init(BuildConfig.LOG_DEBUG, "---Skylark---");
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = mContext.getPackageManager();
            pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static MyApplication getAppContext() {
        return mContext;
    }

    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        //设置缓存路径
        File httpCacheDirectory = new File(mContext.getCacheDir(), "okhttpCache");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        client.cache(cache);
        //设置拦截器
        client.addInterceptor(LoggingInterceptor);
        client.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        client.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        /**
         * 添加拦截器 chuck 记录客户端所有的网络连接
         * 可以使用 new ChuckInterceptor(mAppContext).showNotification(false)
         * 来取消显示。
         *
         * 使用 Chuck.getLaunchIntent();来在任何地方打开Chunck界面
         */
        client.addInterceptor(new ChuckInterceptor(mContext));
        return client.build();
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //方案一：有网和没有网都是先读缓存
//                Request request = chain.request();
//                Log.i(TAG, "request=" + request);
//                Response response = chain.proceed(request);
//                Log.i(TAG, "response=" + response);
//
//                String cacheControl = request.cacheControl().toString();
//                if (TextUtils.isEmpty(cacheControl)) {
//                    cacheControl = "public, max-age=60";
//                }
//                return response.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();

            //方案二：无网读缓存，有网根据过期时间重新请求
            boolean netWorkConection = NetUtils.hasNetWorkConection(MyApplication.getAppContext());
            Request request = chain.request();
            if (!netWorkConection) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (netWorkConection) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", cacheControl)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };

    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.e(TAG, "-----LoggingInterceptor----- :\nrequest url:" + request.url() + "\ntime:" + (t2 - t1) / 1e6d + "\nbody:" + content + "\n");
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    };
}
