package online.himakeit.skylark.util;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/24 14:34
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * 1.Prepare
 * use WifiManager class need the permission
 * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *
 * 2.Usage
 * ...start
 * ApMgr.isApOn(Context context); // check Ap state :boolean
 * ApMgr.configApState(Context context); // change Ap state :boolean
 * ...end
 */
public class ApMgr {

    //check whether wifi hotspot on or off
    public static boolean isApOn(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (boolean) method.invoke(wifiManager);
        }catch (Throwable ignored){}

        return false;
    }

    //close wifi hotspot
    public static void disableAp(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
            method.invoke(wifiManager,null,false);
        }catch (Throwable ignored){}
    }

    //toggle wifi hotspot on or off
    public static boolean configApState(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiConfiguration wifiConfiguration = null;
        try {
            //if wifi is on ,turn if off
            if (isApOn(context)){
                wifiManager.setWifiEnabled(false);
                //if ap is on and then disable ap
                disableAp(context);
            }
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled",WifiConfiguration.class,boolean.class);
            method.invoke(wifiManager,wifiConfiguration, !isApOn(context));
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //toggle wifi hotspot on or off , and specify the hotspot name
    public static boolean configApState(Context context,String apName){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiConfiguration wifiConfiguration = null;
        try {
            wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = apName;
            // if wifi is on ,turn it off
            if (isApOn(context)){
                wifiManager.setWifiEnabled(false);
                //if ap is on and then disable ap
                disableAp(context);
            }
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled",WifiConfiguration.class,boolean.class);
            method.invoke(wifiManager,wifiConfiguration,!isApOn(context));
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
