package online.himakeit.skylark.activity.mob;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobTrainListRecycleAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.mob.MobTrainEntity;
import online.himakeit.skylark.model.mob.MobTrainNoEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/6 16:02
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TrainListActivity extends BaseActivity {

    /**
     * toolbar
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.recycler_train_list)
    RecyclerView recyclerView;

    private String intentTitle;
    private ArrayList<MobTrainEntity> mDatas;
    private MobTrainListRecycleAdapter trainListRecycleAdapter;

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_train_list);
        ButterKnife.bind(this);

        initIntent();
        initToolBar();
        initRecyclerView();
        //默认排序
        sortStartTime();
        initAdapter();
    }

    private void initAdapter() {
        if (trainListRecycleAdapter == null) {
            trainListRecycleAdapter = new MobTrainListRecycleAdapter(this, mDatas);
            recyclerView.setAdapter(trainListRecycleAdapter);
        }else {
            trainListRecycleAdapter.updateDatas(mDatas);
        }
    }

    private void sortStartTime() {
        Collections.sort(mDatas, new Comparator<MobTrainEntity>() {
            @Override
            public int compare(MobTrainEntity mobTrainEntity01, MobTrainEntity mobTrainEntity02) {
                String startTime01 = mobTrainEntity01.getStartTime();
                String startTime02 = mobTrainEntity02.getStartTime();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else if (sdf.parse(startTime01).getTime() < sdf.parse(startTime02).getTime()) {
                        result = -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return result;
            }
        });
    }

    private void sortLiShi() {
        Collections.sort(mDatas, new Comparator<MobTrainEntity>() {
            @Override
            public int compare(MobTrainEntity mobTrainEntity01, MobTrainEntity mobTrainEntity02) {
                String startTime01 = mobTrainEntity01.getLishi();
                String startTime02 = mobTrainEntity02.getLishi();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else if (sdf.parse(startTime01).getTime() < sdf.parse(startTime02).getTime()) {
                        result = -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return result;
            }
        });
    }

    private void sortEndime() {
        Collections.sort(mDatas, new Comparator<MobTrainEntity>() {
            @Override
            public int compare(MobTrainEntity mobTrainEntity01, MobTrainEntity mobTrainEntity02) {
                String startTime01 = mobTrainEntity01.getArriveTime();
                String startTime02 = mobTrainEntity02.getArriveTime();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else {
                        result = -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return result;
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.parseColor("#FFCCCCCC"))
                .build());

    }

    @OnClick({R.id.tv_back, R.id.btn_sort_start_time, R.id.btn_sort_lishi_time, R.id.btn_sort_end_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.btn_sort_start_time:
                //发车时间排序
                sortStartTime();
                //刷新
                initAdapter();
                break;
            case R.id.btn_sort_lishi_time:
                sortLiShi();
                //刷新
                initAdapter();
                break;
            case R.id.btn_sort_end_time:
                sortEndime();
                //刷新
                initAdapter();
                break;
        }
    }

    private void initToolBar() {
        tv_title.setText(intentTitle);
    }

    private void initIntent() {
        intentTitle = getIntent().getStringExtra("IntentTitle");
        mDatas = (ArrayList<MobTrainEntity>) getIntent().getSerializableExtra("IntentDatas");
    }

    public void queryTrainNo(int position) {
        MobTrainEntity mobTrainEntity = mDatas.get(position);
        mobTrainEntity.setShowDetails(!mobTrainEntity.isShowDetails());
        initAdapter();

        final ArrayList<MobTrainNoEntity> trainDetails = mobTrainEntity.getTrainDetails();
        if (trainDetails == null) {
            MobApiImpl.queryMobTrainNo(mobTrainEntity.getStationTrainCode(), position, new MobCallBack() {
                @Override
                public void onSuccess(int what, Object result) {

                }

                @Override
                public void onSuccessList(int what, List results) {
                    ArrayList<MobTrainNoEntity> trainNums = (ArrayList<MobTrainNoEntity>) results;
                    mDatas.get(what).setTrainDetails(trainNums);

                    initAdapter();
                }

                @Override
                public void onFail(int what, String result) {
                    Snackbar.make(tv_back, result, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
