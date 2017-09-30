package online.himakeit.skylark.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public abstract class BaseFragment extends Fragment {

    public Activity mActivity;
    //fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }
    //处理fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews();

    }
    //依附的activity创建完成
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    //子类必须实现初始化布局的方法
    public abstract View initViews();

    //初始化数据，可以不实现
    public abstract void initData();

}
