package online.himakeit.skylark.activity.mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobCookListRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.listeners.OnItemClickListener;
import online.himakeit.skylark.model.mob.MobCookCategoryEntity;
import online.himakeit.skylark.model.mob.MobCookDetailEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/3 7:57
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class CookListActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    public static final String IntentKey_Cook = "IntentKey_Cook";
    private static final String TAG = "CookListActivity";

    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.swipe_to_load_layout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;

    MobCookCategoryEntity mobCookCategoryEntity = new MobCookCategoryEntity();
    private int pageIndex = 1;
    private int pageSize = 10;

    private List<MobCookDetailEntity.MobCookDetailListBean> mDatas = new ArrayList<MobCookDetailEntity.MobCookDetailListBean>();
    MobCookListRecyclerAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_cook_list);

        ButterKnife.bind(this);

        initIntent();

        init();

        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        }, 100);

    }

    private void loadNewDatas() {
        pageIndex = 1;
        MobApiImpl.queryMobCookDetailsList(mobCookCategoryEntity.getCategoryInfo().getCtgId(), pageIndex, pageSize, 0x001, mobCallBack);
    }

    private void loadMoreDatas() {
        MobApiImpl.queryMobCookDetailsList(mobCookCategoryEntity.getCategoryInfo().getCtgId(), pageIndex, pageSize, 0x002, mobCallBack);
    }

    private MobCallBack mobCallBack = new MobCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            MobCookDetailEntity mobCookDetailEntity = (MobCookDetailEntity) result;
            pageIndex++;
            if (what == 0x001) {
                mDatas.clear();
                mDatas = mobCookDetailEntity.getList();
                //延时展示刷新动画
                AppContext.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 1000);
            } else {
                List<MobCookDetailEntity.MobCookDetailListBean> moreDatas = mobCookDetailEntity.getList();
                mDatas.addAll(moreDatas);
                swipeToLoadLayout.setLoadingMore(false);
            }

            //判断是不是还能加载更多
            if (mDatas.size() >= mobCookDetailEntity.getTotal()) {
                swipeToLoadLayout.setLoadMoreEnabled(false);
            } else {
                swipeToLoadLayout.setLoadMoreEnabled(true);
            }

            //刷新页面
            initAdapter();
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, String result) {
            if (what == 0x001) {
                swipeToLoadLayout.setRefreshing(false);
            } else {
                swipeToLoadLayout.setLoadingMore(false);
            }
            Snackbar.make(tv_back, result, Snackbar.LENGTH_SHORT).show();
        }
    };

    private void initAdapter() {
        if (adapter == null){
            adapter = new MobCookListRecyclerAdapter(mContext,mDatas);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickLitener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    MobCookDetailEntity.MobCookDetailListBean mobCookDetailListBean = mDatas.get(position);
                    Intent intent = new Intent(CookListActivity.this,CookDetailsActivity.class);
                    intent.putExtra(CookDetailsActivity.IntentKey_Cook,mobCookDetailListBean);
                    CookListActivity.this.startActivity(intent);
                }
            });
        }else {
            adapter.updateDatas(mDatas);
        }
    }

    private void init() {
        tv_title.setText(mobCookCategoryEntity.getCategoryInfo().getName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVerticalScrollBarEnabled(true);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);
    }

    private void initIntent() {
        mobCookCategoryEntity = (MobCookCategoryEntity) getIntent().getSerializableExtra(IntentKey_Cook);
    }

    @OnClick(R.id.tv_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadNewDatas();
    }

    @Override
    public void onLoadMore() {
        loadMoreDatas();
    }
}
