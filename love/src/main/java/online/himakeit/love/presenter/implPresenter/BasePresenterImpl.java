package online.himakeit.love.presenter.implPresenter;

import online.himakeit.love.presenter.IBasePresenter;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 17:14
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BasePresenterImpl<T> implements IBasePresenter {

    public T mView;

    protected void attachView(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
