package online.himakeit.skylark.activity.mob;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobQueryRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobItemEntity;
import online.himakeit.skylark.model.mob.MobPhoneAddressEntity;
import online.himakeit.skylark.util.KeyboardUtils;
import online.himakeit.skylark.util.TextStrUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:37
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class PhoneAddressActivity extends OtherBaseActivity {

    /**
     * toolbar
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    /**
     * 其他UI
     */
    @Bind(R.id.btn_phone_address_query)
    Button btn_phone_address_query;
    @Bind(R.id.ed_phone_address_query)
    EditText ed_phone_address_query;
    @Bind(R.id.iv_phone_address_query)
    ImageView iv_phone_address_query;
    @Bind(R.id.recycler_phone)
    RecyclerView recyclerView;

    MobQueryRecyclerAdapter recyclerAdapter;
    String phoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_phone_address);
        ButterKnife.bind(this);

        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        tv_title.setText("手机号码归属地查询");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                KeyboardUtils.hideSoftInput(this);
                phoneNumber = ed_phone_address_query.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Snackbar.make(btn_phone_address_query, "手机号码不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (!TextStrUtils.isMobileNum(phoneNumber)) {
                    Snackbar.make(btn_phone_address_query, "手机号码格式不正确", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("正在查询...");
                MobApiImpl.queryMobMobileAddress(phoneNumber, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobPhoneAddressEntity phoneAddressEntity = (MobPhoneAddressEntity) result;
                            initAdapter(phoneAddressEntity);
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(btn_phone_address_query, result, Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void initAdapter(MobPhoneAddressEntity phoneAddressEntity) {
        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new MobItemEntity("运营商:", phoneAddressEntity.getOperator()));
        mDatas.put("1", new MobItemEntity("城市:", phoneAddressEntity.getProvince() + " " + phoneAddressEntity.getCity()));
        mDatas.put("2", new MobItemEntity("城市区号:", phoneAddressEntity.getCityCode()));
        mDatas.put("3", new MobItemEntity("邮政编码:", phoneAddressEntity.getZipCode()));

        if (recyclerAdapter == null) {
            recyclerAdapter = new MobQueryRecyclerAdapter(this, mDatas);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.updateDatas(mDatas);
        }
    }
}
