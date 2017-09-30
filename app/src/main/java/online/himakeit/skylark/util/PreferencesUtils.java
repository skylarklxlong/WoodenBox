package online.himakeit.skylark.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 8:52
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description: SharedPreferences封装类
 */
public class PreferencesUtils {
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public PreferencesUtils(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    //-----------------------boolean--------------------------
    public void saveBoolean(int keyResId, boolean value) {
        String key = mContext.getString(keyResId);
        saveBoolean(key, value);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        return mSharedPreferences.getBoolean(key, def);
    }

    public boolean getBoolean(int keyResId, boolean def) {
        String key = mContext.getString(keyResId);
        return mSharedPreferences.getBoolean(key, def);
    }

    //-----------------------int--------------------------
    public void saveInt(int keyResId, int value) {
        String key = mContext.getString(keyResId);
        saveInt(key, value);
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int def) {
        return mSharedPreferences.getInt(key, def);
    }

    public int getInt(int keyResId, int def) {
        String key = mContext.getString(keyResId);
        return mSharedPreferences.getInt(key, def);
    }
}
