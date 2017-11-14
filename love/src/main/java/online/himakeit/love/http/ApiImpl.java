package online.himakeit.love.http;

import com.socks.library.KLog;

import online.himakeit.love.bean.AppUpdateInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/14 16:54
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ApiImpl {

    public final static String GET_DATA_FAIL = "获取数据失败";
    public final static String NET_FAIL = "网络出问题啦";

    public static Call<AppUpdateInfo> getAppUpdateInfo(final int what, final MyCallBack myCallBack) {

        Call<AppUpdateInfo> theLastAppInfoCall = BuildApi.getAPIService().getTheLastAppInfo();

        theLastAppInfoCall.enqueue(new Callback<AppUpdateInfo>() {
            @Override
            public void onResponse(Call<AppUpdateInfo> call, Response<AppUpdateInfo> response) {
                if (response.isSuccessful()) {
                    AppUpdateInfo body = response.body();
                    if (body != null) {
                        if (body.getName().equals("Love")) {
                            myCallBack.onSuccess(what, body);
                        } else {
                            myCallBack.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<AppUpdateInfo> call, Throwable t) {
                KLog.e("getRandomDatas-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return theLastAppInfoCall;
    }
}
