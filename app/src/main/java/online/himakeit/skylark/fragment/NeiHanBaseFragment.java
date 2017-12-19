package online.himakeit.skylark.fragment;

import android.annotation.SuppressLint;
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
import online.himakeit.skylark.adapter.NeiHanBaseAdapter;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.model.neihan.NeiHanDataEntity;
import online.himakeit.skylark.presenter.implPresenter.NeiHanPresenterImpl;
import online.himakeit.skylark.presenter.implView.INeiHanFragment;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 10:05
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanBaseFragment extends BaseFragment implements INeiHanFragment {

    @Bind(R.id.recycle_neihan_base)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    NeiHanPresenterImpl neiHanPresenterImpl = null;
    NeiHanBaseAdapter neiHanBaseAdapter = null;
    LinearLayoutManager linearLayoutManager = null;
    RecyclerView.OnScrollListener loadingMoreListener = null;

    boolean isLoading;
    int currentLoadData = 10;

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce = false;

    String mTtype = "-101";

    public NeiHanBaseFragment() {
        super();
    }
    @SuppressLint("ValidFragment")
    public NeiHanBaseFragment(String type) {
        super();
        if (type.equals("推荐")) {
            mTtype = "-101";
        } else if (type.equals("段子")) {
            mTtype = "-102";
        } else if (type.equals("图片")) {
            mTtype = "-103";
        } else if (type.equals("段友秀")) {
            mTtype = "-301";
        } else if (type.equals("视频")) {
            mTtype = "-104";
        }
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
        neiHanPresenterImpl = new NeiHanPresenterImpl(getContext(), this);
        neiHanBaseAdapter = new NeiHanBaseAdapter(getContext());
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
                        currentLoadData += 10;
                        loadMoreDate();
                    }
                }
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(neiHanBaseAdapter);
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
        if (neiHanBaseAdapter.getItemCount() > 0) {
            neiHanBaseAdapter.clearData();
        }
        neiHanPresenterImpl.getNeiHanData(mTtype, currentLoadData);
    }

    private void loadMoreDate() {
        neiHanBaseAdapter.loadingStart();
        neiHanPresenterImpl.getNeiHanData(mTtype, currentLoadData);
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
                            neiHanPresenterImpl.getNeiHanData(mTtype, currentLoadData);
                        }
                    }).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        neiHanPresenterImpl.unsubscrible();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void updateNeiHanData(NeiHanDataEntity entity) {
        neiHanBaseAdapter.loadingFinish();
        isLoading = false;
        neiHanBaseAdapter.addItems(entity.getData());
    }
}
