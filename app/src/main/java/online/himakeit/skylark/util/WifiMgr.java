package online.himakeit.skylark.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-5 上午11:13
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class WifiMgr {

    /**
     * 创建WifiConfiguration的类型
     */
    public static final int WIFICIPHER_NOPASS = 1;
    public static final int WIFICIPHER_WEP = 2;
    public static final int WIFICIPHER_WAP = 3;

    private static WifiMgr mWifiMgr;
    private Context mContext;
    private WifiManager mWifiManager;

    List<ScanResult> mScanResultList;
    List<WifiConfiguration> mWifiConfigurations;

    WifiInfo mWifiInfo;

    private WifiMgr(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

    }

    public static WifiMgr getInstance(Context context) {

        if (mWifiMgr == null) {
            synchronized (WifiMgr.class) {
                if (mWifiMgr == null) {
                    mWifiMgr = new WifiMgr(context);
                }
            }
        }

        return mWifiMgr;
    }

    /**
     * 打开Wifi
     */
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭Wifi
     */
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 判断wifi是否为开启的状态
     *
     * @return
     */
    public boolean isWifiEnable() {
        //1、如果 null为真则 null=false 否则null=mWifiManager.isWifiEnabled()
        //2、如果 mWifiManager和null相等 则返回 true 否则返回 false
        return mWifiManager == null ? false : mWifiManager.isWifiEnabled();
    }

    /**
     * wifi扫描
     * android6.0 在wifi扫描时加了一个非常奇怪的操作，就是一定要打开gps
     */
    public void startScan() {
        /**
         * 这里应该先判断系统版本是否为 android6.0
         * 如果是的话，应该先打开gps开关
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//android 6.0 以下
            mWifiManager.startScan();
            mScanResultList = mWifiManager.getScanResults();
            mWifiConfigurations = mWifiManager.getConfiguredNetworks();
        }
    }

    public List<ScanResult> getmScanResultList() {
        return mScanResultList;
    }

    public List<WifiConfiguration> getmWifiConfigurations() {
        return mWifiConfigurations;
    }

    /**
     * 添加到指定Wifi网络 /切换到指定Wifi网络
     *
     * @param wifiConfiguration
     * @return
     */
    public boolean addNetwork(WifiConfiguration wifiConfiguration) {
        //断开当前的连接
        disconnectCurrentNetwork();

        //创建新的连接
        int netId = mWifiManager.addNetwork(wifiConfiguration);
        boolean enable = mWifiManager.enableNetwork(netId, true);
        return enable;

    }

    /**
     * 关闭当前的Ｗifi网络
     *
     * @return
     */
    public boolean disconnectCurrentNetwork() {
        if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
            int netId = mWifiManager.getConnectionInfo().getNetworkId();
            mWifiManager.disableNetwork(netId);
            return mWifiManager.disconnect();
        }
        return false;
    }

    /**
     * 创建WifiConfiguration
     *
     * @param ssid
     * @param password
     * @param type
     * @return
     */
    public static WifiConfiguration createWifiCfg(String ssid, String password,
                                                  int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();

        config.SSID = "\"" + ssid + "\"";

        if (type == WIFICIPHER_NOPASS) {
//            无密码连接WIFI时，连接不上wifi，需要注释两行代码
//            config.wepKeys[0] = "";
//            config.wepTxKeyIndex = 0;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (type == WIFICIPHER_WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0]= "\""+password+"\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == WIFICIPHER_WAP) {
            config.preSharedKey = "\""+password+"\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    /**
     * 获取当前ＷifiInfo
     * @return
     */
    public WifiInfo getmWifiInfo() {
        mWifiInfo = mWifiManager.getConnectionInfo();
        return mWifiInfo;
    }

    /**
     * 获取当前Wifi所分配的Ip地址
     * @return
     */
    public String getCurrentIpAddress(){
        String ipAddress = "";
        int address= mWifiManager.getDhcpInfo().ipAddress;
        ipAddress = ((address & 0xFF)
                + "." + ((address >> 8) & 0xFF)
                + "." + ((address >> 16) & 0xFF)
                + "." + ((address >> 24) & 0xFF));
        return ipAddress;
    }

    /**
     * 设备连接Wifi之后， 设备获取Wifi热点的IP地址
     * @return
     */
    public String getIpAddressFromHotspot(){
        // WifiAP ip address is hardcoded in Android.
        /* IP/netmask: 192.168.43.1/255.255.255.0 */
        String ipAddress = "192.168.43.1";
        DhcpInfo dhcpInfo = mWifiManager.getDhcpInfo();
        int address = dhcpInfo.gateway;
        ipAddress = ((address & 0xFF)
                + "." + ((address >> 8) & 0xFF)
                + "." + ((address >> 16) & 0xFF)
                + "." + ((address >> 24) & 0xFF));
        return ipAddress;
    }

    /**
     * 开启热点之后，获取自身热点的IP地址
     * @return
     */
    public String getHotspotLocalIpAddress(){
        // WifiAP ip address is hardcoded in Android.
        /* IP/netmask: 192.168.43.1/255.255.255.0 */
        String ipAddress = "192.168.43.1";
        DhcpInfo dhcpInfo = mWifiManager.getDhcpInfo();
        int address = dhcpInfo.serverAddress;
        ipAddress = ((address & 0xFF)
                + "." + ((address >> 8) & 0xFF)
                + "." + ((address >> 16) & 0xFF)
                + "." + ((address >> 24) & 0xFF));
        return ipAddress;
    }



    /**
     * 关闭Wifi
     */
    public void disableWifi(){
        if(mWifiManager != null){
            mWifiManager.setWifiEnabled(false);
        }
    }

}
