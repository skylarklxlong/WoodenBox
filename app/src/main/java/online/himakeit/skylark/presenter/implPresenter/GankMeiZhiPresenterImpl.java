package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;

import com.google.gson.Gson;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.api.ApiManager;
import online.himakeit.skylark.model.gank.GankMeiZhiData;
import online.himakeit.skylark.presenter.IGankMeiZhiPresenter;
import online.himakeit.skylark.presenter.implView.IGankMeiZhiFragment;
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
public class GankMeiZhiPresenterImpl extends BasePresenterImpl implements IGankMeiZhiPresenter {

    private static final String TAG = "GankMeiZhiPresenterImpl";

    private IGankMeiZhiFragment mGankMeiZhiFragment;
    private CacheUtils cacheUtils;
    private Gson gson = new Gson();

    public GankMeiZhiPresenterImpl(Context mContext, IGankMeiZhiFragment mGankMeiZhiFragment) {
        this.mGankMeiZhiFragment = mGankMeiZhiFragment;
        cacheUtils = CacheUtils.get(mContext);
    }

    @Override
    public void getGankMeiZhiData(int t) {
        mGankMeiZhiFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getGankService().getMeiZhiData(t)
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
        addSubscription(subscription);

    }
}
