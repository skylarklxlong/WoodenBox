package online.himakeit.skylark.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.victor.loading.rotate.RotateLoading;

import org.xutils.ex.DbException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
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
    @Bind(R.id.iv_fail)
    ImageView iv_fail;

    TestAdapter mAdapter;

    String mTtype = "-101";

    public TestFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public TestFragment(String type) {
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

    public static TestFragment newInstance(String type) {
        TestFragment testFragment = new TestFragment(type);
        return testFragment;
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
                    iv_fail.setVisibility(View.GONE);
                    mAdapter.loadFirst();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        mRecyclerView.setOnPauseListenerParams(false, true);
        try {
            mAdapter = new TestAdapter(mTtype, getActivity(), mRecyclerView, this);
        } catch (DbException e) {
            e.printStackTrace();
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                /*if (mTtype.equals("-301") || mTtype.equals("-104")){
                    JZVideoPlayer jzvd = (JZVideoPlayer) view.findViewById(R.id.videoplayer);
                    if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                        JZVideoPlayer.releaseAllVideos();
                    }
                }*/

                JZVideoPlayer jzvd = (JZVideoPlayer) view.findViewById(R.id.videoplayer);
                if (jzvd != null) {
                    if (jzvd.dataSourceObjects != null) {
                        if (JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                            JZVideoPlayer.releaseAllVideos();
                        }
                    }
                }
            }
        });
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
        iv_fail.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
