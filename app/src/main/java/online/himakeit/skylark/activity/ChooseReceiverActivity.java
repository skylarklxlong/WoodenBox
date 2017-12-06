package online.himakeit.skylark.activity;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.Constant;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.WifiScanResultAdapter;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.model.kuaichuan.FileInfo;
import online.himakeit.skylark.util.BaseTransfer;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.NavigatorUtils;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.Toasts;
import online.himakeit.skylark.util.WifiMgr;
import online.himakeit.skylark.view.RadarScanView;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-4 下午4:45
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ChooseReceiverActivity extends BaseActivity {

    private static final String TAG = ChooseReceiverActivity.class.getSimpleName();

    /**
     * TopBar相关UI
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    /**
     * 雷达UI
     */
    @Bind(R.id.radarscan)
    RadarScanView radarScanView;
    /**
     * listview 扫描结果
     */
    @Bind(R.id.lv_result)
    ListView lv_result;

    List<ScanResult> mScanResultList;
    WifiScanResultAdapter mWifiScanResultAdapter;

    /**
     * 与 文件发送方 通信的 线程
     */
    Runnable mUdpServerRuannable;

    public static final int MSG_TO_FILE_SENDER_UI = 0X88;   //消息：跳转到文件发送列表UI
    public static final int MSG_TO_SHOW_SCAN_RESULT = 0X99; //消息：更新扫描可连接Wifi网络的列表

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_TO_FILE_SENDER_UI){
                Toasts.showShort(" 进入文件发送列表");
                NavigatorUtils.toFileSenderListUI(getContext());
                finishNormal();
            }else if(msg.what == MSG_TO_SHOW_SCAN_RESULT){
                getOrUpdateWifiScanResult();
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_TO_SHOW_SCAN_RESULT), 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_receiver);
        ButterKnife.bind(this);
        LogUtils.e(" ChooseReceiverActivity onCreate");
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        closeSocket();

        //断开当前的Wifi网络
        WifiMgr.getInstance(getContext()).disconnectCurrentNetwork();

        this.finish();
    }
    /**
     * 成功进入 文件发送列表UI 调用的finishNormal()
     */
    private void finishNormal(){
        closeSocket();
        this.finish();
    }

    /**
     * 初始化
     */
    private void init() {

        radarScanView.startScan();
        if (!WifiMgr.getInstance(getContext()).isWifiEnable()) {
            WifiMgr.getInstance(getContext()).openWifi();
        }
        getOrUpdateWifiScanResult();
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_TO_SHOW_SCAN_RESULT), 1000);
    }

    /**
     * 获取或者更新wifi扫描列表
     */
    private void getOrUpdateWifiScanResult() {
        WifiMgr.getInstance(getContext()).startScan();
        mScanResultList = WifiMgr.getInstance(getContext()).getmScanResultList();
        mScanResultList = filterWithNoPassword(mScanResultList);

        if (mScanResultList != null){
            mWifiScanResultAdapter = new WifiScanResultAdapter(getContext(),mScanResultList);
            lv_result.setAdapter(mWifiScanResultAdapter);
            lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ScanResult scanResult = mScanResultList.get(position);
                    LogUtils.e(TAG + " ###select the wifi info" + scanResult.toString());

                    //1、连接网络
                    String ssid = Constant.DEFAULT_SSID;
                    ssid = scanResult.SSID;
                    WifiMgr.getInstance(getContext()).openWifi();
                    WifiMgr.getInstance(getContext()).addNetwork(WifiMgr.createWifiCfg(ssid,null,WifiMgr.WIFICIPHER_NOPASS));

                    //2、发送ＵＤＰ通知信息到文件接收方 同时开启ServerSocketRunnable
                    mUdpServerRuannable = createSendMsgToServerRunnable(WifiMgr.getInstance(getContext()).getIpAddressFromHotspot());
                    AppContext.MAIN_EXECUTOR.execute(mUdpServerRuannable);

                }
            });
        }
    }

    @OnClick({R.id.tv_back,R.id.radarscan})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.radarscan:
                LogUtils.e(TAG+" radarView click");
                mUdpServerRuannable = createSendMsgToServerRunnable(WifiMgr.getInstance(getContext()).getIpAddressFromHotspot());
                AppContext.MAIN_EXECUTOR.execute(mUdpServerRuannable);
                break;
        }
    }

    /**
     * 创建发送ＵＤＰ消息到 文件接收方 的服务线程
     * @param serverIp
     * @return
     */
    private Runnable createSendMsgToServerRunnable(final String serverIp){

        LogUtils.e(TAG + " serverIp : " + serverIp);

        return new Runnable() {
            @Override
            public void run() {
                try {
                    startFileSenderServer(serverIp, Constant.DEFAULT_SERVER_COM_PORT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    DatagramSocket mDatagramSocket;
    /**
     * 开启 文件发送方 通信服务 (必须在子线程执行)
     * @param targetIpAddr
     * @param serverPort
     * @throws Exception
     */
    private void startFileSenderServer(String targetIpAddr , int serverPort)throws Exception{
        int count = 0;
        while(targetIpAddr.equals(Constant.DEFAULT_UNKOWN_IP)
                && count < Constant.DEFAULT_TRY_TIME){
            Thread.sleep(1000);
            targetIpAddr = WifiMgr.getInstance(getContext()).getIpAddressFromHotspot();
            LogUtils.e(TAG + " receiver serverIp " + targetIpAddr);
            count++;
        }

        // 即使获取到连接的热点wifi的IP地址也是无法连接网络 所以采取此策略
        count = 0;
        while(!NetUtils.pingIpAddress(targetIpAddr)
                && count < Constant.DEFAULT_TRY_TIME){
            Thread.sleep(500);
            LogUtils.e(TAG + " try to ping " + targetIpAddr + "-" + count);
            count++;
        }

        mDatagramSocket = new DatagramSocket(serverPort);
        byte[] receiveData = new byte[1024];
        byte[] sendData = null;
        InetAddress ipAddress = InetAddress.getByName(targetIpAddr);

        //0、发送 即将发送的文件列表 到文件接收方
        sendFileInfoListToFileReceiverWithUdp(serverPort,ipAddress);
        //1、发送 文件接收方 初始化
        sendData = Constant.MSG_FILE_RECEIVER_INIT.getBytes(BaseTransfer.UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(sendData,
                sendData.length,ipAddress,serverPort);
        mDatagramSocket.send(sendPacket);
        LogUtils.e(TAG + " Send Msg To FileReceiver " + Constant.MSG_FILE_RECEIVER_INIT);
        //2、接收 文件接收方 初始化 反馈
        while (true){
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            mDatagramSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(),BaseTransfer.UTF_8).trim();

            LogUtils.e(TAG + " Get the msg from FileReceiver " + response);
            if (response != null &&
                    response.equals(Constant.MSG_FILE_RECEIVER_INIT_SUCCESS)){
                //进入文件发送列表界面 并通知文件接收方进入文件接收列表界面
                mHandler.obtainMessage(MSG_TO_FILE_SENDER_UI).sendToTarget();
            }
        }
    }

    /**
     * 发送即将发送的文件列表到文件接收方
     * @param serverPort
     * @param ipAddress
     * @throws Exception
     */
    private void sendFileInfoListToFileReceiverWithUdp(int serverPort,InetAddress ipAddress)throws Exception{
        //将发送的List<FileInof> 发送给 文件接收方
        //如何将发送的数据列表封装成JSON
        Map<String,FileInfo> sendFileInfoMap = AppContext.getAppContext().getFileInfoMap();
        List<Map.Entry<String,FileInfo>> fileInfoMapList = new ArrayList<Map.Entry<String,FileInfo>>(sendFileInfoMap.entrySet());
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        //排序
        Collections.sort(fileInfoMapList,Constant.DEFAULT_COMPARATOR);
        for (Map.Entry<String,FileInfo> entry : fileInfoMapList){
            if (entry.getValue() != null){
                FileInfo fileInfo = entry.getValue();
                String fileInfoStr = FileInfo.toJsonStr(fileInfo);
                DatagramPacket sendFileInfoListPacket = new DatagramPacket(fileInfoStr.getBytes(),
                        fileInfoStr.getBytes().length,ipAddress,serverPort);

                try {
                    mDatagramSocket.send(sendFileInfoListPacket);
                    LogUtils.e(TAG + " sendFileInfoListToFileReceiverWithUdp " + fileInfoStr + "  success");
                }catch (Exception e){
                    LogUtils.e(TAG + " sendFileInfoListToFileReceiverWithUdp " + fileInfoStr + "  failure");

                }
            }
        }

    }

    /**
     * 关闭 UDP Socket 流
     */
    private void closeSocket(){
        if (mDatagramSocket != null){
            mDatagramSocket.disconnect();
            mDatagramSocket.close();
            mDatagramSocket = null;
        }
    }

    public static final String NO_PASSWORD = "[ESS]";
    public static final String NO_PASSWORD_WPS = "[WPS][ESS]";

    /**
     * 过滤有密码的wifi扫描结果集合
     * @param scanResultList
     * @return
     */
    public static List<ScanResult> filterWithNoPassword(List<ScanResult> scanResultList){
        if (scanResultList == null || scanResultList.size() == 0){
            return scanResultList;
        }
        List<ScanResult> resultList = new ArrayList<ScanResult>();
        for (ScanResult scanResult : scanResultList){
            if (scanResult.capabilities != null
                    && scanResult.capabilities.equals(NO_PASSWORD)
                    || scanResult.capabilities != null
                    && scanResult.capabilities.equals(NO_PASSWORD_WPS)){
                resultList.add(scanResult);
            }
        }
        return resultList;
    }
}
