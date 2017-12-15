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
import online.himakeit.skylark.adapter.ZhiHuAdapter;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.model.zhuhu.ZhiHuDaily;
import online.himakeit.skylark.model.zhuhu.ZhiHuDailyItem;
import online.himakeit.skylark.presenter.implPresenter.ZhiHuPresenterImpl;
import online.himakeit.skylark.presenter.implView.IZhiHuFragment;
import online.himakeit.skylark.view.MultiSwipeRefreshLayout;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 15:40
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuFragment extends BaseFragment implements IZhiHuFragment {

    @Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycle_zhihu)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.iv_tip_fail)
    ImageView mIvTipFail;

    ZhiHuPresenterImpl zhiHuPresenter;
    ZhiHuAdapter zhiHuAdapter;

    LinearLayoutManager linearLayoutManager;
    RecyclerView.OnScrollListener loadingMoreListener;

    boolean loading;
    private String currentLoadDate;

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce = false;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_zhihu, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        zhiHuPresenter = new ZhiHuPresenterImpl(getContext(), this);
        zhiHuAdapter = new ZhiHuAdapter(getContext());
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {//向下滑动
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!mSwipeRefreshLayout.isRefreshing() && !loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadMoreData();
                    }
                }
            }
        };

        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(zhiHuAdapter);
        recyclerView.setOnScrollListener(loadingMoreListener);

        QueryBuilder queryBuilder = new QueryBuilder(ZhiHuDailyItem.class);
        queryBuilder.appendOrderDescBy("date");
        queryBuilder.limit(0,10);
        if (AppContext.liteOrmDB.query(queryBuilder).size() > 0){
            zhiHuAdapter.addItems(AppContext.liteOrmDB.query(queryBuilder));
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
                                loadData();
                            }
                        }
                    }, 1000);

                }
            });
        }
    }

    private void loadData() {
        if (zhiHuAdapter.getItemCount() > 0) {
            zhiHuAdapter.clearData();
        }
        currentLoadDate = "0";
        zhiHuPresenter.getLastZhiHuNews();
    }

    private void loadMoreData() {
        zhiHuAdapter.loadingStart();
        zhiHuPresenter.getTheDaily(currentLoadDate);
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null && !mHasLoadedOnce) {
            progressBar.setVisibility(View.VISIBLE);
            mHasLoadedOnce = true;
        }
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        mSwipeRefreshLayout.setRefreshing(false);
        /*mIvTipFail.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        mIvTipFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIvTipFail.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        if (currentLoadDate.equals("0")) {
                            zhiHuPresenter.getLastZhiHuNews();
                        } else {
                            zhiHuPresenter.getTheDaily(currentLoadDate);
                        }
                    }
                }, 1000);
            }
        });*/
        if (recyclerView != null) {
            Snackbar.make(recyclerView, "请检查网络！", Snackbar.LENGTH_LONG)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSwipeRefreshLayout.setRefreshing(true);
                            if (currentLoadDate.equals("0")) {
                                zhiHuPresenter.getLastZhiHuNews();
                            } else {
                                zhiHuPresenter.getTheDaily(currentLoadDate);
                            }
                        }
                    }).show();
        }
    }

    @Override
    public void updateList(ZhiHuDaily zhiHuDaily) {
        if (loading) {
            loading = false;
            zhiHuAdapter.loadingFinish();
        }
        currentLoadDate = zhiHuDaily.getDate();
        AppContext.liteOrmDB.insert(zhiHuDaily.getStories(), ConflictAlgorithm.Replace);
        zhiHuAdapter.addItems(zhiHuDaily.getStories());
        mSwipeRefreshLayout.setRefreshing(false);
        if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        zhiHuPresenter.unsubscrible();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
