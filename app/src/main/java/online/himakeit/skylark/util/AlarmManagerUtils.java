package online.himakeit.skylark.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import online.himakeit.skylark.receiver.AlarmReceiver;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 10:00
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class AlarmManagerUtils {

    public static void register(Context mContext) {

        Calendar today = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 2);
        today.set(Calendar.MINUTE, 00);
        today.set(Calendar.SECOND, 00);

//        if (now.after(today)) {
//            return;
//        }

        Intent intent = new Intent("com.skylark.myapplication.alarm");
        intent.setClass(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 520,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(
                mContext.ALARM_SERVICE);
        /**
         * 从today.getTimeInMillis()开始，没过5 * 100提醒一次
         */
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(),
                5 * 100, pendingIntent);

    }

}
