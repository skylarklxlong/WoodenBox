package online.himakeit.skylark.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.himakeit.skylark.AppContext;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 9:26
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class OkHttp3Utils {
    private static OkHttpClient mOkHttpClient;

    //设置缓存目录
    private static File cacheDirectory = new File(
            AppContext.getAppContext().getCacheDir().getAbsolutePath()
            ,"MyCache");
    private static Cache mCache = new Cache(cacheDirectory,10*1024*1024);

    public static OkHttpClient getOkHttpClient(){
        if (null == mOkHttpClient){
            //同样在OkHttp3以后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //设置一个自动管理cookies的管理器
                    .cookieJar(new CookiesManager())
                    //添加拦截器
//                    .addInterceptor(new MyIntercepter())
                    //添加网络拦截器
//                    .addNetworkInterceptor(new MyIntercepter())
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(mCache)
                    .build();
        }

        return mOkHttpClient;
    }

    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar{
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(AppContext.getAppContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0){
                for (Cookie item : cookies){
                    cookieStore.add(url,item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 拦截器
     */
    private static class MyIntercepter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetworkReachable(AppContext.getAppContext())) {
                Toast.makeText(AppContext.getAppContext(), "暂无网络", Toast.LENGTH_SHORT).show();
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                        .build();
            }

            Response response = chain.proceed(request);
            if (isNetworkReachable(AppContext.getAppContext())) {
                int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
