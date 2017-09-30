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
public class GankFragment extends BaseFragment {

    @Bind(R.id.tab_layout_gank)
    TabLayout tabLayout;
    @Bind(R.id.view_pager_gank)
    ViewPager viewPager;

    ReadViewPagerAdapter adapter;

    GankMeiZhiFragment gankMeiZhiFragment;
    GankBaseFragment gankAndroidFragment;
    GankBaseFragment gankIOSFragment;
    GankBaseFragment gankVideoFragment;
    GankBaseFragment gankExpandFragment;
    GankBaseFragment gankWebFragment;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_gank,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        String[] mTitleArray = new String[]{"妹纸","安卓","IOS","视频","拓展","前端"};

        // TODO: 2017/9/14 type: 福利/Android/iOS/休息视频/拓展资源/前端
        List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
        gankMeiZhiFragment = new GankMeiZhiFragment();
        gankAndroidFragment = new GankBaseFragment().newInstance("Android");
        gankIOSFragment = new GankBaseFragment().newInstance("iOS");
        gankVideoFragment = new GankBaseFragment().newInstance("休息视频");
        gankExpandFragment = new GankBaseFragment().newInstance("拓展资源");
        gankWebFragment = new GankBaseFragment().newInstance("前端");
        mFragmentList.add(gankMeiZhiFragment);
        mFragmentList.add(gankAndroidFragment);
        mFragmentList.add(gankIOSFragment);
        mFragmentList.add(gankVideoFragment);
        mFragmentList.add(gankExpandFragment);
        mFragmentList.add(gankWebFragment);

        /**
         * 在Fragment中的ViewPager中添加Fragment时，获取FragmentManager不能使用getFragmentManager，
         * 否则会报如下错误：
         * java.lang.IllegalStateException: FragmentManager is already executing transactions
         * 解决方法：http://blog.csdn.net/a1274624994/article/details/53575976
         * 使用getChildFragmentManager()
         */
        adapter = new ReadViewPagerAdapter(getChildFragmentManager(),mTitleArray,mFragmentList);
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
