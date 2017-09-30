package online.himakeit.skylark.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 9:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NotificationUtils {


    public static void showNotification(Context mContext, Class<?> targetActivity,
                                        String title, String content,
                                        int largeIcon, int smallIcon, int code) {

        //要跳转到的Activity
        Intent intent = new Intent(mContext, targetActivity);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setAutoCancel(true)//点击后消失
                .setContentTitle(title)//通知标题
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)//设置通知铃声
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, false)
                .setContentText(content);//通知内容

        if (Build.VERSION.SDK_INT >= 21) {
            builder.setLargeIcon(
                    BitmapFactory.decodeResource(mContext.getResources(), largeIcon))
                    .setSmallIcon(smallIcon);
        } else {
            builder.setSmallIcon(largeIcon);
        }

        NotificationManager manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        manager.notify(code, builder.build());
    }
}
