package online.himakeit.skylark.api;

import com.google.gson.Gson;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.zhuhu.ZhiHuDaily;
import online.himakeit.skylark.model.zhuhu.ZhiHuDailyItem;
import online.himakeit.skylark.presenter.implView.IZhiHuFragment;
import online.himakeit.skylark.util.CacheUtils;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/14 17:20
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuApiImpl {

    private static final String TAG = "ZhiHuApiImpl";

    private static Gson gson = new Gson();

    public static Subscription getLastZhiHuNews(final IZhiHuFragment zhiHuFragment, final CacheUtils cacheUtil) {
        zhiHuFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().getLastDaily()
                .map(new Func1<ZhiHuDaily, ZhiHuDaily>() {
                    @Override
                    public ZhiHuDaily call(ZhiHuDaily zhiHuDaily) {
                        String date = zhiHuDaily.getDate();
                        for (ZhiHuDailyItem zhiHuDailyItem : zhiHuDaily.getStories()) {
                            zhiHuDailyItem.setDate(date);
                        }
                        return zhiHuDaily;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG, "getLastZhiHuNews onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        zhiHuFragment.hideProgressDialog();
                        zhiHuFragment.showError(e.getMessage());
                        LogUtils.e(TAG, "getLastZhiHuNews onError " + e.toString());
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        zhiHuFragment.hideProgressDialog();
                        cacheUtil.put(Config.ZHIHU, gson.toJson(zhiHuDaily));
                        LogUtils.e(TAG, "getLastZhiHuNews onNext " + zhiHuDaily.getDate());
                        zhiHuFragment.updateList(zhiHuDaily);
                    }
                });
//        addSubscription(subscription);
        return subscription;
    }

    public static Subscription getTheDaily(final IZhiHuFragment zhiHuFragment, String date) {
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().getTheDaily(date)
                .map(new Func1<ZhiHuDaily, ZhiHuDaily>() {
                    @Override
                    public ZhiHuDaily call(ZhiHuDaily zhiHuDaily) {
                        String date = zhiHuDaily.getDate();
                        for (ZhiHuDailyItem zhiHuDailyItem : zhiHuDaily.getStories()) {
                            zhiHuDailyItem.setDate(date);
                        }
                        return zhiHuDaily;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.show("getTheDaily onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        zhiHuFragment.hideProgressDialog();
                        LogUtils.show("getTheDaily onError " + e.toString());
                        zhiHuFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        zhiHuFragment.hideProgressDialog();
                        LogUtils.show("getTheDaily onNext " + zhiHuDaily.getDate());
                        zhiHuFragment.updateList(zhiHuDaily);
                    }
                });
//        addSubscription(subscription);
        return subscription;
    }

    public static void getLastFromCache(final IZhiHuFragment zhiHuFragment, final CacheUtils cacheUtil){
        if (cacheUtil.getAsJSONObject(Config.ZHIHU) != null){
            ZhiHuDaily zhiHuDaily = gson.fromJson(cacheUtil.getAsJSONObject(Config.ZHIHU).toString(), ZhiHuDaily.class);
            zhiHuFragment.updateList(zhiHuDaily);
        }
    }
}
