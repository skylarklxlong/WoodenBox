package online.himakeit.skylark.util;

import android.content.Context;
import android.widget.Toast;

import online.himakeit.skylark.AppContext;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/9 19:05
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MyToast {


    private static Toast mToast = null;

    private static void showShortToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showShortToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(AppContext.getAppContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
