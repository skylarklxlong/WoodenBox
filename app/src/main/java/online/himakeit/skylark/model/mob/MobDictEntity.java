package online.himakeit.skylark.model.mob;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/19 19:30
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * {
 "msg": "success",
 "result": {
 "bihua": 7,
 "bihuaWithBushou": 3,
 "brief": "李；lǐ；落叶小乔木，果实称“李子”，熟时呈黄色或紫红色，可食",
 "bushou": "木",
 "detail": "李；lǐ；【名】；(形声。从木,子声。本义:李树)；同本义。落叶乔木,春天开白色花,果实叫李子,熟",
 "name": "李",
 "pinyin": "lǐ",
 "wubi": "sbf"
 },
 "retCode": "200"
 }
 */
public class MobDictEntity {

    private int bihua;
    private int bihuaWithBushou;
    private String brief;
    private String bushou;
    private String detail;
    private String name;
    private String pinyin;
    private String wubi;

    public int getBihua() {
        return bihua;
    }

    public void setBihua(int bihua) {
        this.bihua = bihua;
    }

    public int getBihuaWithBushou() {
        return bihuaWithBushou;
    }

    public void setBihuaWithBushou(int bihuaWithBushou) {
        this.bihuaWithBushou = bihuaWithBushou;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBushou() {
        return bushou;
    }

    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getWubi() {
        return wubi;
    }

    public void setWubi(String wubi) {
        this.wubi = wubi;
    }
}
