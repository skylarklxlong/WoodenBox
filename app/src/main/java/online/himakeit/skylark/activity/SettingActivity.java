package online.himakeit.skylark.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.presenter.implView.ISettingView;
import online.himakeit.skylark.view.SettingItemView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/7 14:14
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SettingActivity extends BaseActivity implements ISettingView {

    private static final String TAG = "FxcSalaryActivity";
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_back)
    ImageView tv_back;

    @Bind(R.id.setting_item_clean_cache)
    SettingItemView clean_cache;
    @Bind(R.id.setting_item_check_version)
    SettingItemView check_version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        tv_title.setText("设置");
    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void setCacheSize(String cacheSize) {

    }

    @Override
    public void showAppUpdateDialog(AppUpdateInfo appUpdateInfo) {

    }

    @Override
    public void showBasesProgressSuccess(String msg) {

    }

    @Override
    public void showToast(String msg) {

    }
}
