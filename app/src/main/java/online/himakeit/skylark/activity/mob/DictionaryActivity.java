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
import online.himakeit.skylark.model.mob.MobDictEntity;
import online.himakeit.skylark.model.mob.MobItemEntity;
import online.himakeit.skylark.util.KeyboardUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:22
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class DictionaryActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.btn_dict_query)
    Button btn_query;
    @Bind(R.id.ed_dict_query)
    EditText ed_query;
    @Bind(R.id.iv_dict_query)
    ImageView iv_query;
    @Bind(R.id.recycler_dict)
    RecyclerView recyclerView;

    String keyWord;
    MobQueryRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_dictionary);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tv_title.setText("新华字典");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick({R.id.tv_back, R.id.iv_dict_query, R.id.btn_dict_query})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_dict_query:
                keyWord = ed_query.getText().toString().trim();
                if (!"".equals(keyWord)) {
                    ed_query.setText("");
                }
                break;
            case R.id.btn_dict_query:
                KeyboardUtils.hideSoftInput(this);
                keyWord = ed_query.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    Snackbar.make(btn_query, "输入内容不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("正在查询。。。");
                MobApiImpl.queryMobDict(keyWord, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobDictEntity mobDictEntity = (MobDictEntity) result;
                            initAdapter(mobDictEntity);
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

    private void initAdapter(MobDictEntity mobDictEntity) {
        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new MobItemEntity("拼音:", mobDictEntity.getPinyin()));
        mDatas.put("1", new MobItemEntity("简介:", mobDictEntity.getBrief()));
        mDatas.put("2", new MobItemEntity("明细:", mobDictEntity.getDetail()));
        mDatas.put("3", new MobItemEntity("部首:", mobDictEntity.getBushou()));
        mDatas.put("4", new MobItemEntity("笔画数:", String.valueOf(mobDictEntity.getBihua())));
        mDatas.put("5", new MobItemEntity("五笔:", mobDictEntity.getWubi()));

        if (recyclerAdapter == null) {
            recyclerAdapter = new MobQueryRecyclerAdapter(this, mDatas);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.updateDatas(mDatas);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
