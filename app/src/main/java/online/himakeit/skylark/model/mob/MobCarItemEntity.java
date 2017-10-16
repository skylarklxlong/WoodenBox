package online.himakeit.skylark.model.mob;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/16 20:16
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * brandName : 奥迪Q5
 * carId : 1060133
 * guidePrice : 38.34万
 * seriesName : 奥迪Q5 2016款 40 TFSI 进取型
 */
public class MobCarItemEntity {

    private String brandName;
    private String carId;
    private String guidePrice;
    private String seriesName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
