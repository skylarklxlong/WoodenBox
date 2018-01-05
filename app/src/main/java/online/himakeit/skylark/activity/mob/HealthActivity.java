package online.himakeit.skylark.activity.mob;

import android.graphics.Color;
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

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobHealthRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobHealthEntity;
import online.himakeit.skylark.util.KeyboardUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class HealthActivity extends OtherBaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.btn_health_query)
    Button btn_health_query;
    @Bind(R.id.ed_health_query)
    EditText ed_health_query;
    @Bind(R.id.iv_health_query)
    ImageView iv_health_query;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;

    private String keyWord;
    private int pageIndex = 1;
    private int pageSize = 20;
    private ArrayList<MobHealthEntity.MobHealthListBean> mDatas = new ArrayList<>();
    private MobHealthRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_health);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tv_title.setText("健康");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dx) > 20) {
                    KeyboardUtils.hideSoftInput(HealthActivity.this);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }

    @OnClick({R.id.tv_back, R.id.iv_health_query, R.id.btn_health_query})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_health_query:
                keyWord = ed_health_query.getText().toString().trim();
                if (!"".equals(keyWord)) {
                    ed_health_query.setText("");
                }
                break;
            case R.id.btn_health_query:
                showProgressDialog("正在查询。。。");
                keyWord = ed_health_query.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    Snackbar.make(btn_health_query, "查询内容不能为空", Snackbar.LENGTH_SHORT).show();
                    dissmissProgressDialog();
                    return;
                }
                KeyboardUtils.hideSoftInput(this);
                onRefresh();
                break;
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        queryDatas(0x001);
    }

    private void queryDatas(int what) {
        MobApiImpl.queryMobHealth(keyWord, pageIndex, pageSize, what, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                MobHealthEntity mobHealthEntity = (MobHealthEntity) result;
                if (mobHealthEntity != null) {
                    List<MobHealthEntity.MobHealthListBean> list = mobHealthEntity.getList();
                    if (what == 0x001) {
                        mDatas = (ArrayList<MobHealthEntity.MobHealthListBean>) list;
                    } else {
                        mDatas.addAll(list);
                    }
                    initAdapter();

                    if (mDatas.size() < pageIndex * pageSize) {
                        swipeToLoadLayout.setLoadMoreEnabled(false);
                    } else {
                        swipeToLoadLayout.setLoadMoreEnabled(true);
                    }

                    if (mDatas.size() > 0) {
                        swipeToLoadLayout.setRefreshEnabled(true);
                    } else {
                        swipeToLoadLayout.setRefreshEnabled(false);
                    }
                    pageIndex++;
                    overRefresh();
                }
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                Snackbar.make(btn_health_query, result, Snackbar.LENGTH_SHORT).show();
                overRefresh();
            }
        });
    }

    private void overRefresh() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    private void initAdapter() {
        if (recyclerAdapter == null) {
            recyclerAdapter = new MobHealthRecyclerAdapter(this, mDatas);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.updateDatas(mDatas);
        }
    }

    @Override
    public void onLoadMore() {
        queryDatas(0x002);
    }
}
