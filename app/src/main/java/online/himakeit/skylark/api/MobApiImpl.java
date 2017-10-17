package online.himakeit.skylark.api;

import java.util.ArrayList;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.mob.MobBankCard;
import online.himakeit.skylark.model.mob.MobBaseEntity;
import online.himakeit.skylark.model.mob.MobCarDetailsEntity;
import online.himakeit.skylark.model.mob.MobCarEntity;
import online.himakeit.skylark.model.mob.MobCarItemEntity;
import online.himakeit.skylark.util.LogUtils;
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
public class MobApiImpl {
    private static final String TAG = "MobApiImpl";

    public static Subscription queryBankCradInfo(String card){
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().queryMobBankCradInfo(Config.MOB_APP_KEY,card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobBaseEntity<MobBankCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"queryBankCradInfo onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG,"queryBankCradInfo onError " + e.toString());
                    }

                    @Override
                    public void onNext(MobBaseEntity<MobBankCard> mobBankCardMobBaseEntity) {
                        LogUtils.e(TAG,"queryBankCradInfo onNext " + mobBankCardMobBaseEntity.getResult().getBank());
                    }

                });
        return subscription;
    }

    public static Subscription queryCarList(){
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().queryMobCarList(Config.MOB_APP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobBaseEntity<ArrayList<MobCarEntity>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"queryMobCarList onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG,"queryMobCarList onError " + e.toString());
                    }

                    @Override
                    public void onNext(MobBaseEntity<ArrayList<MobCarEntity>> arrayListMobBaseEntity) {
                        LogUtils.e(TAG,"queryMobCarList onNext " + arrayListMobBaseEntity.getResult().get(0).getName());
                    }
                });

        return subscription;
    }

    public static Subscription queryCarItems(String name){
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().queryMobCarItems(Config.MOB_APP_KEY,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobBaseEntity<ArrayList<MobCarItemEntity>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"queryMobCarItems onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG,"queryMobCarItems onError " + e.toString());
                    }

                    @Override
                    public void onNext(MobBaseEntity<ArrayList<MobCarItemEntity>> arrayListMobBaseEntity) {
                        LogUtils.e(TAG,"queryMobCarItems onNext " + arrayListMobBaseEntity.getResult().get(0).getBrandName());
                    }
                });

        return subscription;
    }

    public static Subscription queryCarDetails(String cid){
        Subscription subscription = ApiManager.getInstence().getWebServiceApi().queryMobCarDetails(Config.MOB_APP_KEY,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MobBaseEntity<ArrayList<MobCarDetailsEntity>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"queryCarDetails onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG,"queryCarDetails onError " + e.toString());
                    }

                    @Override
                    public void onNext(MobBaseEntity<ArrayList<MobCarDetailsEntity>> arrayListMobBaseEntity) {
                        LogUtils.e(TAG,"queryCarDetails onCompleted " + arrayListMobBaseEntity.getResult().get(0).getCarImage());
                    }
                });

        return subscription;
    }
}
