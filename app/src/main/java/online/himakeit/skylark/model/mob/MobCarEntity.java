package online.himakeit.skylark.model.mob;

import java.io.Serializable;
import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/16 20:19
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * name : AC Schnitzer
 * son : [{"car":"AC Schnitzer","type":"AC Schnitzer X5"}]
 */
public class MobCarEntity {

    private String name;
    private List<SonBean> son;

    private String pinyin;
    private boolean showSon = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SonBean> getSon() {
        return son;
    }

    public void setSon(List<SonBean> son) {
        this.son = son;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean isShowSon() {
        return showSon;
    }

    public void setShowSon(boolean showSon) {
        this.showSon = showSon;
    }

    public static class SonBean implements Serializable {
        /**
         * car : AC Schnitzer
         * type : AC Schnitzer X5
         */

        private String car;
        private String type;

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
