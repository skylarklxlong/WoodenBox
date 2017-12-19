package online.himakeit.skylark.model.neihan;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/19 19:59
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
@Table("neihan")
public class NeiHanDataBase implements Serializable {
    @Column("_id")
    private int id;
    @Column("data")
    private String data;//请求json数据
    @Column("page")
    private int page;//页数
    @Column("type")
    private String type;//类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
