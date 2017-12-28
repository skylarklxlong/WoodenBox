package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobCookDetailsRecyclerAdapter;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobCookDetailEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/8 19:19
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class CookDetailsActivity extends OtherBaseActivity {

    public static final String IntentKey_Cook = "IntentKey_Cook";
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.recycler_cook_details)
    RecyclerView recyclerView;

    MobCookDetailEntity.MobCookDetailListBean mData = new MobCookDetailEntity.MobCookDetailListBean();
    MobCookDetailsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_cook_detail);
        ButterKnife.bind(this);
        initIntent();

        initView();

        initAdapter();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void initAdapter() {
        adapter = new MobCookDetailsRecyclerAdapter(mContext,mData);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        tv_title.setText(mData.getRecipe().getTitle());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVerticalScrollBarEnabled(true);
    }

    private void initIntent() {
        mData = (MobCookDetailEntity.MobCookDetailListBean) getIntent().getSerializableExtra(IntentKey_Cook);
    }

    @OnClick(R.id.tv_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
