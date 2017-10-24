package online.himakeit.skylark.model.mob;

import java.io.Serializable;
import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/23 20:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobCitysEntity implements Serializable {
    /**
     * {
     "msg": "success",
     "result": [
        {"city": [
            {"city": "香港","district": [
                {"district": "香港"},{"district": "九龙"},{"district": "新界"}
            ]}
        ],
        "province": "香港"
        },
        {}
     ],
     "retCode": "200"
     }
     */

    private String msg;
    private List<MobCitysResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MobCitysResultBean> getResult() {
        return result;
    }

    public void setResult(List<MobCitysResultBean> result) {
        this.result = result;
    }

    public static class MobCitysResultBean implements Serializable {

        private String province;
        private List<MobCitysResultCityBean> city;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public List<MobCitysResultCityBean> getCity() {
            return city;
        }

        public void setCity(List<MobCitysResultCityBean> city) {
            this.city = city;
        }

        public static class MobCitysResultCityBean implements Serializable {

            private String city;
            private List<MobCitysResultCityDistrictBean> district;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public List<MobCitysResultCityDistrictBean> getDistrict() {
                return district;
            }

            public void setDistrict(List<MobCitysResultCityDistrictBean> district) {
                this.district = district;
            }

            public static class MobCitysResultCityDistrictBean implements Serializable {
                private String district;

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }
            }
        }
    }
}
