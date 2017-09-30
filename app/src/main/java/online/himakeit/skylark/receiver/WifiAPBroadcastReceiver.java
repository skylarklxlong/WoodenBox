package online.himakeit.skylark.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import online.himakeit.skylark.util.LogUtils;


/**
 * Created by：LiXueLong 李雪龙 on 2017/7/24 14:20
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public abstract class WifiAPBroadcastReceiver extends BroadcastReceiver{
    public static final String TAG = WifiAPBroadcastReceiver.class.getSimpleName();
    //WIFI AP state action
    public static final String ACTION_WIFI_AP_STATE_CHANGED = "android.net.wifi.WIFI_AP_STATE_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_WIFI_AP_STATE_CHANGED)){//wifi ap state change
            //get wifi hotspot state here
            int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,0);
            LogUtils.e(TAG + "wifi ap state " + state);
            if (WifiManager.WIFI_STATE_ENABLED == state % 10) {
                // Wifi is enabled
                onWifiApEnabled();
            }else if(WifiManager.WIFI_STATE_ENABLING == state % 10){
                // Wifi is enabling
            }else if(WifiManager.WIFI_STATE_DISABLED == state % 10){
                // Wifi is disable
            }else if(WifiManager.WIFI_STATE_DISABLING == state % 10){
                // Wifi is disabling
            }
        }
    }

    /**
     * 热点可用
     */
    public abstract void onWifiApEnabled();
}
