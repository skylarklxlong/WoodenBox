package online.himakeit.skylark.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobAdapter;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.widget.WrapContentLinearLayoutManager;

/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobFragment extends BaseFragment{

    View view;

    @Bind(R.id.recycle_mob)
    RecyclerView recycler_mob;

    MobAdapter mobAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> mDatas = new ArrayList<>();

    @Override
    public View initViews() {
        view = View.inflate(mActivity, R.layout.fragment_mob, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        mDatas.add("生活常用");
        mDatas.add("金融基金");
        mDatas.add("休闲旅游");
        mDatas.add("便民服务");
        mobAdapter = new MobAdapter(mActivity,mDatas);

        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        recycler_mob.setLayoutManager(linearLayoutManager);
        recycler_mob.setItemAnimator(new DefaultItemAnimator());
        recycler_mob.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        recycler_mob.setAdapter(mobAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
