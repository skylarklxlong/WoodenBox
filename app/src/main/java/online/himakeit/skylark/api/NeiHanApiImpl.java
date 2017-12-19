package online.himakeit.skylark.api;

import android.content.Context;
import android.util.Log;

import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.neihan.NeiHanBaseEntity;
import online.himakeit.skylark.util.DensityUtils;
import online.himakeit.skylark.util.DeviceUtils;
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

    public Call<NeiHanBaseEntity> getNeiHanData(Context context, String type, int count, final int what, final MobCallBack callBack) {
        Call<NeiHanBaseEntity> neiHanData = ApiManager.getInstence().getWebServiceApi().getNeiHanData(
                1, 1, 1, type, "-1", "39.91037425263379",
                "116.39739", "%E5%8C%97%E4%BA%AC", System.currentTimeMillis(), count,
                "1465232121", DensityUtils.getDeviceInfo(context)[0],
                0, "3216590132", "32613520945", "wifi", "360", 7,
                "joke_essay", "612", "6.1.2", "android", "a",
                DeviceUtils.getModel(), DeviceUtils.getBrand(), DeviceUtils.getOSApi(), DeviceUtils.getOSVersion(),
                "326135942187625", "3dg6s95rhg2a3dg5", "612",
                (DensityUtils.getDeviceInfo(context)[0] + "*" + DensityUtils.getDeviceInfo(context)[1]),
                DensityUtils.getDeviceInfo(context)[2], "6120");
        neiHanData.enqueue(new Callback<NeiHanBaseEntity>() {
            @Override
            public void onResponse(Call<NeiHanBaseEntity> call, Response<NeiHanBaseEntity> response) {
                if (response.isSuccessful()) {
                    NeiHanBaseEntity body = response.body();
                    if (body != null) {
                        callBack.onSuccess(what, body.getData());
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<NeiHanBaseEntity> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
                Log.e(TAG, "onFailure: " + t);
            }
        });

        return neiHanData;
    }
}
