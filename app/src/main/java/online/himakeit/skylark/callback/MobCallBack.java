package online.himakeit.skylark.callback;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/26 19:04
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:Mob网络回调
 */
public interface MobCallBack {
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
