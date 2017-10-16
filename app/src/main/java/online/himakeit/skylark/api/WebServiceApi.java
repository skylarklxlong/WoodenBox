package online.himakeit.skylark.api;

import java.util.ArrayList;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.gank.GankData;
import online.himakeit.skylark.model.gank.GankMeiZhiData;
import online.himakeit.skylark.model.mob.MobBankCard;
import online.himakeit.skylark.model.mob.MobBaseEntity;
import online.himakeit.skylark.model.mob.MobCarDetailsEntity;
import online.himakeit.skylark.model.mob.MobCarEntity;
import online.himakeit.skylark.model.mob.MobCarItemEntity;
import online.himakeit.skylark.model.topnews.NewsList;
import online.himakeit.skylark.model.zhuhu.ZhiHuDaily;
import online.himakeit.skylark.model.zhuhu.ZhiHuStory;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/14 17:18
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description: 所有网络Api接口
 */
public interface WebServiceApi {

    //--------------------------------TopNews start-----------------------------
    /**
     * 获取今日头条新闻 T1348649580692
     * @param id 这个值只能是20的整数倍 包括0 ，否则获取到的数据为空 T1348647909107
     * @return NewsList对象 Json数据经过Gson解析转换为NewsList对象，有NewsBean组成
     */
    @GET(Config.TOPNEWS_BASE_URL + "/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id);

    /**
     * 暂时未用上 因为使用这个方法时Gson解析一直失败
     * 报错误：java.lang.IllegalStateException: Expected a string but was BEGIN_OBJECT at line 1 column 2 path $
     * @param id 这里的id就是上一个连接获取到的数据中的docid
     * @return
     */
    @GET(Config.TOPNEWS_BASE_URL + "/nc/article/{id}/full.html")
    Observable<String> getNewsDetail(@Path("id") String id);

    //-------------------------------TopNews end-----------------------------

    //--------------------------------ZuiMei start-----------------------------
    // TODO: 2017/8/22 从最美官网抓取壁纸用的
    @GET(Config.ZUIMEI_BASE_URL + "/wallpaper/category/1/?page_size=1")
    Observable<ZuiMeiImageResponse> getImage();
    //--------------------------------ZuiMei end-----------------------------

    //--------------------------------ZhiHu start-----------------------------
    // TODO: 2017/8/22 http://news-at.zhihu.com/api/4/news/latest 用于获取最新的
    @GET(Config.ZHIHU_BASE_URL + "/api/4/news/latest")
    Observable<ZhiHuDaily> getLastDaily();

    @GET(Config.ZHIHU_BASE_URL + "/api/4/news/before/{date}")
    Observable<ZhiHuDaily> getTheDaily(@Path("date") String date);

    @GET(Config.ZHIHU_BASE_URL + "/api/4/news/{id}")
    Observable<ZhiHuStory> getZhiHuStory(@Path("id") String id);

    //--------------------------------ZhiHu end-----------------------------

    //--------------------------------Gank start-----------------------------
    @GET(Config.GANKIO_BASE_URL + "/api/data/福利/10/{page}")
    Observable<GankMeiZhiData> getMeiZhiData(@Path("page") int page);

    // TODO: 2017/9/6 type: 福利/Android/iOS/休息视频/拓展资源/前端
    @GET(Config.GANKIO_BASE_URL + "/api/data/{type}/10/{page}")
    Observable<GankData> getGankData(@Path("type") String type,
                                     @Path("page") int page);
    //--------------------------------Gank end-----------------------------

    //--------------------------------Mob start-----------------------------

    //银行卡信息查询
    //http://apicloud.mob.com/appstore/bank/card/query?key=215df9177263d&card=6228480058489875078
    @GET(Config.MOB_BASE_URL+"/appstore/bank/card/query")
    Observable<MobBaseEntity<MobBankCard>> queryMobBankCradInfo(
            @Query("key") String appkey,@Query("card") String card);

    //查询汽车品牌
    //http://apicloud.mob.com/car/brand/query?key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/car/brand/query")
    Observable<MobBaseEntity<ArrayList<MobCarEntity>>> queryMobCarList(
            @Query("key") String appkey);

    //车型信息查询
    //http://apicloud.mob.com/car/seriesname/query?name=%E5%A5%A5%E8%BF%AAQ5&key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/car/seriesname/query")
    Observable<MobBaseEntity<ArrayList<MobCarItemEntity>>> queryMobCarItems(
            @Query("key") String appkey,@Query("name") String name);

    //车型详细信息查询
    //http://apicloud.mob.com/car/series/query?key=215df9177263d&cid=1060133
    @GET(Config.MOB_BASE_URL + "/car/series/query")
    Observable<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> queryMobCarDetails(
            @Query("key") String appkey,@Query("cid") String cid);




    //--------------------------------Mob start-----------------------------

}
