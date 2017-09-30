package online.himakeit.skylark.util;

import java.io.IOException;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-6 上午9:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NetUtils {

    /**
     * 判断指定的ipaddress是否可以ping
     * @param ipAddress
     * @return
     */
    public static boolean pingIpAddress(String ipAddress){

        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/ping -c 1 -w 100 " + ipAddress);
            int status = process.waitFor();

            if (status == 0){
                return true;
            }else {
                return false;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
