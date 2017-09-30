package online.himakeit.skylark.util;

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
}
