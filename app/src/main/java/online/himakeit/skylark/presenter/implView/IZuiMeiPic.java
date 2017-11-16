package online.himakeit.skylark.presenter.implView;


import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageItem;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 20:15
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface IZuiMeiPic extends IBaseFragment {
    void updateZuiMeiPic(ZuiMeiImageItem zuiMeiImageItem);

    void showToast(String msg);

    void showAppUpdateDialog(AppUpdateInfo appUpdateInfo);
}
