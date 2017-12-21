package online.himakeit.skylark.util;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/21 14:57
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class DataUtils {

    public static int[] reduce(int width, int height) {
        int[] result = new int[2];

        int fz = Math.abs(width); //取分子分母的绝对值
        int fm = Math.abs(height);
        int mod = fz % fm;//求分子除以分母的余数
        while (mod > 0) { //求分子分母的最大公因数
            fz = fm;
            fm = mod;
            mod = fz % fm;
        }
        result[0] = width / fm;//分子分母都除以最大公因数
        result[1] = height / fm;
        return result;
    }

    public static void main(String[] args) {
        int fenzi = 380;
        int fenmu = 192;
        int fz = Math.abs(fenzi); //取分子分母的绝对值
        int fm = Math.abs(fenmu);
        int mod = fz % fm;
        while (mod > 0) { //求分子分母的最大公因数
            fz = fm;
            fm = mod;
            mod = fz % fm;
        }
        int[] reduce = reduce(16, 9);
        System.out.println("fenzi " + reduce[0]);//分子分母都除以最大公因数
        System.out.println("fenmu " + reduce[1]);
    }
}
