package online.himakeit.skylark.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.util.NotificationUtils;
import online.himakeit.skylark.util.PreferencesUtils;


/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 8:34
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PreferencesUtils preferencesUtils = new PreferencesUtils(context);
        if (preferencesUtils.getBoolean("每天提醒", false)) {
            NotificationUtils.showNotification(context, MainActivity.class,
                    "一个木匣",
                    "今天的精彩内容已经准备好了，快来看看吧~",
                    R.mipmap.skylark,
                    R.mipmap.emoji_see_no_evil,
                    123123);
        }
    }
}
