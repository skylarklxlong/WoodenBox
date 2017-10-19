package online.himakeit.skylark.model.mob;

import java.io.Serializable;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/19 19:35
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * {
 "airLines": "中国东方航空公司",
 "flightNo": "MU4011",
 "flightRate": "97%",
 "flightTime": "2h28m",
 "from": "浦东国际机场",
 "fromAirportCode": "PVG",
 "fromCityCode": "SHA",
 "fromCityName": "上海",
 "fromTerminal": "T2",
 "planArriveTime": "10:20",
 "planTime": "07:15",
 "to": "美兰国际机场",
 "toAirportCode": "HAK",
 "toCityCode": "HAK",
 "toCityName": "海口",
 "toTerminal": "",
 "week": "一,二,三,四,五,六,日"
 }
 */
public class MobFlightEntity implements Serializable {

    private String airLines;            //航空公司
    private String flightNo;            //航班号
    private String flightRate;          //航班准点率
    private String flightTime;          //航行时间
    private String from;                //出发机场
    private String fromAirportCode;     //出发机场代码
    private String fromCityCode;        //出发城市代码
    private String fromCityName;        //出发城市名称
    private String fromTerminal;        //出发航站楼
    private String planArriveTime;      //计划起飞时间
    private String planTime;            //计划到达时间
    private String to;                  //到达机场
    private String toAirportCode;       //到达机场代码
    private String toCityCode;          //到达城市代码
    private String toCityName;          //到达城市名称
    private String toTerminal;          //到达城市航站楼
    private String week;                //航班周期

    public String getAirLines() {
        return airLines;
    }

    public void setAirLines(String airLines) {
        this.airLines = airLines;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightRate() {
        return flightRate;
    }

    public void setFlightRate(String flightRate) {
        this.flightRate = flightRate;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromAirportCode() {
        return fromAirportCode;
    }

    public void setFromAirportCode(String fromAirportCode) {
        this.fromAirportCode = fromAirportCode;
    }

    public String getFromCityCode() {
        return fromCityCode;
    }

    public void setFromCityCode(String fromCityCode) {
        this.fromCityCode = fromCityCode;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public String getFromTerminal() {
        return fromTerminal;
    }

    public void setFromTerminal(String fromTerminal) {
        this.fromTerminal = fromTerminal;
    }

    public String getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(String planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToAirportCode() {
        return toAirportCode;
    }

    public void setToAirportCode(String toAirportCode) {
        this.toAirportCode = toAirportCode;
    }

    public String getToCityCode() {
        return toCityCode;
    }

    public void setToCityCode(String toCityCode) {
        this.toCityCode = toCityCode;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }

    public String getToTerminal() {
        return toTerminal;
    }

    public void setToTerminal(String toTerminal) {
        this.toTerminal = toTerminal;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
