package online.himakeit.skylark.presenter.implView;


import online.himakeit.skylark.model.topnews.NewsDetailBean;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 13:49
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ITopNewsDesFragment extends IBaseFragment {
    void updateListItem(NewsDetailBean newsList);
}
