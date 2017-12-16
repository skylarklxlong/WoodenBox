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
import online.himakeit.skylark.model.mob.MobPostCodeEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:39
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class PostCodeActivity extends BaseActivity {

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
    @Bind(R.id.ed_post_code)
    EditText ed_post_code;
    @Bind(R.id.iv_post_code_delete)
    ImageView iv_delete;
    @Bind(R.id.btn_post_code_query)
    Button btn_query;
    @Bind(R.id.tv_post_code_pro)
    TextView tv_pro;
    @Bind(R.id.tv_post_code_city)
    TextView tv_city;
    @Bind(R.id.tv_post_code_qx)
    TextView tv_qx;
    @Bind(R.id.tv_post_code_detail)
    TextView tv_detail;

    String postCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_post_code);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tv_title.setText("邮编查询");
    }

    @OnClick({R.id.btn_post_code_query, R.id.iv_post_code_delete, R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_post_code_delete:
                postCode = ed_post_code.getText().toString().trim();
                if (!"".equals(postCode)) {
                    ed_post_code.setText("");
                }
                break;
            case R.id.btn_post_code_query:
                postCode = ed_post_code.getText().toString().trim();

                if (TextUtils.isEmpty(postCode)) {
                    Snackbar.make(btn_query, "邮编不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                showProgressDialog("正在查询...");
                MobApiImpl.queryMobPostCode(postCode, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobPostCodeEntity postCodeEntity = (MobPostCodeEntity) result;
                            tv_pro.setText("省份：" + postCodeEntity.getProvince());
                            tv_city.setText("城市：" + postCodeEntity.getCity());
                            tv_qx.setText("区县：" + postCodeEntity.getDistrict());
                            tv_detail.setText("\n" + postCodeEntity.getAddress().toString() + "\n");
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(btn_query, result, Snackbar.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }

}
