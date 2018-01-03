package online.himakeit.skylark.activity.mob;

import android.os.Bundle;

import online.himakeit.skylark.R;
import online.himakeit.skylark.common.OtherBaseActivity;

/**
 * @author：LiXueLong
 * @date：2018/1/3
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class LotteryDetailActivity extends OtherBaseActivity {


    public static final String IntentKey_LotteryName = "IntentKey_LotteryName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_lottery_detail);
    }
}
