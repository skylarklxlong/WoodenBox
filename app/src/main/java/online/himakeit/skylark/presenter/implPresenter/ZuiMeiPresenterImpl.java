package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;

import online.himakeit.skylark.api.ZuiMeiApiImpl;
import online.himakeit.skylark.presenter.IZuiMeiPresenter;
import online.himakeit.skylark.presenter.implView.IZuiMeiPic;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/14 20:17
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZuiMeiPresenterImpl extends BasePresenterImpl implements IZuiMeiPresenter {

    private static final String TAG = "ZuiMeiPresenterImpl";

    IZuiMeiPic iZuiMeiPic;
    Context context;

    public ZuiMeiPresenterImpl(IZuiMeiPic iZuiMeiPic, Context context) {
        if (iZuiMeiPic == null) {
            throw new IllegalArgumentException("IZuiMeiPic must not be null!");
        }
        this.iZuiMeiPic = iZuiMeiPic;
        this.context = context;
    }

    @Override
    public void getBackground() {
        addSubscription(ZuiMeiApiImpl.getBackgroundPic(iZuiMeiPic));
        // TODO: 2017/10/16 测试mob接口用
//        addSubscription(MobApiImpl.queryBankCradInfo("6228480058489875078"));
        /*
        iZuiMeiPic.showProgressDialog();
        Subscription subscription = ApiManager.getInstence().getZuiMeiService().getImage()
                .subscribeOn(Schedulers.io())
                *//*.map(new Func1<ZuiMeiImageResponse, Boolean>() {
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
                })*//*
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
                *//*.subscribe(new Observer<Boolean>() {
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
                });*//*
        addSubscription(subscription);*/
    }
}
