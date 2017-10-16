package online.himakeit.skylark.model.mob;

import com.google.gson.annotations.SerializedName;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/16 19:23
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:银行卡信息查询
 * {
 "msg": "success",
 "result": {
 "bank": "农业银行",
 "bin": "622848",
 "binNumber": 6,
 "cardName": "金穗通宝卡(银联卡)",
 "cardNumber": 19,
 "cardType": "借记卡"
 },
 "retCode": "200"
 }
 */
public class MobBankCard {
    @SerializedName("bank")
    private String bank;
    @SerializedName("bin")
    private String bin;
    @SerializedName("binNumber")
    private int binNumber;
    @SerializedName("cardName")
    private String cardName;
    @SerializedName("cardNumber")
    private int cardNumber;
    @SerializedName("cardType")
    private String cardType;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public int getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(int binNumber) {
        this.binNumber = binNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
