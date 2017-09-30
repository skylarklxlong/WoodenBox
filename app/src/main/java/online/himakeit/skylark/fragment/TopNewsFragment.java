package online.himakeit.skylark.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.adapter.TopNewsAdapter;
import online.himakeit.skylark.model.topnews.NewsBean;
import online.himakeit.skylark.model.topnews.NewsList;
import online.himakeit.skylark.presenter.implPresenter.TopNewsNewPresenterImpl;
import online.himakeit.skylark.presenter.implView.ITopNewsFragment;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;
import online.himakeit.skylark.view.MultiSwipeRefreshLayout;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 15:04
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TopNewsFragment extends BaseFragment implements ITopNewsFragment {

    @Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycle_topnews)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.iv_tip_fail)
    ImageView mIvTipFail;

    private boolean isLoading;
    private int currentIndex;
    private TopNewsNewPresenterImpl mTopNewsPresenter;
    private TopNewsAdapter mTopNewsAdapter;
    private RecyclerView.OnScrollListener loadingMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce = false;


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_topnews, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mTopNewsPresenter = new TopNewsNewPresenterImpl(this);
        mTopNewsAdapter = new TopNewsAdapter(getContext());
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {//向上滑动，向下滚动
                    int visibileItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();
                    if (!mSwipeRefreshLayout.isRefreshing() && !isLoading && (visibileItemCount + pastVisibleItems) >= totalItemCount) {
                        isLoading = true;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadMoreData();
                    }
                }
            }
        };
        mLinearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        // TODO: 2017/7/28 RecyclerView它本身只关心view的回收与复用，其他的都需要自己去定义
        //设置布局管理器，控制显示方式
        recyclerView.setLayoutManager(mLinearLayoutManager);
        //添加分割线 这个只有在layoutmanager为linearlayoutmanager时才能使用
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        //设置item增加和移除的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mTopNewsAdapter);
        recyclerView.setOnScrollListener(loadingMoreListener);

//        loadData();

        QueryBuilder queryBuilder = new QueryBuilder(NewsBean.class);
        queryBuilder.appendOrderDescBy("ptime");
        queryBuilder.limit(0,10);
        if (AppContext.liteOrmDB.query(queryBuilder).size() > 0){
            mTopNewsAdapter.addItem(AppContext.liteOrmDB.query(queryBuilder));
        }
        trySetupSwipeRefresh();

    }

    private void trySetupSwipeRefresh(){
        if (mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                    R.color.refresh_progress_2, R.color.refresh_progress_1);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 防止刷新消失太快，让子弹飞一会儿.
                    mSwipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mSwipeRefreshLayout != null) {
                                mSwipeRefreshLayout.setRefreshing(true);
                                loadData(true);
                            }
                        }
                    }, 1000);

                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (mHasLoadedOnce) {
                return;
            }
            mSwipeRefreshLayout.setRefreshing(true);
            loadData(true);
            mHasLoadedOnce = true;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void loadData(boolean isClean) {
        if (isClean) {
            mTopNewsAdapter.clearData();
        }
        currentIndex = 0;
        mTopNewsPresenter.getNewsList(currentIndex);
    }

    private void loadMoreData() {
        mTopNewsAdapter.loadingStart();
        currentIndex += 20;
        mTopNewsPresenter.getNewsList(currentIndex);
    }

    @Override
    public void updateListItem(NewsList newsList) {
        isLoading = false;
        AppContext.liteOrmDB.insert(newsList.getNewsList(), ConflictAlgorithm.Replace);
        mTopNewsAdapter.addItem(newsList.getNewsList());
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgressDialog() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        mSwipeRefreshLayout.setRefreshing(false);
        mIvTipFail.setVisibility(View.VISIBLE);
        mIvTipFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvTipFail.setVisibility(View.INVISIBLE);
                mSwipeRefreshLayout.setRefreshing(true);
                loadMoreData();
            }
        });
        if (recyclerView != null) {
            Snackbar.make(recyclerView, "请检查网络！", Snackbar.LENGTH_LONG)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            mTopNewsPresenter.getNewsList(currentIndex);
                            mSwipeRefreshLayout.setRefreshing(true);
                            loadMoreData();
                        }
                    }).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTopNewsPresenter.unsubscrible();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
