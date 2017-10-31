package online.himakeit.skylark.util;

import android.text.TextUtils;

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
     * @param number
     * @return
     */
    public static boolean isMobileNum(String number){
        /**
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        /**
         * "[1]"代表第1位为数字1，
         * "[358]"代表第二位可以为3、5、8中的一个，
         * "\\d{9}"代表后面是可以是0～9的数字，有9位。
         */
        String num = "[1][3578]\\d{9}";
        if (TextUtils.isEmpty(number)){
            return false;
        }else {
            //matches():字符串是否在给定的正则表达式匹配
         return number.matches(num);
        }
    }
}
