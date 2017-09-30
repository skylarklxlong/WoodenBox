package online.himakeit.skylark.api;


import online.himakeit.skylark.model.topnews.NewsList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/27 15:58
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface TopNewsApi {
    /**
     * 获取今日头条新闻 T1348649580692
     * @param id 这个值只能是20的整数倍 包括0 ，否则获取到的数据为空 T1348647909107
     * @return NewsList对象 Json数据经过Gson解析转换为NewsList对象，有NewsBean组成
     */
    @GET("http://c.m.163.com/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id);

    /**
     * 暂时未用上 因为使用这个方法时Gson解析一直失败
     * 报错误：java.lang.IllegalStateException: Expected a string but was BEGIN_OBJECT at line 1 column 2 path $
     * @param id 这里的id就是上一个连接获取到的数据中的docid
     * @return
     */
    @GET("http://c.m.163.com/nc/article/{id}/full.html")
    Observable<String> getNewsDetail(@Path("id") String id);
}
