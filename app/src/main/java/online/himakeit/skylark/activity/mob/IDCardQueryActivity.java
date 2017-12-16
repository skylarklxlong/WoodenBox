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
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.mob.MobIdCardEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:40
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class IDCardQueryActivity extends BaseActivity {

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
    @Bind(R.id.btn_idcard_query)
    Button btn_idcard_query;
    @Bind(R.id.ed_idcard_query)
    EditText ed_idcard_query;
    @Bind(R.id.iv_idcard_query)
    ImageView iv_idcard_query;
    @Bind(R.id.tv_idcard_city)
    TextView tv_idcard_city;
    @Bind(R.id.tv_idcard_date)
    TextView tv_idcard_date;
    @Bind(R.id.tv_idcard_sex)
    TextView tv_idcard_sex;

    String idCardNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_id_card_query);
        ButterKnife.bind(this);

        init();
    }
    private void init() {
        tv_title.setText("身份证号码查询");
    }

    @OnClick({R.id.tv_back, R.id.iv_idcard_query, R.id.btn_idcard_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_idcard_query:
                idCardNumber = ed_idcard_query.getText().toString().trim();
                if (!"".equals(idCardNumber)) {
                    ed_idcard_query.setText("");
                }
                break;
            case R.id.btn_idcard_query:
                idCardNumber = ed_idcard_query.getText().toString().trim();
                if (TextUtils.isEmpty(idCardNumber)) {
                    Snackbar.make(ed_idcard_query, "身份证号码不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                showProgressDialog("正在查询...");
                MobApiImpl.queryMobIdcard(idCardNumber, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobIdCardEntity idCardEntity = (MobIdCardEntity) result;

                            tv_idcard_city.setText("城市：     " + idCardEntity.getArea());
                            tv_idcard_date.setText("生日：         " + idCardEntity.getBirthday());
                            tv_idcard_sex.setText("性别： " + idCardEntity.getSex());
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(btn_idcard_query, result, Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
