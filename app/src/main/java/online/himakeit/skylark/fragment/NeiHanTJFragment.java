package online.himakeit.skylark.fragment;

import android.view.View;

import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;

/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 *
 */
public class NeiHanTJFragment extends BaseFragment {

    private static final String TAG = "NeiHanTJFragment";

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_neihan_tj, null);
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
