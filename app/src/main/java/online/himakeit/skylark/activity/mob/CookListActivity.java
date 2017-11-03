package online.himakeit.skylark.activity.mob;

import android.os.Bundle;

import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/3 7:57
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class CookListActivity extends BaseActivity {

    public static final String IntentKey_Cook = "IntentKey_Cook";
    private static final String TAG = "CookListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_cook_list);
    }
}
