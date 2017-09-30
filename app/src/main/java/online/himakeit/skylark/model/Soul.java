package online.himakeit.skylark.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/27 15:14
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class Soul implements Serializable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    private long id;
}
