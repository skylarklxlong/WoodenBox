package online.himakeit.skylark.api;

import java.util.ArrayList;

import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.model.gank.GankData;
import online.himakeit.skylark.model.gank.GankMeiZhiData;
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
import online.himakeit.skylark.model.neihan.NeiHanBaseEntity;
import online.himakeit.skylark.model.topnews.NewsList;
import online.himakeit.skylark.model.zhuhu.ZhiHuDaily;
import online.himakeit.skylark.model.zhuhu.ZhiHuStory;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
     *
     * @param id 这个值只能是20的整数倍 包括0 ，否则获取到的数据为空 T1348647909107
     * @return NewsList对象 Json数据经过Gson解析转换为NewsList对象，有NewsBean组成
     */
    @GET(Config.TOPNEWS_BASE_URL + "/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id);

    /**
     * 暂时未用上 因为使用这个方法时Gson解析一直失败
     * 报错误：java.lang.IllegalStateException: Expected a string but was BEGIN_OBJECT at line 1 column 2 path $
     *
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

    @GET(Config.ZUIMEI_BASE_URL + "/wallpaper/category/1/?page_size=1")
    Call<ZuiMeiImageResponse> getZuiMeiPic();
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
    @GET(Config.GANKIO_BASE_URL + "/api/data/福利/5/{page}")
    Observable<GankMeiZhiData> getMeiZhiData(@Path("page") int page);

    // TODO: 2017/9/6 type: 福利/Android/iOS/休息视频/拓展资源/前端
    @GET(Config.GANKIO_BASE_URL + "/api/data/{type}/10/{page}")
    Observable<GankData> getGankData(@Path("type") String type,
                                     @Path("page") int page);
    //--------------------------------Gank end-----------------------------

    //--------------------------------Fir.im start-----------------------------
    //获取fir.im中的Love的最新版本
    @Headers("Cache-Control: public, max-age=3600")
    @GET(Config.URL_AppUpdateInfo)
    Call<AppUpdateInfo> getTheLastAppInfo();
    //--------------------------------Fir.im end-----------------------------

    //--------------------------------NeiHan start-----------------------------

    /**
     * http://iu.snssdk.com/neihan/stream/mix/v1/
     * ?mpic=1
     * &webp=1  固定值 1
     * &essence=1   固定值 1
     * &content_type=-103   从获取 content_type 中获取得到的 list_id 字段值。目前推荐的是-101，视频的是-104，段友秀的是-301，图片的是-103，段子的是-102
     * &message_cursor=-1   固定值-1
     * &am_longitude=110    经度。可为空
     * &am_latitude=120     纬度。可为空
     * &am_city=%E5%8C%97%E4%BA%AC%E5%B8%82     城市名，例如：北京市。可为空
     * &am_loc_time=1463225362314   当前时间 Unix 时间戳，毫秒为单位
     * &count=30    返回数量
     * &min_time=1465232121     上次更新时间的 Unix 时间戳，秒为单位
     * &screen_width=1450       屏幕宽度，px为单位
     * &do00le_col_mode=0       固定值0
     * &iid=3216590132      一个长度为10的纯数字字符串，用于标识用户唯一性
     * &device_id=32613520945   设备 id，一个长度为11的纯数字字符串
     * &ac=wifi     网络环境，可取值 wifi
     * &channel=360     下载渠道，可360、tencent等
     * &aid=7       固定值7
     * &app_name=joke_essay     固定值joke_essay
     * &version_code=612        版本号去除小数点，例如612
     * &version_name=6.1.2      版本号，例如6.1.2
     * &device_platform=android     设备平台，android 或 ios
     * &ssmix=a     固定值 a
     * &device_type=sansung     设备型号，例如 hongmi
     * &device_brand=xiaomi     设备品牌，例如 xiaomi
     * &os_api=28       操作系统版本，例如20
     * &os_version=6.10.1       操作系统版本号，例如7.1.0
     * &uuid=326135942187625        用户 id，一个长度为15的纯数字字符串
     * &openudid=3dg6s95rhg2a3dg5   一个长度为16的数字和小写字母混合字符串
     * &manifest_version_code=612   版本号去除小数点，例如612
     * &resolution=1450*2800    屏幕宽高，例如 1920*1080
     * &dpi=620     手机 dpi
     * &update_version_code=6120    版本号去除小数点后乘10，例如6120
     */
    @GET(Config.NEIHAN_BASE_URL + "/?mpic=1&webp=1&essence=1&content_type=-{type}" +
            "&message_cursor=-1&am_longitude=116.39739&am_latitude=39.91037425263379&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
            "&am_loc_time=1463225362314&count={count}&min_time=1465232121&screen_width={pWidth}&do00le_col_mode=0" +
            "&iid={iid}&device_id={device_id}&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612" +
            "&version_name=6.1.2&device_platform=android&ssmix=a&device_type={device_type}&device_brand={device_brand}" +
            "&os_api={os_api}&os_version={os_version}&uuid={uuid}&openudid={openudid}&manifest_version_code=612" +
            "&resolution={resolution}&dpi={dpi}&update_version_code=6120")
    Call<NeiHanBaseEntity> getNeiHanData(@Path("type") int type, @Path("count") int count,
                                         @Path("pWidth") int pWidth, @Path("iid") String iid,
                                         @Path("device_id") String device_id, @Path("device_type") String device_type,
                                         @Path("device_brand") String device_brand, @Path("os_api") int os_api,
                                         @Path("os_version") String os_version, @Path("uuid") String uuid,
                                         @Path("openudid") String openudid, @Path("resolution") String resolution,
                                         @Path("dpi") int dpi);

    Call<NeiHanBaseEntity> getNeiHanData1(@Query("card") String card);

    //--------------------------------NeiHan end-----------------------------

    //--------------------------------Mob start-----------------------------

    //银行卡信息查询
    //http://apicloud.mob.com/appstore/bank/card/query?key=215df9177263d&card=6228480058489875078
    @GET(Config.MOB_BASE_URL + "/appstore/bank/card/query")
    Call<MobBaseEntity<MobBankCard>> queryMobBankCradInfo(
            @Query("key") String appkey, @Query("card") String card);

    //万年历查询
    //http://apicloud.mob.com/appstore/clendar/day?key=appkey&date=2015-05-01
    @Headers("Cache-Control: public, max-age=300")
    @GET(Config.MOB_BASE_URL + "/appstore/calendar/day")
    Call<MobBaseEntity<MobCalendarInfoEntity>> queryMobCalendarInfo(
            @Query("key") String appkey, @Query("date") String date);


    //城市列表查询接口
    //http://apiclod.mob.com/v1/weather/citys?key=appkey
    @Headers("Cache-Control: public, max-age=300")
    @GET(Config.MOB_BASE_URL + "/v1/weather/citys")
    Call<MobCitysEntity> queryMobCitys(@Query("key") String appkey);

    //查询汽车品牌
    //http://apicloud.mob.com/car/brand/query?key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/car/brand/query")
    Call<MobBaseEntity<ArrayList<MobCarEntity>>> queryMobCarList(
            @Query("key") String appkey);

    //车型信息查询
    //http://apicloud.mob.com/car/seriesname/query?name=%E5%A5%A5%E8%BF%AAQ5&key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/car/seriesname/query")
    Call<MobBaseEntity<ArrayList<MobCarItemEntity>>> queryMobCarItems(
            @Query("key") String appkey, @Query("name") String name);

    //车型详细信息查询
    //http://apicloud.mob.com/car/series/query?key=215df9177263d&cid=1060133
    @GET(Config.MOB_BASE_URL + "/car/series/query")
    Call<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> queryMobCarDetails(
            @Query("key") String appkey, @Query("cid") String cid);

    //菜谱分类标签查询
    //http://apicloud.mob.com/v1/cook/category/query?key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/v1/cook/category/query")
    Call<MobBaseEntity<MobCookCategoryEntity>> queryMobCookCategory(
            @Query("key") String appkey);

    //按标签查询菜谱接口
    //http://apicloud.mob.com/v1/cook/menu/search?key=appkey&cid=0010001007&page=1&size=20
    @GET(Config.MOB_BASE_URL + "/v1/cook/menu/search")
    Call<MobBaseEntity<MobCookDetailEntity>> queryMobCookDetailsList(
            @Query("key") String appkey, @Query("cid") String cid,
            @Query("page") int page, @Query("size") int size);

    //新华字典查询
    //http://apicloud.mob.com/appstore/dicionary/query?key=215df9177263d&name=李
    @GET(Config.MOB_BASE_URL + "/appstore/dicionary/query")
    Call<MobBaseEntity<MobDictEntity>> queryMobDict(
            @Query("key") String appkey, @Query("name") String name);

    //航线查询航班信息
    //http://apicloud.mob.com/flight/line/query?key=215df9177263d&start=上海&end=海口
    @GET(Config.MOB_BASE_URL + "/flight/line/query")
    Call<MobBaseEntity<ArrayList<MobFlightEntity>>> queryMobFlightLineList(
            @Query("key") String appkey, @Query("start") String start, @Query("end") String end
    );

    //健康知识
    //http://apicloud.mob.com/appstore/health/search?key=215df9177263d&name=板栗
    @GET(Config.MOB_BASE_URL + "/appstore/health/search")
    Call<MobBaseEntity<MobHealthEntity>> queryMobHealth(
            @Query("key") String appkey, @Query("name") String name,
            @Query("page") int page, @Query("size") int size);

    //历史上今天
    //http://apicloud.mob.com/appstore/history/query?day=0418&key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/appstore/history/query")
    Call<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> queryMobHistory(
            @Query("key") String appkey, @Query("day") String day);

    //身份证查询
    //http://apicloud.mob.com/idcrd/query?cardno=xxx&key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/idcrd/query")
    Call<MobBaseEntity<MobIdCardEntity>> queryMobIdcard(
            @Query("key") String appkey, @Query("cardno") String cardno);

    //成语大全
    //http://apicloud.mob.com/appstore/idiom/query?name=丢三落四&key=215df9177263d
    @GET(Config.MOB_BASE_URL + "/appstore/idiom/query")
    Call<MobBaseEntity<MobIdiomEntity>> queryMobIdiom(
            @Query("key") String appkey, @Query("name") String name);

    //IP查询
    //http://apicloud.mob.com/ip/query?key=215df9177263d&ip=123.123.123.123
    @GET(Config.MOB_BASE_URL + "/ip/query")
    Call<MobBaseEntity<MobIpEntity>> queryMobIp(
            @Query("key") String appkey, @Query("ip") String ip);

    //支持彩种列表
    //http://apicloud.mob.com/lottery/list?key=appkey
    @GET(Config.MOB_BASE_URL + "/lottery/list")
    Call<MobBaseEntity<ArrayList<String>>> queryMoblotteryList(@Query("key") String appkey);

    //彩票开奖结果查询
    //http://apicloud.mob.com/lottery/query?key=appkey&name=大乐透
    @GET(Config.MOB_BASE_URL + "/lottery/query")
    Call<MobBaseEntity<MobLotteryEntity>> queryMoblotteryDetail(
            @Query("key") String appkey, @Query("name") String name
    );

    //全国今日油价查询
    //http://apicloud.mob.com/oil/price/province/query?key=appkey
    @GET(Config.MOB_BASE_URL + "/oil/price/province/query")
    Call<MobBaseEntity<MobOilPriceEntity>> queryMobOilPrice(@Query("key") String appkey);

    //手机号码归属地查询
    //http://apicloud.mob.com/v1/mobile/address/query?phone=xxxx&key=1c9dccb9a2434
    @GET(Config.MOB_BASE_URL + "/v1/mobile/address/query")
    Call<MobBaseEntity<MobPhoneAddressEntity>> queryMobMobileAddress(
            @Query("key") String appkey, @Query("phone") String phone);

    //邮编查询
    //http://apicloud.mob.com/v1/postcode/query?code=102629&key=1c9dccb9a2434
    @GET(Config.MOB_BASE_URL + "/v1/postcode/query")
    Call<MobBaseEntity<MobPostCodeEntity>> queryMobPostCode(
            @Query("key") String appkey, @Query("code") String code);

    //火车站站查询
    //http://apicloud.mob.com/train/tickets/queryByStationToStation?key=123456&start=北京&end=上海
    @GET(Config.MOB_BASE_URL + "/train/tickets/queryByStationToStation")
    Call<MobBaseEntity<ArrayList<MobTrainEntity>>> queryMobTrainStation(
            @Query("key") String appkey, @Query("start") String start, @Query("end") String end);

    //火车车次查询
    //http://apicloud.mob.com/train/tickets/queryByTrainNo?key=123456&trainno=G2
    @GET(Config.MOB_BASE_URL + "/train/tickets/queryByTrainNo")
    Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> queryMobTrainNo(
            @Query("key") String appkey, @Query("trainno") String trainno);

    //获取天气信息
    //http://apicloud.mob.com/v1/weather/query?key=215df9177263d&city=武汉&province=湖北
    @Headers("Cache-Control: public, max-age=300")
    @GET(Config.MOB_BASE_URL + "/v1/weather/query")
    Call<MobWeatherEntity> queryMobCityWeather(@Query("key") String appkey,
                                               @Query("city") String city, @Query("province") String province);

    //微信精选列表查询
    //http://apicloud.mob.com/wx/article/search?key=123456&cid=1
    @GET(Config.MOB_BASE_URL + "/wx/article/search")
    Call<MobBaseEntity<MobWxArticleEntity>> queryMobWxArticle(
            @Query("key") String appkey, @Query("cid") String cid,
            @Query("page") int page, @Query("size") int size);

    //微信精选分类查询
    //http://apicloud.mob.com/wx/article/category/query?key=123456
    @GET(Config.MOB_BASE_URL + "/wx/article/category/query")
    Call<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> queryMobWxArticleCategory(
            @Query("key") String appkey);

    //--------------------------------Mob start-----------------------------

}
