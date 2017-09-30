package online.himakeit.skylark.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import online.himakeit.skylark.util.LogUtils;


/**
 * Created by：LiXueLong 李雪龙 on 17-7-3 下午7:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public abstract class SeletedFileListChangedBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = SeletedFileListChangedBroadcastReceiver.class.getSimpleName();
    //更新选择传送文件的Action
    public static final String ACTION_CHOOSE_FILE_LIST_CHANGED = "ACTION_CHOOSE_FILE_LIST_CHANGED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_CHOOSE_FILE_LIST_CHANGED)){//选中传送文件的改变
            LogUtils.e(TAG + "ACTION_CHOOSE_FILE_LIST_CHANGED--->>>");
            onSeletecdFileListChanged();
        }

    }
    /**
     * 选中传送文件的改变
     */
    public abstract void onSeletecdFileListChanged();
}
