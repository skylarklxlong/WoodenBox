package online.himakeit.skylark.model.mob;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/23 19:20
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * {
 "msg": "success",
 "result": {
 "name": "丢三落四",
 "pinyin": "diū sān là sì",
 "pretation": "形容做事马虎粗心，不是丢了这个，就是忘了那个。",
 "sample": "无",
 "source": "清·曹雪芹《红楼梦》第六十七回咱们家没人，俗语说的‘夯雀儿先飞’，省的临时丢三落四的不齐全，令人笑话。”"
 },
 "retCode": "200"
 }
 */
public class MobIdiomEntity {
    private String name;
    private String pinyin;
    private String pretation;
    private String sample;
    private String source;
    private String sampleFrom;

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

    public String getPretation() {
        return pretation;
    }

    public void setPretation(String pretation) {
        this.pretation = pretation;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSampleFrom() {
        return sampleFrom;
    }

    public void setSampleFrom(String sampleFrom) {
        this.sampleFrom = sampleFrom;
    }
}
