package online.himakeit.skylark.common;

import android.os.Bundle;

import online.himakeit.skylark.R;
import online.himakeit.skylark.util.StatusBarUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/28 19:29
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class OtherBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.with(this)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .clearActionBarShadow()
                .init();
    }
}
