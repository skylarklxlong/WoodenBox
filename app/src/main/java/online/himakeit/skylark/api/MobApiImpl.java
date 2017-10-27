package online.himakeit.skylark.api;

import java.util.ArrayList;

import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.mob.MobBankCard;
import online.himakeit.skylark.model.mob.MobBaseEntity;
import online.himakeit.skylark.model.mob.MobCalendarInfoEntity;
import online.himakeit.skylark.model.mob.MobCarDetailsEntity;
import online.himakeit.skylark.model.mob.MobCarEntity;
import online.himakeit.skylark.model.mob.MobCarItemEntity;
import online.himakeit.skylark.model.mob.MobCitysEntity;
import online.himakeit.skylark.model.mob.MobCookCategoryEntity;
import online.himakeit.skylark.model.mob.MobCookDetailEntity;
import online.himakeit.skylark.model.mob.MobDictEntity;
import online.himakeit.skylark.model.mob.MobFlightEntity;
import online.himakeit.skylark.model.mob.MobHealthEntity;
import online.himakeit.skylark.model.mob.MobHistoryTodayEntity;
import online.himakeit.skylark.model.mob.MobIdCardEntity;
import online.himakeit.skylark.model.mob.MobIdiomEntity;
import online.himakeit.skylark.model.mob.MobIpEntity;
import online.himakeit.skylark.model.mob.MobLotteryEntity;
import online.himakeit.skylark.model.mob.MobOilPriceEntity;
import online.himakeit.skylark.model.mob.MobPhoneAddressEntity;
import online.himakeit.skylark.model.mob.MobPostCodeEntity;
import online.himakeit.skylark.model.mob.MobTrainEntity;
import online.himakeit.skylark.model.mob.MobTrainNoEntity;
import online.himakeit.skylark.model.mob.MobWeatherEntity;
import online.himakeit.skylark.model.mob.MobWxArticleEntity;
import online.himakeit.skylark.model.mob.MobWxCategoryEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/14 17:20
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobApiImpl {
    private static final String TAG = "MobApiImpl";
    public final static String GET_DATA_FAIL = "获取数据失败";
    public final static String NET_FAIL = "网络出问题了";

    public static Call<MobBaseEntity<MobBankCard>> queryMobBankCradInfo(String card, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobBankCard>> call = ApiManager.getInstence().
                getWebServiceApi().queryMobBankCradInfo(Config.MOB_APP_KEY, card);

        call.enqueue(new Callback<MobBaseEntity<MobBankCard>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobBankCard>> call, Response<MobBaseEntity<MobBankCard>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobBankCard> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobBankCard>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobCalendarInfoEntity>> queryMobCalendarInfo(String date, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobCalendarInfoEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobCalendarInfo(Config.MOB_APP_KEY, date);

        call.enqueue(new Callback<MobBaseEntity<MobCalendarInfoEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobCalendarInfoEntity>> call, Response<MobBaseEntity<MobCalendarInfoEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobCalendarInfoEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobCalendarInfoEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobCitysEntity> queryMobCitys(final int what, final MobCallBack callBack) {
        Call<MobCitysEntity> call = ApiManager.getInstence().getWebServiceApi().queryMobCitys(Config.MOB_APP_KEY);

        call.enqueue(new Callback<MobCitysEntity>() {
            @Override
            public void onResponse(Call<MobCitysEntity> call, Response<MobCitysEntity> response) {
                if (response.isSuccessful()) {
                    MobCitysEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobCitysEntity> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobCarEntity>>> queryMobCarList(final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobCarEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobCarList(Config.MOB_APP_KEY);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobCarEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobCarEntity>>> call, Response<MobBaseEntity<ArrayList<MobCarEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobCarEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobCarEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobCarItemEntity>>> queryMobCarItems(String name, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobCarItemEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobCarItems(Config.MOB_APP_KEY, name);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobCarItemEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobCarItemEntity>>> call, Response<MobBaseEntity<ArrayList<MobCarItemEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobCarItemEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobCarItemEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> queryMobCarDetails(String cid, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobCarDetails(Config.MOB_APP_KEY, cid);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobCarDetailsEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> call, Response<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobCarDetailsEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobCookCategoryEntity>> queryMobCookCategory(final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobCookCategoryEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobCookCategory(Config.MOB_APP_KEY);

        call.enqueue(new Callback<MobBaseEntity<MobCookCategoryEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobCookCategoryEntity>> call, Response<MobBaseEntity<MobCookCategoryEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobCookCategoryEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobCookCategoryEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobCookDetailEntity>> queryMobCookDetailsList(String cid, int pageIndex, int pageSize, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobCookDetailEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobCookDetailsList(Config.MOB_APP_KEY, cid, pageIndex, pageSize);

        call.enqueue(new Callback<MobBaseEntity<MobCookDetailEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobCookDetailEntity>> call, Response<MobBaseEntity<MobCookDetailEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobCookDetailEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobCookDetailEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobDictEntity>> queryMobDict(String name, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobDictEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobDict(Config.MOB_APP_KEY, name);

        call.enqueue(new Callback<MobBaseEntity<MobDictEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobDictEntity>> call, Response<MobBaseEntity<MobDictEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobDictEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobDictEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobFlightEntity>>> queryMobFlightLineList(String start, String end, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobFlightEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobFlightLineList(Config.MOB_APP_KEY, start, end);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobFlightEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobFlightEntity>>> call, Response<MobBaseEntity<ArrayList<MobFlightEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobFlightEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobFlightEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobHealthEntity>> queryMobHealth(String name, int page, int size, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobHealthEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobHealth(Config.MOB_APP_KEY, name, page, size);

        call.enqueue(new Callback<MobBaseEntity<MobHealthEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobHealthEntity>> call, Response<MobBaseEntity<MobHealthEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobHealthEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobHealthEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> queryMobHistory(String day, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobHistory(Config.MOB_APP_KEY, day);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> call, Response<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobHistoryTodayEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobIdCardEntity>> queryMobIdcard(String cardno, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobIdCardEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobIdcard(Config.MOB_APP_KEY, cardno);

        call.enqueue(new Callback<MobBaseEntity<MobIdCardEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobIdCardEntity>> call, Response<MobBaseEntity<MobIdCardEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobIdCardEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobIdCardEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobIdiomEntity>> queryMobIdiom(String name, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobIdiomEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobIdiom(Config.MOB_APP_KEY, name);

        call.enqueue(new Callback<MobBaseEntity<MobIdiomEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobIdiomEntity>> call, Response<MobBaseEntity<MobIdiomEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobIdiomEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobIdiomEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobIpEntity>> queryMobIp(String ip, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobIpEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobIp(Config.MOB_APP_KEY, ip);

        call.enqueue(new Callback<MobBaseEntity<MobIpEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobIpEntity>> call, Response<MobBaseEntity<MobIpEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobIpEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobIpEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<String>>> queryMoblotteryList(final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<String>>> call = ApiManager.getInstence().getWebServiceApi().queryMoblotteryList(Config.MOB_APP_KEY);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<String>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<String>>> call, Response<MobBaseEntity<ArrayList<String>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<String>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<String>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobLotteryEntity>> queryMoblotteryDetail(String name, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobLotteryEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMoblotteryDetail(Config.MOB_APP_KEY, name);

        call.enqueue(new Callback<MobBaseEntity<MobLotteryEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobLotteryEntity>> call, Response<MobBaseEntity<MobLotteryEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobLotteryEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobLotteryEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobOilPriceEntity>> queryMobOilPrice(final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobOilPriceEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobOilPrice(Config.MOB_APP_KEY);

        call.enqueue(new Callback<MobBaseEntity<MobOilPriceEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobOilPriceEntity>> call, Response<MobBaseEntity<MobOilPriceEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobOilPriceEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobOilPriceEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobPhoneAddressEntity>> queryMobMobileAddress(String name, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobPhoneAddressEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobMobileAddress(Config.MOB_APP_KEY, name);

        call.enqueue(new Callback<MobBaseEntity<MobPhoneAddressEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobPhoneAddressEntity>> call, Response<MobBaseEntity<MobPhoneAddressEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobPhoneAddressEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobPhoneAddressEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobPostCodeEntity>> queryMobPostCode(String code, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobPostCodeEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobPostCode(Config.MOB_APP_KEY, code);

        call.enqueue(new Callback<MobBaseEntity<MobPostCodeEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobPostCodeEntity>> call, Response<MobBaseEntity<MobPostCodeEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobPostCodeEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobPostCodeEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobTrainEntity>>> queryMobTrainStation(String start, String end, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobTrainEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobTrainStation(Config.MOB_APP_KEY, start, end);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobTrainEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobTrainEntity>>> call, Response<MobBaseEntity<ArrayList<MobTrainEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobTrainEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobTrainEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> queryMobTrainNo(String trainno, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobTrainNo(Config.MOB_APP_KEY, trainno);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobTrainNoEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> call, Response<MobBaseEntity<ArrayList<MobTrainNoEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobTrainNoEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobWeatherEntity> queryMobCityWeather(String city, String province, final int what, final MobCallBack callBack) {
        Call<MobWeatherEntity> call = ApiManager.getInstence().getWebServiceApi().queryMobCityWeather(Config.MOB_APP_KEY, city, province);

        call.enqueue(new Callback<MobWeatherEntity>() {
            @Override
            public void onResponse(Call<MobWeatherEntity> call, Response<MobWeatherEntity> response) {
                if (response.isSuccessful()) {
                    MobWeatherEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobWeatherEntity> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<MobWxArticleEntity>> queryMobWxArticle(String cid, int page, int size, final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<MobWxArticleEntity>> call = ApiManager.getInstence().getWebServiceApi().queryMobWxArticle(Config.MOB_APP_KEY, cid, page, size);

        call.enqueue(new Callback<MobBaseEntity<MobWxArticleEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobWxArticleEntity>> call, Response<MobBaseEntity<MobWxArticleEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobWxArticleEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccess(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobWxArticleEntity>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

    public static Call<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> queryMobWxArticleCategory(final int what, final MobCallBack callBack) {
        Call<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> call = ApiManager.getInstence().getWebServiceApi().queryMobWxArticleCategory(Config.MOB_APP_KEY);

        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobWxCategoryEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> call, Response<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobWxCategoryEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            callBack.onSuccessList(what, body.getResult());
                        } else {
                            callBack.onFail(what, body.getMsg());
                        }
                    } else {
                        callBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    callBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> call, Throwable t) {
                callBack.onFail(what, NET_FAIL);
            }
        });

        return call;
    }

}
