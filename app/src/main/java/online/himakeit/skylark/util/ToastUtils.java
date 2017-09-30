package online.himakeit.skylark.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-20 下午8:21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ToastUtils {
    static Toast toast = null;
    public static void show(Context context, String text) {
        try {
            if(toast!=null){
                toast.setText(text);
            }else{
                toast= Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
