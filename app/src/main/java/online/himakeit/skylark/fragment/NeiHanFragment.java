package online.himakeit.skylark.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.ReadViewPagerAdapter;
import online.himakeit.skylark.common.BaseFragment;


/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanFragment extends BaseFragment {

    @Bind(R.id.tab_layout_neihan)
    TabLayout tabLayout;
    @Bind(R.id.view_pager_neihan)
    ViewPager viewPager;

    ReadViewPagerAdapter adapter;
    NeiHanTJFragment neiHanTJFragment;
    NeiHanBaseFragment neiHanDZFragment;
    NeiHanBaseFragment neiHanPicFragment;
    NeiHanBaseFragment neiHanDYXFragment;
    NeiHanBaseFragment neiHanVideoFragment;
    TestFragment tjFragment;
    TestFragment dzFragment;
    TestFragment tpFragment;
    TestFragment dyFragment;
    TestFragment spFragment;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_neihan, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        String[] mTitleArray = new String[]{"推荐", "段子", "图片", "段友秀", "视频"};

        // TODO: 2017/9/14 type: 推荐-101/段子-102/图片-103/段友秀-301/视频-104
        List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
        tjFragment = TestFragment.newInstance("推荐");
        dzFragment = TestFragment.newInstance("段子");
        tpFragment = TestFragment.newInstance("图片");
        dyFragment = TestFragment.newInstance("段友秀");
        spFragment = TestFragment.newInstance("视频");
//        neiHanTJFragment = new NeiHanTJFragment();
//        neiHanDZFragment = new NeiHanBaseFragment().newInstance("段子");
//        neiHanPicFragment = new NeiHanBaseFragment().newInstance("图片");
//        neiHanDYXFragment = new NeiHanBaseFragment().newInstance("段友秀");
//        neiHanVideoFragment = new NeiHanBaseFragment().newInstance("视频");
        mFragmentList.add(tjFragment);
        mFragmentList.add(dzFragment);
        mFragmentList.add(tpFragment);
        mFragmentList.add(dyFragment);
        mFragmentList.add(spFragment);
//        mFragmentList.add(neiHanTJFragment);
//        mFragmentList.add(neiHanDZFragment);
//        mFragmentList.add(neiHanPicFragment);
//        mFragmentList.add(neiHanDYXFragment);
//        mFragmentList.add(neiHanVideoFragment);

        /**
         * 在Fragment中的ViewPager中添加Fragment时，获取FragmentManager不能使用getFragmentManager，
         * 否则会报如下错误：
         * java.lang.IllegalStateException: FragmentManager is already executing transactions
         * 解决方法：http://blog.csdn.net/a1274624994/article/details/53575976
         * 使用getChildFragmentManager()
         */
        adapter = new ReadViewPagerAdapter(getChildFragmentManager(), mTitleArray, mFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(7);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
