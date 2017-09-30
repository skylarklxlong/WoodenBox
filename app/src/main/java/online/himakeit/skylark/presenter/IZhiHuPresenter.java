package online.himakeit.skylark.presenter;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/23 14:19
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface IZhiHuPresenter extends BasePresenter {
    void getLastZhiHuNews();
    void getTheDaily(String date);
    void getLastFromCache();
}
