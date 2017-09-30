package online.himakeit.skylark.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import online.himakeit.skylark.util.AlarmManagerUtils;


/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 10:15
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class KeepAlarmLiveReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
            AlarmManagerUtils.register(context);
        }
    }
}
