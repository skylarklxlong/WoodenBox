package online.himakeit.skylark.presenter.implView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 17:10
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description: fragment 要实现的接口
 */
public interface IBaseFragment {
    void showProgressDialog();

    void hideProgressDialog();

    void showError(String error);
}