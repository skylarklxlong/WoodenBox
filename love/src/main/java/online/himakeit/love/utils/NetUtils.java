package online.himakeit.love.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-6 上午9:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NetUtils {

    /**
     * 判断指定的ipaddress是否可以ping
     * @param ipAddress
     * @return
     */
    public static boolean pingIpAddress(String ipAddress){

        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/ping -c 1 -w 100 " + ipAddress);
            int status = process.waitFor();

            if (status == 0){
                return true;
            }else {
                return false;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static ConnectivityManager mConnectivityManager = null;

    private static ConnectivityManager getConnectivityManager(Context context) {
        if (mConnectivityManager == null) {
            mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return mConnectivityManager;
    }


    /**
     * 判断是否具有网络连接
     *
     * @return
     */
    public static final boolean hasNetWorkConection(Context ctx) {
        // 获取连接活动管理器
        NetworkInfo activeNetworkInfo = getConnectivityManager(ctx).getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isAvailable());
    }


    /**
     * 当前网络是不是wifi
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            NetworkInfo mWiFiNetworkInfo = getConnectivityManager(context)
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
