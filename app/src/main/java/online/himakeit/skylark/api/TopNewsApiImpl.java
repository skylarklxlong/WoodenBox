package online.himakeit.skylark.api;

import online.himakeit.skylark.model.topnews.NewsList;
import online.himakeit.skylark.presenter.implView.ITopNewsFragment;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/14 17:20
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TopNewsApiImpl {

    private static final String TAG = "TopNewsApiImpl";

    public static Subscription getTopNewsList(final ITopNewsFragment mITopNewsFragment,int t){
        mITopNewsFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().getNews(t)
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
//        addSubscription(subscription);
        return subscription;
    }
}
