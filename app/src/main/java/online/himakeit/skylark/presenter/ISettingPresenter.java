package online.himakeit.skylark.presenter;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/8 13:44
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ISettingPresenter extends BasePresenter {

    //计算Cache大小
    void initCache();

    //清除缓存
    void cleanCache();

    //初始化版本更新的状态
    void initAppUpdateState();

    //检查更新
    void checkAppUpdate();

}
