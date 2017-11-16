package online.himakeit.skylark.model;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 16:39
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class Config {

    //获取APKinfo的地址：fir.im
    public static final String URL_AppUpdateInfo = "http://api.fir.im/apps/latest/5a0d0618ca87a8718400007a?api_token=d242396c7ad3470a207b6c120a2ab0b5&type=android";
    public static final String SPAppUpdate = "update";

    public static final String TOPNEWS = "topnews";
    public static final String ZHIHU = "zhihu";
    public static final String GANK = "gank";
    public static final String ZUIMEI = "zuimei";
    public static final String TOPNEWS_BASE_URL = "http://c.m.163.com";
    public static final String TOPNEWS_DETAIL_BASE_URL = TOPNEWS_BASE_URL + "/nc/article/";
    public static final String TOPNEWS_DETAIL_END_URL = "/full.html";

    public static final String ZHIHU_BASE_URL = "http://news-at.zhihu.com";

    public static final String GANKIO_BASE_URL = "http://gank.io";

    public static final String ZUIMEI_BASE_URL = "http://lab.zuimeia.com";
    public static final String ZUIMEI_PIC_BASE_URL = "http://wpstatic.zuimeia.com/";

    //Mob官网API
    public static final String MOB_BASE_URL = "http://apicloud.mob.com";
    //Mob ApiKey
    public static final String MOB_APP_KEY = "215df9177263d";

}
