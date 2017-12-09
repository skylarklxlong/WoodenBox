package online.himakeit.skylark.presenter.implView;


import online.himakeit.skylark.model.fir.AppUpdateInfo;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/8 11:41
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ISettingView extends IBaseFragment {

    void setCacheSize(String cacheSize);

    void setAppUpdateState(boolean flag);

    void showAppUpdateDialog(AppUpdateInfo appUpdateInfo);

    void showBasesProgressSuccess(String msg);

    void showToast(String msg);

}
