package online.himakeit.skylark.fragment;

import android.annotation.SuppressLint;
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
import online.himakeit.skylark.adapter.GankBaseAdapter;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.model.gank.Gank;
import online.himakeit.skylark.presenter.implPresenter.GankPresenterImpl;
import online.himakeit.skylark.presenter.implView.IGankFragment;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 10:05
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanBaseFragment extends BaseFragment implements IGankFragment {

    @Bind(R.id.recycle_neihan_base)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    GankPresenterImpl gankPresenterImpl;
    GankBaseAdapter gankBaseAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.OnScrollListener loadingMoreListener;

    boolean isLoading;
    int currentLoadData = 1;

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce = false;

    String mTtype = "Android";

    public NeiHanBaseFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public NeiHanBaseFragment(String type) {
        super();
        mTtype = type;
    }

    public static NeiHanBaseFragment newInstance(String type) {
        NeiHanBaseFragment gankBaseFragment = new NeiHanBaseFragment(type);
        return gankBaseFragment;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_neihan_base, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        gankPresenterImpl = new GankPresenterImpl(getContext(), this);
        gankBaseAdapter = new GankBaseAdapter(getContext());
        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
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
        recyclerView.setAdapter(gankBaseAdapter);
        recyclerView.addOnScrollListener(loadingMoreListener);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

//        loadData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (mHasLoadedOnce) {
                return;
            }
            loadData();
            mHasLoadedOnce = true;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void loadData() {
        if (gankBaseAdapter.getItemCount() > 0) {
            gankBaseAdapter.clearData();
        }
//        gankPresenterImpl.getGankData("Android",currentLoadData);
        gankPresenterImpl.getGankData(mTtype, currentLoadData);
    }

    private void loadMoreDate() {
        gankBaseAdapter.loadingStart();
//        gankPresenterImpl.getGankData("Android",currentLoadData);
        gankPresenterImpl.getGankData(mTtype, currentLoadData);
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
//                            gankPresenterImpl.getGankData("Android",currentLoadData);
                            gankPresenterImpl.getGankData(mTtype, currentLoadData);
                        }
                    }).show();
        }
    }

    @Override
    public void updateGankData(ArrayList<Gank> list) {
        gankBaseAdapter.loadingFinish();
        isLoading = false;
        gankBaseAdapter.addItems(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gankPresenterImpl.unsubscrible();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
