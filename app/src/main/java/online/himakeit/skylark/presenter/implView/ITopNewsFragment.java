package online.himakeit.skylark.presenter.implView;


import online.himakeit.skylark.model.topnews.NewsList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 17:11
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ITopNewsFragment extends IBaseFragment {
    void updateListItem(NewsList newsList);
}
