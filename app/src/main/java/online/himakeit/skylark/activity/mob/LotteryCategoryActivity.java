package online.himakeit.skylark.activity.mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobLotteryCategoryRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.callback.OnItemClickListener;
import online.himakeit.skylark.common.OtherBaseActivity;

/**
 * @author：LiXueLong
 * @date:2018/1/3-16:02
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des：LotteryCategoryActivity
 */
public class LotteryCategoryActivity extends OtherBaseActivity {

    /**
     * toolbar
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.recycler_lottery)
    RecyclerView recyclerView;

    private ArrayList<String> mDatas = new ArrayList<>();
    private MobLotteryCategoryRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_lottery_cateory);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    private void loadData() {
        showProgressDialog();
        MobApiImpl.queryMoblotteryList(0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                dissmissProgressDialog();
                mDatas = (ArrayList<String>) results;
                initAdapter();
            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                Snackbar.make(recyclerView, result, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        adapter = new MobLotteryCategoryRecyclerAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转详情页面
                String name = mDatas.get(position);
                Intent intent = new Intent(LotteryCategoryActivity.this, LotteryDetailActivity.class);
                intent.putExtra(LotteryDetailActivity.IntentKey_LotteryName, name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        tv_title.setText("彩票");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.tv_back)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
