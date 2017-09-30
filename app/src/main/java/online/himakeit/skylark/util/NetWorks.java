package online.himakeit.skylark.util;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 11:06
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NetWorks extends RetrofitUtils {

    protected static final NetService service = getRetrofit().create(NetService.class);


    public static void getData(int id,Observer<Object> observer){
        setSubscribe(service.getData(id),observer);
    }

    public static void getDetailData(String docid, Observer<Object> observer){
        setSubscribe(service.getDetatilData(docid),observer);
    }

    // TODO: 2017/7/27 rxjava的观察者模式
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer){
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private interface NetService{
        @GET("http://c.m.163.com/nc/article/headline/T1348647909107/{id}-20.html")
        Observable<Object> getData(@Path("id") int id);

        @GET("http://c.m.163.com/nc/article/{id}/full.html")
        Observable<Object> getDetatilData(@Path("id") String id);
    }
}
