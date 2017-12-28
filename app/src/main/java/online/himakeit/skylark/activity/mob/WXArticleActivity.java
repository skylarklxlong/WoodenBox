package online.himakeit.skylark.activity.mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.adapter.MobWXArticleCategoryRecyclerAdapter;
import online.himakeit.skylark.adapter.MobWXArticleRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.callback.OnItemClickListener;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobWxArticleEntity;
import online.himakeit.skylark.model.mob.MobWxCategoryEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:43
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class WXArticleActivity extends OtherBaseActivity implements OnRefreshListener, OnLoadMoreListener {

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
    @Bind(R.id.rl_wxarticle_category)
    RelativeLayout rl_wxarticle_category;
    @Bind(R.id.tv_wxarticle_title)
    TextView tv_wxarticle_title;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.swipe_target)
    RecyclerView swipe_target;
    @Bind(R.id.recycler_wxacticle_category)
    RecyclerView recycler_wxacticle_category;
    @Bind(R.id.rl_wxarticle_category_bg)
    RelativeLayout rl_wxarticle_category_bg;

    List<MobWxCategoryEntity> wxCategoryEntityList = new ArrayList<MobWxCategoryEntity>();
    List<MobWxArticleEntity.MobWxArticleListBean> mobWxArticleListBeans = new ArrayList<MobWxArticleEntity.MobWxArticleListBean>();
    private int pageIndex = 1;
    private int pageSize = 20;
    MobWxCategoryEntity wxCategoryEntity;
    MobWXArticleRecyclerAdapter wxArticleRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_wx_article);
        ButterKnife.bind(this);

        initViews();

        queryWXCategory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void initViews() {

        tv_title.setText("微信精选");

        recycler_wxacticle_category.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_wxacticle_category.setItemAnimator(new DefaultItemAnimator());

        swipe_target.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipe_target.setItemAnimator(new DefaultItemAnimator());
        swipe_target.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);
    }

    private void queryWXCategory() {
        showProgressDialog("数据加载中...");
        MobApiImpl.queryMobWxArticleCategory(0x001, callBack);
    }

    private MobCallBack callBack = new MobCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            if (what == 0x002) {//加载新数据
                MobWxArticleEntity wxArticleEntity = (MobWxArticleEntity) result;
                if (wxArticleEntity != null) {
                    mobWxArticleListBeans.clear();
                    mobWxArticleListBeans = wxArticleEntity.getList();
                }
                refreshAdapter();
                swipeToLoadLayout.setRefreshing(false);
            } else if (what == 0x003) {//加载下一页数据
                MobWxArticleEntity wxArticleEntity = (MobWxArticleEntity) result;
                if (wxArticleEntity != null) {
                    mobWxArticleListBeans.addAll(wxArticleEntity.getList());
                }
                refreshAdapter();
                swipeToLoadLayout.setLoadingMore(false);
            }

        }

        @Override
        public void onSuccessList(int what, List results) {
            if (what == 0x001) {
                dissmissProgressDialog();
                if (results != null && results.size() > 0) {
                    wxCategoryEntityList = (List<MobWxCategoryEntity>) results;
                    //初始化分类的列表
                    initCategoryAdapter();
                }
            }
        }

        @Override
        public void onFail(int what, String result) {
            dissmissProgressDialog();
            Snackbar.make(rl_wxarticle_category, result, Snackbar.LENGTH_SHORT).show();
            if (what == 0x002) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (what == 0x003) {
                swipeToLoadLayout.setLoadingMore(false);
            }

        }
    };

    private void initCategoryAdapter() {
        MobWXArticleCategoryRecyclerAdapter categoryRecyclerAdapter = new MobWXArticleCategoryRecyclerAdapter(this, wxCategoryEntityList);
        recycler_wxacticle_category.setAdapter(categoryRecyclerAdapter);
        categoryRecyclerAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                wxCategoryEntity = wxCategoryEntityList.get(position);
                tv_wxarticle_title.setText(wxCategoryEntity.getName());
                hideCategroyRecyclerView();
                //加载列表
                loadNewDatas();
            }
        });
    }

    private void loadNewDatas() {
        //清空
        mobWxArticleListBeans.clear();
        refreshAdapter();
        swipeToLoadLayout.setRefreshing(true);

        pageIndex = 1;
        MobApiImpl.queryMobWxArticle(wxCategoryEntity.getCid(), pageIndex, pageSize, 0x002, callBack);

    }

    private void loadMoreDatas() {
        MobApiImpl.queryMobWxArticle(wxCategoryEntity.getCid(), pageIndex, pageSize, 0x003, callBack);
    }

    private void refreshAdapter() {
        pageIndex++;
        //刷新列表
        if (wxArticleRecyclerAdapter == null){
            wxArticleRecyclerAdapter = new MobWXArticleRecyclerAdapter(this,mobWxArticleListBeans);
            swipe_target.setAdapter(wxArticleRecyclerAdapter);
            wxArticleRecyclerAdapter.setOnItemClickLitener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    MobWxArticleEntity.MobWxArticleListBean listBean = mobWxArticleListBeans.get(position);
                    Intent intent = WebActivity.newTntent(mContext, listBean.getSourceUrl(), listBean.getTitle());
                    startActivity(intent);
                }
            });
        }else {
            wxArticleRecyclerAdapter.updateDatas(mobWxArticleListBeans);
        }
    }

    private void hideCategroyRecyclerView() {
        rl_wxarticle_category_bg.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadNewDatas();
    }

    @Override
    public void onLoadMore() {
        loadMoreDatas();
    }

    @OnClick({R.id.tv_back, R.id.rl_wxarticle_category_bg, R.id.rl_wxarticle_category})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_wxarticle_category_bg:
                //隐藏显示列表
                hideCategroyRecyclerView();
                break;
            case R.id.rl_wxarticle_category:
                //隐藏显示列表
                if (rl_wxarticle_category_bg.getVisibility() == View.GONE) {
                    rl_wxarticle_category_bg.setVisibility(View.VISIBLE);
                } else {
                    hideCategroyRecyclerView();
                }
                break;
        }
    }
}
