package online.himakeit.love.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import online.himakeit.love.activity.WebActivity;


/**
 * Created by maning on 16/3/3.
 * <p/>
 * 页面跳转
 */
public class IntentUtils {

    public static final String ImagePositionForImageShow = "PositionForImageShow";
    public static final String ImageArrayList = "BigImageArrayList";
    public static final String WebTitleFlag = "WebTitleFlag";
    public static final String WebTitle = "WebTitle";
    public static final String WebUrl = "WebUrl";
    public static final String DayDate = "DayDate";


    public static void startToWebActivity(Context context, String titleFlag, String title, String url) {
        Intent intent = new Intent(context.getApplicationContext(), WebActivity.class);
        intent.putExtra(WebTitleFlag, titleFlag);
        intent.putExtra(WebTitle, title);
        intent.putExtra(WebUrl, url);
        context.startActivity(intent);
    }

    public static void startAppShareText(Context context, String shareTitle, String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public static void startAppShareImage(Context context, String shareTitle, String shareText, Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

}
