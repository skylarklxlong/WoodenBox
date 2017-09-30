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
public class ReadFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    ReadViewPagerAdapter readViewPagerAdapter;
    ZhiHuFragment zhiHuFragment;
    TopNewsFragment topNewsFragment;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_read, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {

        String[] mTitleArray = new String[]{"知乎","头条"};

        List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
        zhiHuFragment = new ZhiHuFragment();
        topNewsFragment = new TopNewsFragment();
        mFragmentList.add(zhiHuFragment);
        mFragmentList.add(topNewsFragment);

        readViewPagerAdapter = new ReadViewPagerAdapter(getFragmentManager(),mTitleArray,mFragmentList);
        viewPager.setAdapter(readViewPagerAdapter);
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
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
