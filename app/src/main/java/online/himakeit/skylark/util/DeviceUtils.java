package online.himakeit.skylark.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-5 下午2:14
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class DeviceUtils {

    /**
     * 获取程序最大可用运行内存
     *
     * @param context
     */
    public static void getAppCanUsedMem(Context context) {
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));
        int heapSize = activityManager.getMemoryClass();
        LogUtils.e("程序可用内存大小为：" + heapSize);
    }

}
