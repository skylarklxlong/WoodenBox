package online.himakeit.skylark.model.mob;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/23 19:28
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 *
 */
public class MobLotteryEntity {

    /**
     * awardDateTime : 2016-03-0520:30
     * lotteryDetails : [{"awardNumber":7,"awardPrice":6817484,"awards":"一等奖"},{"awardNumber":1,"awardPrice":4090490,"awards":"一等奖","type":"追加"},{"awardNumber":107,"awardPrice":60565,"awards":"二等奖"},{"awardNumber":29,"awardPrice":36339,"awards":"二等奖","type":"追加"},{"awardNumber":1381,"awardPrice":1793,"awards":"三等奖"},{"awardNumber":421,"awardPrice":1075,"awards":"三等奖","type":"追加"},{"awardNumber":52055,"awardPrice":200,"awards":"四等奖"},{"awardNumber":16237,"awardPrice":100,"awards":"四等奖","type":"追加"},{"awardNumber":740094,"awardPrice":10,"awards":"五等奖"},{"awardNumber":222539,"awardPrice":5,"awards":"五等奖","type":"追加"},{"awardNumber":6138276,"awardPrice":5,"awards":"六等奖"}]
     * lotteryNumber : ["03","06","13","23","24","07","10"]
     * name : 大乐透
     * period : 16025
     * pool : 2.41448842022E9
     * sales : 189993749
     */

    private String awardDateTime;
    private String name;
    private String period;
    private double pool;
    private int sales;
    private List<MobLotteryDetailsBean> lotteryDetails;
    private List<String> lotteryNumber;

    public String getAwardDateTime() {
        return awardDateTime;
    }

    public void setAwardDateTime(String awardDateTime) {
        this.awardDateTime = awardDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getPool() {
        return pool;
    }

    public void setPool(double pool) {
        this.pool = pool;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public List<MobLotteryDetailsBean> getLotteryDetails() {
        return lotteryDetails;
    }

    public void setLotteryDetails(List<MobLotteryDetailsBean> lotteryDetails) {
        this.lotteryDetails = lotteryDetails;
    }

    public List<String> getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(List<String> lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    public static class MobLotteryDetailsBean{
        /**
         * awardNumber : 7
         * awardPrice : 6817484
         * awards : 一等奖
         * type : 追加
         */

        private int awardNumber;
        private int awardPrice;
        private String awards;
        private String type;

        public int getAwardNumber() {
            return awardNumber;
        }

        public void setAwardNumber(int awardNumber) {
            this.awardNumber = awardNumber;
        }

        public int getAwardPrice() {
            return awardPrice;
        }

        public void setAwardPrice(int awardPrice) {
            this.awardPrice = awardPrice;
        }

        public String getAwards() {
            return awards;
        }

        public void setAwards(String awards) {
            this.awards = awards;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
