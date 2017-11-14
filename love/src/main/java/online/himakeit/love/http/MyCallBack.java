package online.himakeit.love.http;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 16:55
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface MyCallBack {

    /**
     * 成功的回调对象
     *
     * @param what
     * @param result
     */
    void onSuccess(int what, Object result) ;

    /**
     * 成功的回调集合
     *
     * @param what
     * @param results
     */
    void onSuccessList(int what, List results);

    /**
     * 失败的回调
     *
     * @param what
     * @param result
     */
    void onFail(int what, String result);

}