package online.himakeit.love.utils;

import android.app.Activity;

import online.himakeit.love.R;


/**
 * Created by Administrator on 2017/7/31.
 */

public class ThemeUtils {
    public static int isChange;

    public static void changeTheme(Activity activity) {
        if (isChange == 1) {
            activity.setTheme(R.style.AppTheme);
        }
        if (isChange == 2) {
            activity.setTheme(R.style.AppTheme_Red);
        }
        if (isChange == 3) {
            activity.setTheme(R.style.AppTheme_Purple);
        }
        if (isChange == 4) {
            activity.setTheme(R.style.AppTheme_Blue);
        }
        if (isChange == 5) {
            activity.setTheme(R.style.AppTheme_Orange);
        }
        if (isChange == 6) {
            activity.setTheme(R.style.AppTheme_Green);
        }
    }
}