package online.himakeit.skylark.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.receiver.WifiAPBroadcastReceiver;
import online.himakeit.skylark.util.LogUtils;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-4 下午5:23
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class WebTransferActivity extends OtherBaseActivity {

    private static final String TAG = WebTransferActivity.class.getSimpleName();

    /**
     * Topbar 相关UI
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_tip_1)
    TextView tv_tip_1;
    @Bind(R.id.tv_tip_2)
    TextView tv_tip_2;

    WifiAPBroadcastReceiver mWifiAPBroadcastReceiver;
    boolean mIsInitialized = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_transfer);
        LogUtils.e("WebTransferActivity onCreate");
    }
}
