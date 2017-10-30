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
import online.himakeit.skylark.model.mob.MobPhoneAddressEntity;
import online.himakeit.skylark.util.LogUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:37
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class PhoneAddressActivity extends BaseActivity {

    /**
     * toolbar
     */
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.btn_phone_address_query)
    Button btn_phone_address_query;
    @Bind(R.id.ed_phone_address_query)
    EditText ed_phone_address_query;
    @Bind(R.id.iv_phone_address_query)
    ImageView iv_phone_address_query;

    String phoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_phone_address);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tv_title.setText("手机号码归属地查询");
    }

    @OnClick({R.id.tv_back, R.id.iv_phone_address_query, R.id.btn_phone_address_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_phone_address_query:
                phoneNumber = ed_phone_address_query.getText().toString().trim();
                if (!"".equals(phoneNumber)) {
                    ed_phone_address_query.setText("");
                }
                break;
            case R.id.btn_phone_address_query:
                phoneNumber = ed_phone_address_query.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Snackbar.make(btn_phone_address_query, "手机号码不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                MobApiImpl.queryMobMobileAddress(phoneNumber, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        if (result != null) {
                            MobPhoneAddressEntity phoneAddressEntity = (MobPhoneAddressEntity) result;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(phoneAddressEntity.getCity() + "\n");
                            stringBuilder.append(phoneAddressEntity.getCityCode() + "\n");
                            stringBuilder.append(phoneAddressEntity.getMobileNumber() + "\n");
                            stringBuilder.append(phoneAddressEntity.getOperator() + "\n");
                            stringBuilder.append(phoneAddressEntity.getProvince() + "\n");
                            stringBuilder.append(phoneAddressEntity.getZipCode() + "\n");
                            LogUtils.show("skylark",stringBuilder+"");
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {

                    }
                });
                break;
        }
    }
}
