package online.himakeit.skylark.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 18:26
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class Shares {

    public static void share(Context context,String extraText){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_SUBJECT,"分享");
        textIntent.putExtra(Intent.EXTRA_TEXT,extraText);
        textIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(textIntent,"分享"));
    }

    public static void share(Context context,int stringRes){
        share(context,context.getString(stringRes));
    }

    public static void shareImage(Context context, Uri uri,String title){
        Intent imageIntent = new Intent();
        imageIntent.setAction(Intent.ACTION_SEND);
        imageIntent.putExtra(Intent.EXTRA_STREAM,uri);
        imageIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(imageIntent,title));
    }
}
