package online.himakeit.skylark;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.facebook.stetho.Stetho;
import com.litesuits.orm.LiteOrm;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.himakeit.skylark.model.kuaichuan.FileInfo;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.Toasts;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-20 下午6:30
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class AppContext extends Application{

    private static final String TAG = "AppContext";
    /**
     * 主要的线程池
     */
    public static Executor MAIN_EXECUTOR = Executors.newFixedThreadPool(5);

    /**
     * 文件发送单线程
     */
    public static Executor FILE_SENDER_EXECUTOR = Executors.newSingleThreadExecutor();

    /**
     * 全局应用的上下文
     */
    static AppContext mAppContext;


    //文件发送方
    Map<String, FileInfo> mFileInfoMap = new HashMap<String, FileInfo>(); //采用HashMap结构，文件地址--->>>FileInfo 映射结构，重复加入FileInfo

    Map<String, FileInfo> mReceiverFileInfoMap = new HashMap<String, FileInfo>();

    private static final String DB_NAME = "skylark.db";
    public static LiteOrm liteOrmDB;

    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        mHandler = new Handler();

        initXUtils3();

        initJPush();

        initUmengAnalytics();

        Toasts.register(this);

        liteOrmDB = LiteOrm.newSingleInstance(this, DB_NAME);
        liteOrmDB.setDebugged(true);

        /*//memory leak testing
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...*/

        /**
         * facebook调试工具
         */
        Stetho.initializeWithDefaults(this);
    }

    private void initXUtils3() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    private void initJPush() {
        try {
            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);            // 初始化 JPush
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    private void initUmengAnalytics() {
        //------------------友盟统计----------------------

        //禁止默认的页面统计方式
        MobclickAgent.openActivityDurationTrack(false);

        //设置是否对日志信息进行加密, 默认false(不加密).
        MobclickAgent.enableEncrypt(!BuildConfig.DEBUG);

        //捕获程序崩溃日志
        MobclickAgent.setCatchUncaughtExceptions(true);

    }

    /**
     * 获取全局的AppContext
     *
     * @return
     */
    public static AppContext getAppContext() {
        return mAppContext;
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
        PackageInfo info = null;

        try {
            PackageManager packageManager = mAppContext.getPackageManager();
            info = packageManager.getPackageInfo(mAppContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }


    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        client.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        //设置缓存路径
        File httpCacheDirectory = new File(mAppContext.getCacheDir(), "okhttpCache");
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
        client.addInterceptor(new ChuckInterceptor(mAppContext).showNotification(!BuildConfig.DEBUG));
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
            boolean netWorkConection = NetUtils.hasNetWorkConection(AppContext.getAppContext());
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


    //==========================================================================
    //==========================================================================
    //发送方

    /**
     * 添加一个FileInfo
     *
     * @param fileInfo
     */
    public void addFileInfo(FileInfo fileInfo) {
//        if(!mFileInfoSet.contains(fileInfo)){
//            mFileInfoSet.add(fileInfo);
//        }

        if (!mFileInfoMap.containsKey(fileInfo.getFilePath())) {
            mFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
        }
    }

    /**
     * 更新FileInfo
     *
     * @param fileInfo
     */
    public void updateFileInfo(FileInfo fileInfo) {
        mFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
    }

    /**
     * 删除一个FileInfo
     *
     * @param fileInfo
     */
    public void delFileInfo(FileInfo fileInfo) {
//        if(mFileInfoSet.contains(fileInfo)){
//            mFileInfoSet.remove(fileInfo);
//        }
        if (mFileInfoMap.containsKey(fileInfo.getFilePath())) {
            mFileInfoMap.remove(fileInfo.getFilePath());
        }
    }

    /**
     * 是否存在FileInfo
     *
     * @param fileInfo
     * @return
     */
    public boolean isExist(FileInfo fileInfo) {
        if (mFileInfoMap == null) {
            return false;
        }
        return mFileInfoMap.containsKey(fileInfo.getFilePath());
    }

    /**
     * 判断文件集合是否有元素
     *
     * @return 有返回true， 反之
     */
    public boolean isFileInfoMapExist() {
        if (mFileInfoMap == null || mFileInfoMap.size() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取全局变量中的FileInfoMap
     *
     * @return
     */
    public Map<String, FileInfo> getFileInfoMap() {
        return mFileInfoMap;
    }

    /**
     * 获取即将发送文件列表的总长度
     *
     * @return
     */
    public long getAllSendFileInfoSize() {
        long total = 0;
        for (FileInfo fileInfo : mFileInfoMap.values()) {
            if (fileInfo != null) {
                total = total + fileInfo.getSize();
            }
        }
        return total;
    }

    //==========================================================================
    //==========================================================================


    //==========================================================================
    //==========================================================================
    //发送方

    /**
     * 添加一个FileInfo
     *
     * @param fileInfo
     */
    public void addReceiverFileInfo(FileInfo fileInfo) {
        if (!mReceiverFileInfoMap.containsKey(fileInfo.getFilePath())) {
            mReceiverFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
        }
    }

    /**
     * 更新FileInfo
     *
     * @param fileInfo
     */
    public void updateReceiverFileInfo(FileInfo fileInfo) {
        mReceiverFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
    }

    /**
     * 删除一个FileInfo
     *
     * @param fileInfo
     */
    public void delReceiverFileInfo(FileInfo fileInfo) {
        if (mReceiverFileInfoMap.containsKey(fileInfo.getFilePath())) {
            mReceiverFileInfoMap.remove(fileInfo.getFilePath());
        }
    }

    /**
     * 是否存在FileInfo
     *
     * @param fileInfo
     * @return
     */
    public boolean isReceiverInfoExist(FileInfo fileInfo) {
        if (mReceiverFileInfoMap == null) return false;
        return mReceiverFileInfoMap.containsKey(fileInfo.getFilePath());
    }

    /**
     * 判断文件集合是否有元素
     *
     * @return 有返回true， 反之
     */
    public boolean isReceiverFileInfoMapExist() {
        if (mReceiverFileInfoMap == null || mReceiverFileInfoMap.size() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取全局变量中的FileInfoMap
     *
     * @return
     */
    public Map<String, FileInfo> getReceiverFileInfoMap() {
        return mReceiverFileInfoMap;
    }


    /**
     * 获取即将接收文件列表的总长度
     *
     * @return
     */
    public long getAllReceiverFileInfoSize() {
        long total = 0;
        for (FileInfo fileInfo : mReceiverFileInfoMap.values()) {
            if (fileInfo != null) {
                total = total + fileInfo.getSize();
            }
        }
        return total;
    }

    //==========================================================================
    //==========================================================================
}
