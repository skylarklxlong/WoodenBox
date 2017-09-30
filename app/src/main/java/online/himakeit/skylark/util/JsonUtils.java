package online.himakeit.skylark.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import online.himakeit.skylark.model.topnews.NewsDetailBean;


/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 14:32
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description: Json转换工具
 */
public class JsonUtils {

    public static NewsDetailBean readJsonNewsDetailBeans(String result, String docid){
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(docid);
            if (jsonElement == null){
                return null;
            }
            LogUtils.show("JsonUtils readJsonNewsDetailBeans 解析前 "+jsonElement.getAsJsonObject().toString());
            newsDetailBean = deserialize(jsonElement.getAsJsonObject(),NewsDetailBean.class);
            LogUtils.show("JsonUtils readJsonNewsDetailBeans 解析后 "+newsDetailBean.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return newsDetailBean;
    }


    private static Gson mGson = new Gson();

    /**
     * 将对象转换为Json字符串
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object){return mGson.toJson(object);}

    /**
     * 将Json字符串转换为对象
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException{
        return mGson.fromJson(json,clz);
    }

    /**
     * 将Json对象转换为实体对象
     * @param jsonObject
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(JsonObject jsonObject, Class<T> clz) throws JsonSyntaxException{
        return mGson.fromJson(jsonObject,clz);
    }

    /**
     * 将Json字符串转换为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException{
        return mGson.fromJson(json,type);
    }
}
