package online.himakeit.skylark;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.litesuits.orm.LiteOrm;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.jpush.android.api.JPushInterface;
import online.himakeit.skylark.model.kuaichuan.FileInfo;
import online.himakeit.skylark.util.Toasts;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-20 下午6:30
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class AppContext extends Application {
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

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        initUmengAnalytics();

        Toasts.register(this);

        liteOrmDB = LiteOrm.newSingleInstance(this,DB_NAME);
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
     * @return
     */
    public static AppContext getAppContext(){
        return mAppContext;
    }

    //==========================================================================
    //==========================================================================
    //发送方
    /**
     * 添加一个FileInfo
     * @param fileInfo
     */
    public void addFileInfo(FileInfo fileInfo){
//        if(!mFileInfoSet.contains(fileInfo)){
//            mFileInfoSet.add(fileInfo);
//        }

        if(!mFileInfoMap.containsKey(fileInfo.getFilePath())){
            mFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
        }
    }

    /**
     * 更新FileInfo
     * @param fileInfo
     */
    public void updateFileInfo(FileInfo fileInfo){
        mFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
    }

    /**
     * 删除一个FileInfo
     * @param fileInfo
     */
    public void delFileInfo(FileInfo fileInfo){
//        if(mFileInfoSet.contains(fileInfo)){
//            mFileInfoSet.remove(fileInfo);
//        }
        if(mFileInfoMap.containsKey(fileInfo.getFilePath())){
            mFileInfoMap.remove(fileInfo.getFilePath());
        }
    }

    /**
     * 是否存在FileInfo
     * @param fileInfo
     * @return
     */
    public boolean isExist(FileInfo fileInfo){
        if(mFileInfoMap == null){
            return false;
        }
        return mFileInfoMap.containsKey(fileInfo.getFilePath());
    }

    /**
     * 判断文件集合是否有元素
     * @return 有返回true， 反之
     */
    public boolean isFileInfoMapExist(){
        if(mFileInfoMap == null || mFileInfoMap.size() <= 0){
            return false;
        }
        return true;
    }

    /**
     * 获取全局变量中的FileInfoMap
     * @return
     */
    public Map<String, FileInfo> getFileInfoMap(){
        return mFileInfoMap;
    }

    /**
     * 获取即将发送文件列表的总长度
     * @return
     */
    public long getAllSendFileInfoSize(){
        long total = 0;
        for(FileInfo fileInfo : mFileInfoMap.values()){
            if(fileInfo != null){
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
     * @param fileInfo
     */
    public void addReceiverFileInfo(FileInfo fileInfo){
        if(!mReceiverFileInfoMap.containsKey(fileInfo.getFilePath())){
            mReceiverFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
        }
    }

    /**
     * 更新FileInfo
     * @param fileInfo
     */
    public void updateReceiverFileInfo(FileInfo fileInfo){
        mReceiverFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
    }

    /**
     * 删除一个FileInfo
     * @param fileInfo
     */
    public void delReceiverFileInfo(FileInfo fileInfo){
        if(mReceiverFileInfoMap.containsKey(fileInfo.getFilePath())){
            mReceiverFileInfoMap.remove(fileInfo.getFilePath());
        }
    }

    /**
     * 是否存在FileInfo
     * @param fileInfo
     * @return
     */
    public boolean isReceiverInfoExist(FileInfo fileInfo){
        if(mReceiverFileInfoMap == null) return false;
        return mReceiverFileInfoMap.containsKey(fileInfo.getFilePath());
    }

    /**
     * 判断文件集合是否有元素
     * @return 有返回true， 反之
     */
    public boolean isReceiverFileInfoMapExist(){
        if(mReceiverFileInfoMap == null || mReceiverFileInfoMap.size() <= 0){
            return false;
        }
        return true;
    }

    /**
     * 获取全局变量中的FileInfoMap
     * @return
     */
    public Map<String, FileInfo> getReceiverFileInfoMap(){
        return mReceiverFileInfoMap;
    }


    /**
     * 获取即将接收文件列表的总长度
     * @return
     */
    public long getAllReceiverFileInfoSize(){
        long total = 0;
        for(FileInfo fileInfo : mReceiverFileInfoMap.values()){
            if(fileInfo != null){
                total = total + fileInfo.getSize();
            }
        }
        return total;
    }

    //==========================================================================
    //==========================================================================

}
