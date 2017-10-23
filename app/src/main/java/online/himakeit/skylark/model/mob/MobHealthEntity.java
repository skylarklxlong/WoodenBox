package online.himakeit.skylark.model.mob;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/19 19:57
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * {
 * "msg": "success",
 * "result": {
 * "list": [
 * {
 * "content": "　　很多人都知道板栗是补肾强骨之果，其实，它的好处还不仅限于此。板栗中所含的矿物质很全面，有钾、镁、铁、锌、锰等，虽然达不到榛子、瓜子那么高的含量，但仍然比苹果、梨等普通水果高得多。 　　   　　尤其是板栗中钾元素的含量很突出，每 100 　　克鲜板栗中含钾量为 442 毫克，比号称富含钾的苹果还高 3 倍。 　　因此，很适合高血压患者食用。此外，栗子中所含的不饱和脂肪酸和维生素也非常丰富，能防治高血压、冠心病、动脉硬化、骨质疏松等疾病，是抗衰老、延年益寿的滋补佳品。 　　食用时需要注意，板栗热量较高，因此吃多了容易引起便秘，每天最多吃 10 　　个就够了，用板栗炖汤、熬粥、煮饭都是不错的选择。",
 * "id": 12225,
 * "title": "血压高每天吃十个板栗"
 * }
 * ],
 * "page": 1,
 * "total": 1
 * },
 * "retCode": "200"
 * }
 */
public class MobHealthEntity {

    private int page;
    private int total;
    private List<MobHealthListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MobHealthListBean> getList() {
        return list;
    }

    public void setList(List<MobHealthListBean> list) {
        this.list = list;
    }

    public static class MobHealthListBean {
        private String content;
        private int id;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
