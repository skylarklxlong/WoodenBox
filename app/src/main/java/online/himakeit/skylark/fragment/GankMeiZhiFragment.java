package online.himakeit.skylark.fragment;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.adapter.GankMeiZhiAdapter;
import online.himakeit.skylark.model.gank.GankMeiZhi;
import online.himakeit.skylark.presenter.implPresenter.GankMeiZhiPresenterImpl;
import online.himakeit.skylark.presenter.implView.IGankMeiZhiFragment;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 10:05
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class GankMeiZhiFragment extends BaseFragment implements IGankMeiZhiFragment {

    @Bind(R.id.recycle_gank_base)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    GankMeiZhiPresenterImpl gankMeiZhiPresenter;
    GankMeiZhiAdapter gankMeiZhiAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.OnScrollListener loadingMoreListener;

    boolean isLoading;
    int currentLoadData = 1;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_gank_base, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        gankMeiZhiPresenter = new GankMeiZhiPresenterImpl(getContext(), this);
        gankMeiZhiAdapter = new GankMeiZhiAdapter(getContext());
        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        currentLoadData += 1;
                        loadMoreDate();
                    }
                }
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(gankMeiZhiAdapter);
        recyclerView.addOnScrollListener(loadingMoreListener);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        loadData();
    }

    private void loadData() {
        if (gankMeiZhiAdapter.getItemCount() > 0) {
            gankMeiZhiAdapter.clearData();
        }
        gankMeiZhiPresenter.getGankMeiZhiData(currentLoadData);
    }

    private void loadMoreDate() {
        gankMeiZhiAdapter.loadingStart();
        gankMeiZhiPresenter.getGankMeiZhiData(currentLoadData);
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
        progressBar.setVisibility(View.INVISIBLE);
        if (recyclerView != null) {
            Snackbar.make(recyclerView, "请检查网络！", Snackbar.LENGTH_SHORT)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gankMeiZhiPresenter.getGankMeiZhiData(currentLoadData);
                        }
                    }).show();
        }
    }

    @Override
    public void updateGankMeiZhiData(ArrayList<GankMeiZhi> list) {
        gankMeiZhiAdapter.loadingFinish();
        isLoading = false;
        gankMeiZhiAdapter.addItems(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gankMeiZhiPresenter.unsubscrible();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
