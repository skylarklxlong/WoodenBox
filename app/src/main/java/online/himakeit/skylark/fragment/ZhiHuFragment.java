package online.himakeit.skylark.fragment;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.adapter.ZhiHuAdapter;
import online.himakeit.skylark.model.zhuhu.ZhiHuDaily;
import online.himakeit.skylark.presenter.implPresenter.ZhiHuPresenterImpl;
import online.himakeit.skylark.presenter.implView.IZhiHuFragment;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 15:40
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuFragment extends BaseFragment implements IZhiHuFragment {

    @Bind(R.id.recycle_zhihu)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    ZhiHuPresenterImpl zhiHuPresenter;
    ZhiHuAdapter zhiHuAdapter;

    LinearLayoutManager linearLayoutManager;
    RecyclerView.OnScrollListener loadingMoreListener;

    boolean loading;
    private String currentLoadDate;

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
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
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

        loadData();
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
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String error) {
        if (recyclerView != null) {
            Snackbar.make(recyclerView, "请检查网络！", Snackbar.LENGTH_SHORT)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
        zhiHuAdapter.addItems(zhiHuDaily.getStories());

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
