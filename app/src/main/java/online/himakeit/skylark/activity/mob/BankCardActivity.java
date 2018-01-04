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
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobBankCard;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:44
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BankCardActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.btn_bank_num_query)
    Button btn_bank_num_query;
    @Bind(R.id.ed_bank_num_query)
    EditText ed_bank_num_query;
    @Bind(R.id.iv_bank_num_query)
    ImageView iv_bank_num_query;
    @Bind(R.id.tv_bank_name)
    TextView tv_bank_name;
    @Bind(R.id.tv_bank_carname)
    TextView tv_bank_carname;
    @Bind(R.id.tv_bank_carnum)
    TextView tv_bank_carnum;
    @Bind(R.id.tv_bank_cartype)
    TextView tv_bank_cartype;
    @Bind(R.id.tv_bank_carbin)
    TextView tv_bank_carbin;

    String bankNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_bank_card);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tv_title.setText("银行卡号码查询");
    }

    @OnClick({R.id.tv_back, R.id.btn_bank_num_query, R.id.iv_bank_num_query})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_bank_num_query:
                bankNumber = ed_bank_num_query.getText().toString().trim();
                if (!"".equals(bankNumber)) {
                    ed_bank_num_query.setText("");
                }
                break;
            case R.id.btn_bank_num_query:
                bankNumber = ed_bank_num_query.getText().toString().trim();
                if (TextUtils.isEmpty(bankNumber)) {
                    Snackbar.make(btn_bank_num_query, "银行卡号码不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("正在查询。。。");
                MobApiImpl.queryMobBankCradInfo(bankNumber, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobBankCard mobBankCard = (MobBankCard) result;
                            tv_bank_name.setText("所属银行:" + mobBankCard.getBank());
                            tv_bank_carname.setText("卡名:" + mobBankCard.getCardName());
                            tv_bank_carnum.setText("卡号长度:" + mobBankCard.getCardNumber());
                            tv_bank_cartype.setText("卡片类型:" + mobBankCard.getCardType());
                            tv_bank_carbin.setText("bin码:" + mobBankCard.getBin());
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(btn_bank_num_query, result, Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
