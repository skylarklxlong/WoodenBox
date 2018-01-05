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
import online.himakeit.skylark.model.mob.MobBankCard;
import online.himakeit.skylark.model.mob.MobItemEntity;
import online.himakeit.skylark.util.KeyboardUtils;

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
    @Bind(R.id.recycler_bank)
    RecyclerView recyclerView;

    MobQueryRecyclerAdapter recyclerAdapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                KeyboardUtils.hideSoftInput(this);
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
                            initAdapter(mobBankCard);
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

    private void initAdapter(MobBankCard mobBankCard) {
        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new MobItemEntity("所属银行:", mobBankCard.getBank()));
        mDatas.put("1", new MobItemEntity("卡名:", mobBankCard.getCardName()));
        mDatas.put("2", new MobItemEntity("卡号长度:", String.valueOf(mobBankCard.getCardNumber())));
        mDatas.put("3", new MobItemEntity("卡片类型:", mobBankCard.getCardType()));
        mDatas.put("4", new MobItemEntity("bin码:", mobBankCard.getBin()));

        if (recyclerAdapter == null) {
            recyclerAdapter = new MobQueryRecyclerAdapter(this, mDatas);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.updateDatas(mDatas);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
