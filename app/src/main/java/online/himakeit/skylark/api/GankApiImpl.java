package online.himakeit.skylark.api;

import com.google.gson.Gson;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.gank.GankData;
import online.himakeit.skylark.model.gank.GankMeiZhiData;
import online.himakeit.skylark.presenter.implView.IGankFragment;
import online.himakeit.skylark.presenter.implView.IGankMeiZhiFragment;
import online.himakeit.skylark.util.CacheUtils;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/14 17:19
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class GankApiImpl {

    private static final String TAG = "GankApiImpl";
    private static Gson gson = new Gson();

    public static Subscription getGankData(final IGankFragment mGankFragment,
                                           final CacheUtils cacheUtils, String type , int t){
        mGankFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().getGankData(type,t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankData>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"getGankData onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankFragment.hideProgressDialog();
                        mGankFragment.showError(e.getMessage());
                        LogUtils.e(TAG,"getGankData onError " + e.toString());
                    }

                    @Override
                    public void onNext(GankData gankData) {
                        mGankFragment.hideProgressDialog();
                        cacheUtils.put(Config.GANK,gson.toJson(gankData));
                        LogUtils.e(TAG,"getGankData onNext " + gankData.getResults().get(0).getUrl());
                        mGankFragment.updateGankData(gankData.getResults());
                    }
                });
//        addSubscription(subscription);

        return subscription;
    }

    public static Subscription getGankMeiZhiData(final IGankMeiZhiFragment mGankMeiZhiFragment,
                                                 final CacheUtils cacheUtils, int t){
        mGankMeiZhiFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().getMeiZhiData(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankMeiZhiData>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"getGankMeiZhiData onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mGankMeiZhiFragment.hideProgressDialog();
                        mGankMeiZhiFragment.showError(e.getMessage());
                        LogUtils.e(TAG,"getGankMeiZhiData onError " + e.toString());
                    }

                    @Override
                    public void onNext(GankMeiZhiData gankMeiZhiData) {
                        mGankMeiZhiFragment.hideProgressDialog();
                        cacheUtils.put(Config.GANK, gson.toJson(gankMeiZhiData));
                        LogUtils.e(TAG,"getGankMeiZhiData onNext " + gankMeiZhiData.getResults().get(0).getUrl());
                        mGankMeiZhiFragment.updateGankMeiZhiData(gankMeiZhiData.getResults());
                    }
                });
//        addSubscription(subscription);
        return subscription;
    }
}
