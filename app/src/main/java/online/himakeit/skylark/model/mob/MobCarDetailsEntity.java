package online.himakeit.skylark.model.mob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/16 19:58
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:车型详细信息
 */
public class MobCarDetailsEntity {

    private String brand;//品牌名称
    private String brandName;   //车系名称
    private String carImage;    //	图片地址
    private String seriesName;  //车型名称
    private String sonBrand;    //子品牌或合资品牌

    private List<DetailItem> baseInfo = new ArrayList<>(); //车型基本配置信息
    private List<DetailItem> airConfig = new ArrayList<>();  //空调/冰箱配置信息
    private List<DetailItem> carbody = new ArrayList<>();  //车身配置信息
    private List<DetailItem> chassis = new ArrayList<>();  //底盘配置信息
    private List<DetailItem> controlConfig = new ArrayList<>();  //操控配置信息
    private List<DetailItem> engine = new ArrayList<>();  //发动机配置信息
    private List<DetailItem> exterConfig = new ArrayList<>();  //外部配置信息
    private List<DetailItem> glassConfig = new ArrayList<>();  //玻璃/后视镜配置信息
    private List<DetailItem> interConfig = new ArrayList<>();  //内部配置信息
    private List<DetailItem> lightConfig = new ArrayList<>();  //灯光配置信息
    private List<DetailItem> mediaConfig = new ArrayList<>();  //多媒体配置信息
    private List<DetailItem> safetyDevice = new ArrayList<>();  //安全装置信息
    private List<DetailItem> seatConfig = new ArrayList<>();  //座椅配置信息
    private List<DetailItem> techConfig = new ArrayList<>();  //高科技配置信息
    private List<DetailItem> transmission = new ArrayList<>();  //变速箱信息
    private List<DetailItem> wheelInfo = new ArrayList<>();  //	车轮制动信息
    private List<DetailItem> motorList = new ArrayList<>();  //电动机配置信息

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSonBrand() {
        return sonBrand;
    }

    public void setSonBrand(String sonBrand) {
        this.sonBrand = sonBrand;
    }

    public List<DetailItem> getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(List<DetailItem> baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<DetailItem> getAirConfig() {
        return airConfig;
    }

    public void setAirConfig(List<DetailItem> airConfig) {
        this.airConfig = airConfig;
    }

    public List<DetailItem> getCarbody() {
        return carbody;
    }

    public void setCarbody(List<DetailItem> carbody) {
        this.carbody = carbody;
    }

    public List<DetailItem> getChassis() {
        return chassis;
    }

    public void setChassis(List<DetailItem> chassis) {
        this.chassis = chassis;
    }

    public List<DetailItem> getControlConfig() {
        return controlConfig;
    }

    public void setControlConfig(List<DetailItem> controlConfig) {
        this.controlConfig = controlConfig;
    }

    public List<DetailItem> getEngine() {
        return engine;
    }

    public void setEngine(List<DetailItem> engine) {
        this.engine = engine;
    }

    public List<DetailItem> getExterConfig() {
        return exterConfig;
    }

    public void setExterConfig(List<DetailItem> exterConfig) {
        this.exterConfig = exterConfig;
    }

    public List<DetailItem> getGlassConfig() {
        return glassConfig;
    }

    public void setGlassConfig(List<DetailItem> glassConfig) {
        this.glassConfig = glassConfig;
    }

    public List<DetailItem> getInterConfig() {
        return interConfig;
    }

    public void setInterConfig(List<DetailItem> interConfig) {
        this.interConfig = interConfig;
    }

    public List<DetailItem> getLightConfig() {
        return lightConfig;
    }

    public void setLightConfig(List<DetailItem> lightConfig) {
        this.lightConfig = lightConfig;
    }

    public List<DetailItem> getMediaConfig() {
        return mediaConfig;
    }

    public void setMediaConfig(List<DetailItem> mediaConfig) {
        this.mediaConfig = mediaConfig;
    }

    public List<DetailItem> getSafetyDevice() {
        return safetyDevice;
    }

    public void setSafetyDevice(List<DetailItem> safetyDevice) {
        this.safetyDevice = safetyDevice;
    }

    public List<DetailItem> getSeatConfig() {
        return seatConfig;
    }

    public void setSeatConfig(List<DetailItem> seatConfig) {
        this.seatConfig = seatConfig;
    }

    public List<DetailItem> getTechConfig() {
        return techConfig;
    }

    public void setTechConfig(List<DetailItem> techConfig) {
        this.techConfig = techConfig;
    }

    public List<DetailItem> getTransmission() {
        return transmission;
    }

    public void setTransmission(List<DetailItem> transmission) {
        this.transmission = transmission;
    }

    public List<DetailItem> getWheelInfo() {
        return wheelInfo;
    }

    public void setWheelInfo(List<DetailItem> wheelInfo) {
        this.wheelInfo = wheelInfo;
    }

    public List<DetailItem> getMotorList() {
        return motorList;
    }

    public void setMotorList(List<DetailItem> motorList) {
        this.motorList = motorList;
    }

    @Override
    public String toString() {
        return "MobCarDetailsEntity{" +
                "brand='" + brand + '\'' +
                ", brandName='" + brandName + '\'' +
                ", carImage='" + carImage + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", sonBrand='" + sonBrand + '\'' +
                ", baseInfo=" + baseInfo +
                ", airConfig=" + airConfig +
                ", carbody=" + carbody +
                ", chassis=" + chassis +
                ", controlConfig=" + controlConfig +
                ", engine=" + engine +
                ", exterConfig=" + exterConfig +
                ", glassConfig=" + glassConfig +
                ", interConfig=" + interConfig +
                ", lightConfig=" + lightConfig +
                ", mediaConfig=" + mediaConfig +
                ", safetyDevice=" + safetyDevice +
                ", seatConfig=" + seatConfig +
                ", techConfig=" + techConfig +
                ", transmission=" + transmission +
                ", wheelInfo=" + wheelInfo +
                ", motorList=" + motorList +
                '}';
    }

    public static class DetailItem {
        private String name;
        private String value;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "DetailItem{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
