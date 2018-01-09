package online.himakeit.skylark.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import online.himakeit.skylark.AppContext;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-24 上午9:59
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TextStrUtils {
    /**
     * 判断文本是否Null或者是空白
     *
     * @param str
     * @return
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.equals("");
    }

    /**
     * 验证手机号码格式
     *
     * @param number
     * @return
     */
    public static boolean isMobileNum(String number) {
        /**
         * [1][3578]\d{9}
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        /**
         * "[1]"代表第1位为数字1，
         * "[358]"代表第二位可以为3、5、8中的一个，
         * "\\d{9}"代表后面是可以是0～9的数字，有9位。
         * String num = "[1][3578]\\d{9}";
         * if (TextUtils.isEmpty(number)) {
         * return false;
         * } else {
         * //matches():字符串是否在给定的正则表达式匹配
         * return number.matches(num);
         * }
         */
        /**
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         *
         * ”^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\d{8}$”这句话其实很简单：
         * ①130-139这十个前三位已经全部开通，后面8位每一位都是0-9之间的任意数；
         * ②14开头的目前只有145、147、149三位，后面8位每一位都是0-9之间的任意数；
         * ③15开头的除了154以外第三位可以随意取，后面8位每一位都是0-9之间的任意数；
         * ④180-189这十个前三位已经全部开通，后面8位每一位都是0-9之间的任意数；
         * ⑤17开头的目前有170、171、173、175、176、177、178这七位，后面8位每一位都是0-9之间的任意数；
         */
        String num = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\\\d{8}$";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }


    }

    /**
     * copy文本
     * @param text
     */
    public static void copyText(String text) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        ClipboardManager cm = (ClipboardManager) AppContext.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData clipData = ClipData.newPlainText("text", text);
        cm.setPrimaryClip(clipData);
        Toasts.showShort("复制成功");
    }
}
