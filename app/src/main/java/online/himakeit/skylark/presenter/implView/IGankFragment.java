package online.himakeit.skylark.presenter.implView;


import java.util.ArrayList;

import online.himakeit.skylark.model.gank.Gank;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 9:12
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface IGankFragment extends IBaseFragment {
    void updateGankData(ArrayList<Gank> list);
}
