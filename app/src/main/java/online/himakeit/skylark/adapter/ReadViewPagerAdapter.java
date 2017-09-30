package online.himakeit.skylark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import online.himakeit.skylark.common.BaseFragment;


/**
 * Created by：LiXueLong 李雪龙 on 2017/8/22 15:32
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ReadViewPagerAdapter extends FragmentPagerAdapter {

    String[] mTitleArray;
    List<BaseFragment> mFragmentList;

    public ReadViewPagerAdapter(FragmentManager fm, String[] mTitleArray, List<BaseFragment> mFragmentList) {
        super(fm);
        this.mTitleArray = mTitleArray;
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTitleArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArray[position];
    }

    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }*/
}
