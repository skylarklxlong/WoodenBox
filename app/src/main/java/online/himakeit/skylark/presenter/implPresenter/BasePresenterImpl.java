package online.himakeit.skylark.presenter.implPresenter;


import online.himakeit.skylark.presenter.BasePresenter;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 17:12
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BasePresenterImpl implements BasePresenter {
    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }
    @Override
    public void unsubscrible() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
