package online.himakeit.skylark.activity;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.Constant;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.kuaichuan.FileInfo;
import online.himakeit.skylark.model.kuaichuan.IpPortInfo;
import online.himakeit.skylark.receiver.WifiAPBroadcastReceiver;
import online.himakeit.skylark.util.ApMgr;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.NavigatorUtils;
import online.himakeit.skylark.util.TextStrUtils;
import online.himakeit.skylark.util.WifiMgr;
import online.himakeit.skylark.view.RadarLayout;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-4 下午5:08
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ReceiverWaitingActivity extends OtherBaseActivity {

    private static final String TAG = ReceiverWaitingActivity.class.getSimpleName();
    /**
     * Topbar相关UI
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    /**
     * 其他UI
     */
    @Bind(R.id.radarLayout)
    RadarLayout radarLayout;
    @Bind(R.id.tv_device_name)
    TextView tv_device_name;
    @Bind(R.id.tv_desc)
    TextView tv_desc;


    WifiAPBroadcastReceiver mWifiAPBroadcastReceiver;
    boolean mIsInitialized = false;

    /**
     * 与文件发送方通信的线程
     */
    Runnable mUdpServerRunnable;

    public static final int MSG_TO_FILE_RECEIVER_UI = 0X88;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_TO_FILE_RECEIVER_UI){
                IpPortInfo ipPortInfo = (IpPortInfo) msg.obj;
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_IP_PORT_INFO,ipPortInfo);

                NavigatorUtils.toFileReceiverListUI(getContext(), bundle);

                finishNormal();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_waiting);
        ButterKnife.bind(this);
        LogUtils.e("ReceiverWaitingActivity onCreate");

        init();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mWifiAPBroadcastReceiver != null){
            unregisterReceiver(mWifiAPBroadcastReceiver);
            mWifiAPBroadcastReceiver = null;
        }
        closeSocket();

        //关闭热点
        ApMgr.disableAp(getContext());

        this.finish();
    }

    /**
     * 成功进入文件接受列表UI调用的 finishNormal()
     */
    private void finishNormal(){
        if (mWifiAPBroadcastReceiver != null){
            unregisterReceiver(mWifiAPBroadcastReceiver);
            mWifiAPBroadcastReceiver = null;
        }

        closeSocket();

        this.finish();
    }


    private void init() {
        radarLayout.setUseRing(true);
        radarLayout.setColor(getResources().getColor(R.color.white));
        radarLayout.setCount(4);
        radarLayout.start();

        //1.初始化热点
        WifiMgr.getInstance(getContext()).disableWifi();
        if (ApMgr.isApOn(getContext())){
            ApMgr.disableAp(getContext());
        }
        mWifiAPBroadcastReceiver = new WifiAPBroadcastReceiver() {
            @Override
            public void onWifiApEnabled() {
                LogUtils.e(TAG + "======>>>onWifiApEnabled !!!");
                if (!mIsInitialized){
                    mUdpServerRunnable = createSendMsgToFileSenderRunnable();
                    AppContext.MAIN_EXECUTOR.execute(mUdpServerRunnable);
                    mIsInitialized = true;

                    tv_desc.setText(getResources().getString(R.string.tip_now_init_is_finish));
                    tv_desc.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_desc.setText(getResources().getString(R.string.tip_is_waitting_connect));
                        }
                    },2*1000);
                }
            }
        };
        IntentFilter filter = new IntentFilter(WifiAPBroadcastReceiver.ACTION_WIFI_AP_STATE_CHANGED);
        registerReceiver(mWifiAPBroadcastReceiver,filter);

        ApMgr.isApOn(getContext());//check ap state
        String ssid = TextStrUtils.isNullOrBlank(Build.DEVICE) ? Constant.DEFAULT_SSID : Build.DEVICE;
        ApMgr.configApState(getContext(),ssid);//change ap state

        tv_device_name.setText(ssid);
        tv_desc.setText(getResources().getString(R.string.tip_now_is_initial));
    }

    @OnClick({R.id.tv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                onBackPressed();
                break;
        }
    }

    /**
     * 创建发送UDP消息 到文件发送方 的服务器
     * @return
     */
    private Runnable createSendMsgToFileSenderRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    startFileReceiverServer(Constant.DEFAULT_SERVER_COM_PORT);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }

    DatagramSocket datagramSocket;
    private void startFileReceiverServer(int serverPort)throws Exception{
        //网络连接上，无法获取IP的问题
        int count = 0;
        String localAddress = WifiMgr.getInstance(getContext()).getHotspotLocalIpAddress();
        while (localAddress.equals(Constant.DEFAULT_UNKOWN_IP) && count < Constant.DEFAULT_TRY_TIME){
            Thread.sleep(1000);
            localAddress = WifiMgr.getInstance(getContext()).getHotspotLocalIpAddress();
            LogUtils.e(TAG + "receiver get local ip " + localAddress);
            count++;
        }
        datagramSocket = new DatagramSocket(serverPort);
        byte[] receiveData = new byte[1024];
        byte[] sendData = null;
        while (true){
            // 1.接受文件发送方的消息
            DatagramPacket datagramPacket = new DatagramPacket(receiveData,receiveData.length);
            datagramSocket.receive(datagramPacket);
            String msg = new String(datagramPacket.getData()).trim();
            InetAddress inetAddress = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            if (msg != null && msg.startsWith(Constant.MSG_FILE_RECEIVER_INIT)){
                LogUtils.e(TAG + " Get the msg from FileReceiver " + Constant.MSG_FILE_RECEIVER_INIT);
                //进入文件接受列表界面 文件接收列表界面需要，通知文件发送方发送文件开始传输UDP通知
                mHandler.obtainMessage(MSG_TO_FILE_RECEIVER_UI,new IpPortInfo(inetAddress,port)).sendToTarget();
            }else{//接受发送方的 文件列表
                if (msg != null){
                    LogUtils.e(TAG + "Get the FileInfo from FileReceiver " + msg);
                    parseFileInfo(msg);
                }
            }
        }

    }

    /**
     * 解析FileInfo
     * @param msg
     */
    private void parseFileInfo(String msg){
        FileInfo fileInfo = FileInfo.toObject(msg);
        if (fileInfo != null && fileInfo.getFilePath() != null){
            AppContext.getAppContext().addReceiverFileInfo(fileInfo);
        }
    }

    /**
     * 关闭UDP Socket 流
     */
    private void closeSocket(){
        if (datagramSocket != null){
            datagramSocket.disconnect();
            datagramSocket.close();
            datagramSocket = null;
        }
    }

}
