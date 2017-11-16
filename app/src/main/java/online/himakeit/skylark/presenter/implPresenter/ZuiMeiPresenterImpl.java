package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.api.ZuiMeiApiImpl;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.presenter.IZuiMeiPresenter;
import online.himakeit.skylark.presenter.implView.IZuiMeiPic;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.SharePreUtil;

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

    private MobCallBack httpCallBack = new MobCallBack() {
        @Override
        public void onSuccessList(int what, List results) {
        }

        @Override
        public void onSuccess(int what, Object result) {
            if (iZuiMeiPic == null) {
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
                    if (AppContext.getVersionCode() < newVersion) {
                        SharePreUtil.saveBooleanData(context, Config.SPAppUpdate + AppContext.getVersionCode(), true);
                        //需要版本更新
                        if (iZuiMeiPic != null) {
                            iZuiMeiPic.showAppUpdateDialog(appUpdateInfo);
                        }
                    }

                    break;
            }
        }

        @Override
        public void onFail(int what, String result) {
            if (!TextUtils.isEmpty(result)) {
                iZuiMeiPic.showToast(result);
            }
        }
    };

    @Override
    public void getBackground() {
        addSubscription(ZuiMeiApiImpl.getBackgroundPic(iZuiMeiPic));
    }

    @Override
    public void initAppUpdate() {
        if (NetUtils.hasNetWorkConection(context)) {
            //版本更新
            MobApiImpl.getAppUpdateInfo(0x001, httpCallBack);
        }
    }
}
