package online.himakeit.skylark.api;

import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.neihan.NeiHanBaseEntity;
import online.himakeit.skylark.model.neihan.NeiHanRequestData;
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

    public static Call<NeiHanBaseEntity> getNeiHanData(NeiHanRequestData neiHanRequestData, final int what, final MobCallBack callBack) {
        Call<NeiHanBaseEntity> neiHanData = ApiManager.getInstence().getWebServiceApi().getNeiHanData(
                neiHanRequestData.getType(), neiHanRequestData.getCount(), neiHanRequestData.getpWidth(),
                neiHanRequestData.getIid(), neiHanRequestData.getDevice_id(), neiHanRequestData.getDevice_type(),
                neiHanRequestData.getDevice_brand(), neiHanRequestData.getOs_api(), neiHanRequestData.getOs_version(),
                neiHanRequestData.getUuid(), neiHanRequestData.getOpenudid(), neiHanRequestData.getResolution(),
                neiHanRequestData.getDpi());
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
