package online.himakeit.skylark.model.mob;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/23 19:06
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * {
 * "msg": "success",
 * "result": [
 * {
 * "date": "18850418",
 * "day": 18,
 * "event": "　　１８８５年４月１８日，中日签订《天津会议专条》。１８８４年１２月４日，朝鲜“开化党”人金玉均按照日驻朝公使竹添进一密订的计划，引日军攻入王宫，挟持国王，组织一个由“开化党”人担任要职的亲日政权。史称“甲申事变”。事变后，清兵应朝鲜之请，击败了日军和“开化党”，拦回被挟持的朝鲜国王。由于当时日本的军事力量尚非清军之敌，内阁便决定暂时维持和局，先搞战备，再图大举。故于１８８５年２月，日本派伊藤博文为全权大使，陆军中将西乡从道为副使，出使中国，乘机要挟清政府谈判。清政府代表李鸿章采取希图苟安的妥协方针，4月18日在天津与伊藤博文签订《会议专条》（又称《天津条约》或《朝鲜撤兵条约》）。共三款，另附李致伊文书一件。内容：中日同时从朝鲜撤兵；将来朝鲜国若有变乱重大事件，中、日两国或一国要派兵，应先互行文知照。这样，日本获得随时可以向朝鲜派兵的特权。后来日本利用此约发动了中日甲午战争。
 * ",
 * "id": "569881b1590146d407331eaa",
 * "month": 4,
 * "title": "中日签订《天津条约》"
 * },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ },{ }
 * ],
 * "retCode": "200"
 * }
 */
public class MobHistoryTodayEntity {

    private String date;
    private int day;
    private String event;
    private String id;
    private int month;
    private String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
