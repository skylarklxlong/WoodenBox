package online.himakeit.skylark.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
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

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_neihan, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
