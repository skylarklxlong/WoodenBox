package online.himakeit.skylark.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

import online.himakeit.skylark.Constant;
import online.himakeit.skylark.activity.ChooseFileActivity;
import online.himakeit.skylark.activity.ChooseReceiverActivity;
import online.himakeit.skylark.activity.FileReceiverActivity;
import online.himakeit.skylark.activity.FileSenderActivity;
import online.himakeit.skylark.activity.ReceiverWaitingActivity;
import online.himakeit.skylark.activity.WebTransferActivity;
import online.himakeit.skylark.util.FileUtils;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-3 下午7:48
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description: UI导航工具类
 */
public class NavigatorUtils {

    static Intent mIntent;

    /**
     * 跳转到文件选择UI
     * @param context
     * @param isWebTransfer 是否要进行网页传送
     */
    public static void toChooseFileUI(Context context,boolean isWebTransfer){
        if (context == null){
            throw new RuntimeException("Context not be null");
        }
        mIntent = new Intent(context, ChooseFileActivity.class);
        mIntent.putExtra(Constant.KEY_WEB_TRANSFER_FLAG,isWebTransfer);
        context.startActivity(mIntent);
    }

    /**
     * 跳转到文件选择UI
     * @param context
     */
    public static void toChooseFileUI(Context context){
        toChooseFileUI(context,false);
    }

    /**
     * 跳转到选择文件接受者UI
     * @param context
     */
    public static void toChooseReceiverUI(Context context){
        if (context == null){
            throw new RuntimeException("Context not be null");
        }
        mIntent = new Intent(context, ChooseReceiverActivity.class);
        context.startActivity(mIntent);
    }

    /**
     * 跳转到文件接受者等待UI
     * @param context
     */
    public static void toReceiverWaitingUI(Context context){
        if(context == null) {
            throw new RuntimeException("Context not be null!!!");
        }
        mIntent = new Intent(context, ReceiverWaitingActivity.class);
        context.startActivity(mIntent);
    }

    /**
     * 跳转到文件发送列表UI
     * @param context
     */
    public static void toFileSenderListUI(Context context){
        if(context == null) {
            throw new RuntimeException("Context not be null!!!");
        }
        mIntent = new Intent(context, FileSenderActivity.class);
        context.startActivity(mIntent);
    }

    /**
     * 跳转到文件接受列表UI
     * @param context
     */
    public static void toFileReceiverListUI(Context context, Bundle bundle){
        if(context == null) {
            throw new RuntimeException("Context not be null!!!");
        }
        mIntent = new Intent(context, FileReceiverActivity.class);
        mIntent.putExtras(bundle);
        context.startActivity(mIntent);
    }

    /**
     * 打开指定的APP文件存储文件夹
     * @param context
     */
    public static void toSystemFileChooser(Context context){
        if(context == null) {
            throw new RuntimeException("Context not be null!!!");
        }

        mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        File file = new File(FileUtils.getRootDirPath());
        Uri uri = Uri.fromFile(file);
        mIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setDataAndType(uri, "*/*");
        context.startActivity(mIntent);
    }

    /**
     * 跳转到网页传UI
     * @param context
     */
    public static void toWebTransferUI(Context context){
        if(context == null) {
            throw new RuntimeException("Context not be null!!!");
        }

        mIntent = new Intent(context, WebTransferActivity.class);
        context.startActivity(mIntent);
    }

}
