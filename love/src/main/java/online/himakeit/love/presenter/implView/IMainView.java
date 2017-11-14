package online.himakeit.love.presenter.implView;

import online.himakeit.love.bean.AppUpdateInfo;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 17:12
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface IMainView {

    void showToast(String msg);

    void showAppUpdateDialog(AppUpdateInfo appUpdateInfo);

}
