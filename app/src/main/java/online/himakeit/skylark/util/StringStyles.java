package online.himakeit.skylark.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 9:22
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class StringStyles {
    public static SpannableString format(Context context,String text,int style){
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context,style),0,text.length(),0);

        return spannableString;
    }
}
