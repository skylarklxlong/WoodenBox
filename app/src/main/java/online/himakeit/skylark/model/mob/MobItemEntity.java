package online.himakeit.skylark.model.mob;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/23 19:28
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobItemEntity {

    private String title;
    private String desc;

    public MobItemEntity(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
