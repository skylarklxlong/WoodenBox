package online.himakeit.skylark.presenter.implPresenter;


import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.topnews.NewsDetailBean;
import online.himakeit.skylark.presenter.ITopNewDesPresenter;
import online.himakeit.skylark.presenter.implView.ITopNewsDesFragment;
import online.himakeit.skylark.util.JsonUtils;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.OkHttpUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 13:52
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TopNewsNewDesPresenterImpl extends BasePresenterImpl implements ITopNewDesPresenter {

    private static final String TAG = "TopNewsNewDesPresenterI";

    ITopNewsDesFragment mITopNewsDesFragment;

    public TopNewsNewDesPresenterImpl(ITopNewsDesFragment mITopNewsDesFragment) {
        if (mITopNewsDesFragment == null) {
            throw new IllegalArgumentException(" must not be null !");
        }
        this.mITopNewsDesFragment = mITopNewsDesFragment;
    }

    private String getTopNewsDetailUrl(String docid) {
        StringBuffer stringBuffer = new StringBuffer(Config.TOPNEWS_DETAIL_BASE_URL);
        stringBuffer.append(docid).append(Config.TOPNEWS_DETAIL_END_URL);
        return stringBuffer.toString();
    }

    @Override
    public void getDescribleMessage(final String docid) {
        mITopNewsDesFragment.showProgressDialog();
        String url = getTopNewsDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> stringResultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                mITopNewsDesFragment.hideProgressDialog();
                LogUtils.show(TAG, "TopNewsDetail onSuccess " + response);
                NewsDetailBean newsDetailBean = JsonUtils.readJsonNewsDetailBeans(response, docid);
                LogUtils.show(TAG, "NewsDetailBean  解析完成" + newsDetailBean);
                mITopNewsDesFragment.updateListItem(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                mITopNewsDesFragment.hideProgressDialog();
                mITopNewsDesFragment.showError(e.toString());
                LogUtils.e(TAG, "TopNewsDetail onFailure " + e.toString());
            }
        };
        OkHttpUtils.get(url, stringResultCallback);

    }

    /*@Override
    public void getDescribleMessage(final String docid) {
        mITopNewsDesFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getTopNewsService().getNewsDetail(docid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.show("TopNewsDetail onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mITopNewsDesFragment.hidProgressDialog();
                        mITopNewsDesFragment.showError(e.toString());
                        LogUtils.show("TopNewsDetail onError " + e.toString());
                    }

                    @Override
                    public void onNext(String result) {
                        mITopNewsDesFragment.hidProgressDialog();
                        LogUtils.show("TopNewsDetail onNext " + result);
                        NewsDetailBean newsDetailBean = JsonUtils.readJsonNewsDetailBeans(result,docid);
//                        NewsDetailBean newsDetailBean = JsonUtils.jsonToNewsDetailBeans(o.toString(),docid);
                        LogUtils.show("NewsDetailBean  " + newsDetailBean.toString());
                        mITopNewsDesFragment.updateListItem(newsDetailBean);

                    }

                });
        addSubscription(subscription);
    }*/
}
