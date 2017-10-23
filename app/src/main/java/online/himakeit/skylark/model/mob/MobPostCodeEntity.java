package online.himakeit.skylark.model.mob;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/23 19:35
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobPostCodeEntity {
    /**
     * address : ["黄良路","天河西路","黄村镇宋庄","黄村镇太福庄","黄村镇大庄村","北臧村镇砖楼","北臧村镇西大营","黄村镇韩园子村","北臧村镇新立村","北臧村镇六合庄"]
     * city : 北京市
     * district : 大兴区
     * postNumber : 102629
     * province : 北京市
     * size : 10
     */

    private String city;
    private String district;
    private String postNumber;
    private String province;
    private String size;
    private List<String> address;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}
