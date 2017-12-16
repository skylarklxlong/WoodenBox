package online.himakeit.skylark.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.victor.loading.rotate.RotateLoading;

import org.xutils.ex.DbException;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.TestAdapter;
import online.himakeit.skylark.callback.LoadMoreListener;
import online.himakeit.skylark.callback.LoadResultCallBack;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.view.AutoLoadRecyclerView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/16 9:13
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TestFragment extends BaseFragment implements LoadResultCallBack {

    @Bind(R.id.recyclerview)
    AutoLoadRecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.loading)
    RotateLoading loading;

    TestAdapter mAdapter;

    public TestFragment() {
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_test, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() throws DbException {
                mAdapter.loadNextPage();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mAdapter.loadFirst();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            mAdapter = new TestAdapter(getActivity(), mRecyclerView, this);
        } catch (DbException e) {
            e.printStackTrace();
        }
        mRecyclerView.setAdapter(mAdapter);
        try {
            mAdapter.loadFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        loading.start();
    }

    @Override
    public void onSuccess() {
        loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError() {
        loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
