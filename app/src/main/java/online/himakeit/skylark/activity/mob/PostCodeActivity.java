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
import online.himakeit.skylark.model.mob.MobPostCodeEntity;
import online.himakeit.skylark.util.KeyboardUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:39
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class PostCodeActivity extends OtherBaseActivity {

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
    @Bind(R.id.recycler_postcode)
    RecyclerView recyclerView;

    MobQueryRecyclerAdapter recyclerAdapter;

    String postCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_post_code);
        ButterKnife.bind(this);

        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        tv_title.setText("邮编查询");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                KeyboardUtils.hideSoftInput(this);
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
                            initAdapter(postCodeEntity);
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

    private void initAdapter(MobPostCodeEntity postCodeEntity) {
        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new MobItemEntity("省份:", postCodeEntity.getProvince()));
        mDatas.put("1", new MobItemEntity("城市:", postCodeEntity.getCity()));
        mDatas.put("2", new MobItemEntity("区县:", postCodeEntity.getDistrict()));
        mDatas.put("3", new MobItemEntity("区县:", postCodeEntity.getAddress().toString()));

        if (recyclerAdapter == null) {
            recyclerAdapter = new MobQueryRecyclerAdapter(this, mDatas);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.updateDatas(mDatas);
        }
    }

}
