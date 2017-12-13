package online.himakeit.skylark.api;

import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.neihan.NeiHanBaseEntity;
import online.himakeit.skylark.util.DensityUtils;
import online.himakeit.skylark.util.DeviceUtils;
import online.himakeit.skylark.util.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/12 20:10
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanApiImpl {
    private static final String TAG = "NeiHanApiImpl";
    public final static String GET_DATA_FAIL = "获取数据失败";
    public final static String NET_FAIL = "网络出问题了";

    public static Call<NeiHanBaseEntity> getNeiHanData(int type, int count, final int what, final MobCallBack callBack) {
        Call<NeiHanBaseEntity> neiHanData = ApiManager.getInstence().getWebServiceApi().getNeiHanData(
                type, count, DensityUtils.getDeviceInfo(AppContext.getAppContext())[0],
                "3216590132", "32613520945", DeviceUtils.getModel(),
                DeviceUtils.getBrand(), DeviceUtils.getOSApi(), DeviceUtils.getOSVersion(),
                "326135942187625", "3dg6s95rhg2a3dg5",
                (DensityUtils.getDeviceInfo(AppContext.getAppContext())[0] +
                        "*" + DensityUtils.getDeviceInfo(AppContext.getAppContext())[0]),
                DensityUtils.getDeviceInfo(AppContext.getAppContext())[2]);
        neiHanData.enqueue(new Callback<NeiHanBaseEntity>() {
            @Override
            public void onResponse(Call<NeiHanBaseEntity> call, Response<NeiHanBaseEntity> response) {
                if (response.isSuccessful()) {
                    LogUtils.show("response.isSuccessful()");
                    NeiHanBaseEntity body = response.body();
                    if (body != null) {
                        LogUtils.show("body != null");
//                        callBack.onSuccessList(what,body.getData().getData());
                        callBack.onSuccess(what, body.getData());
                    } else {
                        LogUtils.show("body == null");
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    LogUtils.show("!response.isSuccessful()");
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<NeiHanBaseEntity> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
                LogUtils.show("onFailure" + t.getMessage());
            }
        });

        return neiHanData;
    }
}
