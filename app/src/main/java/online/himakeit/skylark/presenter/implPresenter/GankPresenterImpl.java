package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;

import com.google.gson.Gson;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.api.ApiManager;
import online.himakeit.skylark.model.gank.GankData;
import online.himakeit.skylark.presenter.IGankPresenter;
import online.himakeit.skylark.presenter.implView.IGankFragment;
import online.himakeit.skylark.util.CacheUtils;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 11:12
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class GankPresenterImpl extends BasePresenterImpl implements IGankPresenter {

    private static final String TAG = "GankPresenterImpl";

    private IGankFragment mGankFragment;
    private CacheUtils cacheUtils;
    private Gson gson = new Gson();

    public GankPresenterImpl(Context mContext ,IGankFragment mGankFragment) {
        this.mGankFragment = mGankFragment;
        cacheUtils = CacheUtils.get(mContext);
    }

    @Override
    public void getGankData(String type ,int t) {
        mGankFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getGankService().getGankData(type,t)
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
        addSubscription(subscription);

    }
}
