package online.himakeit.love.presenter.implPresenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import online.himakeit.love.Constants;
import online.himakeit.love.MyApplication;
import online.himakeit.love.bean.AppUpdateInfo;
import online.himakeit.love.http.ApiImpl;
import online.himakeit.love.http.MyCallBack;
import online.himakeit.love.presenter.IMainPresenter;
import online.himakeit.love.presenter.implView.IMainView;
import online.himakeit.love.utils.NetUtils;
import online.himakeit.love.utils.SharePreUtil;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 17:13
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MainPresenterImpl extends BasePresenterImpl<IMainView> implements IMainPresenter {

    private Context context;

    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccessList(int what, List results) {
        }

        @Override
        public void onSuccess(int what, Object result) {
            if (mView == null) {
                return;
            }
            switch (what) {
                case 0x001:
                    if (result == null) {
                        return;
                    }
                    //获取当前APP的版本号
                    int newVersion;
                    AppUpdateInfo appUpdateInfo;
                    try {
                        appUpdateInfo = (AppUpdateInfo) result;
                        newVersion = Integer.parseInt(appUpdateInfo.getBuild());
                    } catch (Exception e) {
                        newVersion = 1;
                        appUpdateInfo = new AppUpdateInfo();
                    }
                    if (MyApplication.getVersionCode() < newVersion) {
                        SharePreUtil.saveBooleanData(context, Constants.SPAppUpdate + MyApplication.getVersionCode(), true);
                        //需要版本更新
                        if (mView != null) {
                            mView.showAppUpdateDialog(appUpdateInfo);
                        }
                    }

                    break;
            }
        }

        @Override
        public void onFail(int what, String result) {
            if (!TextUtils.isEmpty(result)) {
                mView.showToast(result);
            }
        }
    };


    public MainPresenterImpl(Context context, IMainView iMainView) {
        this.context = context;
        attachView(iMainView);
    }
    @Override
    public void initAppUpdate() {
        if (NetUtils.hasNetWorkConection(context)) {
            //版本更新
            ApiImpl.getAppUpdateInfo(0x001, httpCallBack);
        }
    }

}