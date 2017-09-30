package online.himakeit.skylark.presenter.implPresenter;

import online.himakeit.skylark.api.ApiManager;
import online.himakeit.skylark.model.topnews.NewsList;
import online.himakeit.skylark.presenter.ITopNewPresenter;
import online.himakeit.skylark.presenter.implView.ITopNewsFragment;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 17:18
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TopNewsNewPresenterImpl extends BasePresenterImpl implements ITopNewPresenter {

    private static final String TAG = "TopNewsNewPresenterImpl";

    ITopNewsFragment mITopNewsFragment;

    public TopNewsNewPresenterImpl(ITopNewsFragment mITopNewsFragment) {
        this.mITopNewsFragment = mITopNewsFragment;
    }

    @Override
    public void getNewsList(int t) {
        mITopNewsFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getTopNewsService().getNews(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsList>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"TopNews onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mITopNewsFragment.hideProgressDialog();
                        mITopNewsFragment.showError(e.toString());
                        LogUtils.e(TAG,"TopNews onError " + e.toString());
                    }

                    @Override
                    public void onNext(NewsList newsList) {
                        mITopNewsFragment.hideProgressDialog();
                        mITopNewsFragment.updateListItem(newsList);
                        LogUtils.e(TAG,"TopNews onNext "
                                + newsList.getNewsList().get(0).getDocid()
                                + "  "
                                + newsList.getNewsList().get(0).getTitle());
                    }
                });
        addSubscription(subscription);
    }
}
