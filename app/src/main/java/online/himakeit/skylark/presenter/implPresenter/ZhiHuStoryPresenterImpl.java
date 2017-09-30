package online.himakeit.skylark.presenter.implPresenter;


import online.himakeit.skylark.api.ApiManager;
import online.himakeit.skylark.model.zhuhu.ZhiHuStory;
import online.himakeit.skylark.presenter.IZhiHuStoryPresenter;
import online.himakeit.skylark.presenter.implView.IZhiHuStory;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/23 14:31
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuStoryPresenterImpl extends BasePresenterImpl implements IZhiHuStoryPresenter {

    private static final String TAG = "ZhiHuStoryPresenterImpl";

    private IZhiHuStory mIZhiHuStory;

    public ZhiHuStoryPresenterImpl(IZhiHuStory zhiHuStory) {
        if (zhiHuStory == null){
            throw new IllegalArgumentException("zhihuStory must not be null");
        }
        this.mIZhiHuStory = zhiHuStory;
    }

    @Override
    public void getZhiHuStory(String id) {
        Subscription subscription = ApiManager.getInstence().getZhiHuApiService().getZhiHuStory(id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhiHuStory>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"getZhiHuStory onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIZhiHuStory.showError(e.getMessage());
                        LogUtils.e(TAG,"getZhiHuStory onError " + e.toString());
                    }

                    @Override
                    public void onNext(ZhiHuStory zhiHuStory) {
                        LogUtils.e(TAG,"getZhiHuStory onNext " + zhiHuStory.getmShareUrl());
                        mIZhiHuStory.showZhiHuStory(zhiHuStory);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getGuokrArticle(String id) {

    }
}
