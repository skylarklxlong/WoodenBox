package online.himakeit.skylark.util;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-6 上午9:51
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface Transferable {
    /**
     *
     * @throws Exception
     */
    void init() throws Exception;


    /**
     *
     * @throws Exception
     */
    void parseHeader() throws Exception;


    /**
     *
     * @throws Exception
     */
    void parseBody() throws Exception;


    /**
     *
     * @throws Exception
     */
    void finish() throws Exception;
}
