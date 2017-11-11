package online.himakeit.skylark.activity.mob;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.mob.MobIpEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:40
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class IPQueryActivity extends BaseActivity {

    /**
     * toolbar
     */
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    /**
     * 其他UI
     */
    @Bind(R.id.btn_ip_address_query)
    Button btn_ip_address_query;
    @Bind(R.id.ed_ip_address_query)
    EditText ed_ip_address_query;
    @Bind(R.id.iv_ip_address_query)
    ImageView iv_ip_address_query;
    @Bind(R.id.tv_ip_address)
    TextView tv_ip_address;
    @Bind(R.id.tv_ip_country)
    TextView tv_ip_country;
    @Bind(R.id.tv_ip_city)
    TextView tv_ip_city;

    String ipAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_ip_query);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tv_title.setText("IP地址查询");
    }

    @OnClick({R.id.tv_back, R.id.iv_ip_address_query, R.id.btn_ip_address_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_ip_address_query:
                ipAddress = ed_ip_address_query.getText().toString().trim();
                if (!"".equals(ipAddress)) {
                    ed_ip_address_query.setText("");
                }
                break;
            case R.id.btn_ip_address_query:
                ipAddress = ed_ip_address_query.getText().toString().trim();
                if (TextUtils.isEmpty(ipAddress)) {
                    Snackbar.make(btn_ip_address_query, "IP地址不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("正在查询...");
                MobApiImpl.queryMobIp(ipAddress, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobIpEntity ipEntity = (MobIpEntity) result;

                            tv_ip_address.setText("IP：     " + ipEntity.getIp());
                            tv_ip_country.setText("国家： " + ipEntity.getCountry());
                            tv_ip_city.setText("城市：         " + ipEntity.getProvince() + " " + ipEntity.getCity());
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(btn_ip_address_query, result, Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
