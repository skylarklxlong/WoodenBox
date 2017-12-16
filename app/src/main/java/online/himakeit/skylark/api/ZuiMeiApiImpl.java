package online.himakeit.skylark.api;

import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageResponse;
import online.himakeit.skylark.presenter.implView.IZuiMeiPic;
import online.himakeit.skylark.util.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
public class ZuiMeiApiImpl {

    private static final String TAG = "ZuiMeiApiImpl";
    public final static String GET_DATA_FAIL = "获取数据失败";
    public final static String NET_FAIL = "网络出问题了";

    public static Subscription getBackgroundPic(final IZuiMeiPic iZuiMeiPic){
        iZuiMeiPic.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().getImage()
                .subscribeOn(Schedulers.io())
                /*.map(new Func1<ZuiMeiImageResponse, Boolean>() {
                    @Override
                    public Boolean call(ZuiMeiImageResponse zuiMeiImageResponse) {

                        if (zuiMeiImageResponse.getData() != null && zuiMeiImageResponse.getData().getImages() != null) {
                            try {
                                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                                File directory = new File(externalStorageDirectory, "Skylark");
                                if (!directory.exists()) {
                                    directory.mkdir();
                                }
                                File file = new File(directory, "/bg.jpg");
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                URL url = new URL(Config.ZUIMEI_PIC_BASE_URL + zuiMeiImageResponse.getData().getImages().get(0).getImageUrl()
                                        + "?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100");
                                LogUtils.e(TAG, "最美图片链接为： " + url.toString());
                                Bitmap bitmap = BitmapFactory.decodeStream(
                                        url.openConnection().getInputStream());
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        return false;
                    }
                })*/
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZuiMeiImageResponse>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"getBackground onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        iZuiMeiPic.hideProgressDialog();
                        iZuiMeiPic.showError(e.getMessage());
                        LogUtils.e(TAG,"getBackground onError " + e.toString());
                    }

                    @Override
                    public void onNext(ZuiMeiImageResponse zuiMeiImageResponse) {
                        iZuiMeiPic.hideProgressDialog();
                        iZuiMeiPic.updateZuiMeiPic(zuiMeiImageResponse.getData().getImages().get(0));
                        LogUtils.e(TAG,"getBackground onNext " + zuiMeiImageResponse.getData().getImages().get(0).getDescription());
                    }
                });
                /*.subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"getBackground onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        iZuiMeiPic.hideProgressDialog();
                        iZuiMeiPic.showError(e.getMessage());
                        iZuiMeiPic.updateZuiMeiPic();
                        LogUtils.e(TAG,"getBackground onError " + e.toString());
                    }

                    @Override
                    public void onNext(Boolean isOk) {
                        iZuiMeiPic.hideProgressDialog();
                        iZuiMeiPic.updateZuiMeiPic();
                        LogUtils.e(TAG,"getBackground onNext " + isOk);
                    }
                });*/
//        addSubscription(subscription);

        return subscription;
    }

    public static Call<ZuiMeiImageResponse> getZuiMeiPic(final int what ,final MobCallBack callBack){
        Call<ZuiMeiImageResponse> zuiMeiPic = ApiManager.getInstence().getZuiMeiService().getZuiMeiPic();
        zuiMeiPic.enqueue(new Callback<ZuiMeiImageResponse>() {
            @Override
            public void onResponse(Call<ZuiMeiImageResponse> call, Response<ZuiMeiImageResponse> response) {
                if (response.isSuccessful()){
                    LogUtils.show("response.isSuccessful()");
                    ZuiMeiImageResponse body = response.body();
                    if (body != null){
                        LogUtils.show("body != null");
                        callBack.onSuccessList(what,body.getData().getImages());
                    }else {
                        LogUtils.show("body == null");
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                }else {
                    LogUtils.show("!response.isSuccessful()");
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<ZuiMeiImageResponse> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
                LogUtils.show("onFailure" + t.getMessage());
            }
        });

        return zuiMeiPic;
    }
}
