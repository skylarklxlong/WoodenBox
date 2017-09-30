package online.himakeit.skylark.model.kuaichuan;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/24 14:25
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class IpPortInfo implements Serializable {
    InetAddress inetAddress;
    int port;

    public IpPortInfo(InetAddress inetAddress , int port){
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
