package online.himakeit.skylark.callback;

import org.xutils.ex.DbException;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/16 10:52
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface LoadMoreListener {
    void loadMore() throws DbException;
}
