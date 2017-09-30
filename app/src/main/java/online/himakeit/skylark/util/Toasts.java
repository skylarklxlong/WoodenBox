package online.himakeit.skylark.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 16:05
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class Toasts {

    public static Context sContext;


    private Toasts() {
    }


    public static void register(Context context) {
        sContext = context.getApplicationContext();
    }


    private static void check() {
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtils.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }

    /**
     * 在子线程中显示Toast，会报错：
     * java.lang.RuntimeException: Can't toast on a thread that has not called Looper.prepare()
     * 这句话的意思是：如果在一个线程中没有调用Looper.prepare(),就不能在该线程中创建Handler。
     * Looper.prepare();
     * Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
     * Looper.loop();
     *
     * @param resId
     */
    public static void showShort(int resId) {
        try {
            check();
            Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Looper.prepare();
            Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    }


    public static void showShort(String message) {
        try {
            check();
            Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Looper.prepare();
            Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    }


    public static void showLong(int resId) {
        try {
            check();
            Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Looper.prepare();
            Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }


    public static void showLong(String message) {
        try {
            check();
            Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Looper.prepare();
            Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }


    public static void showLongX2(String message) {
        showLong(message);
        showLong(message);
    }


    public static void showLongX2(int resId) {
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(int resId) {
        showLong(resId);
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(String message) {
        showLong(message);
        showLong(message);
        showLong(message);
    }
}
