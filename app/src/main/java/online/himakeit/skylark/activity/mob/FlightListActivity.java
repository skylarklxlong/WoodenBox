package online.himakeit.skylark.activity.mob;

import android.graphics.Color;
import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobFlightListRecycleAdapter;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.model.mob.MobFlightEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/6 16:02
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class FlightListActivity extends BaseActivity {

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
    private ArrayList<MobFlightEntity> mDatas;
    private MobFlightListRecycleAdapter flightListRecycleAdapter;

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_flight_list);
        ButterKnife.bind(this);

        initIntent();
        initToolBar();
        initRecyclerView();
        //默认排序
        sortStartTime();
        initAdapter();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void initAdapter() {
        if (flightListRecycleAdapter == null) {
            flightListRecycleAdapter = new MobFlightListRecycleAdapter(this, mDatas);
            recyclerView.setAdapter(flightListRecycleAdapter);
        } else {
            flightListRecycleAdapter.updateDatas(mDatas);
        }
    }

    private void sortStartTime() {
        Collections.sort(mDatas, new Comparator<MobFlightEntity>() {
            @Override
            public int compare(MobFlightEntity mobFlightEntity01, MobFlightEntity mobFlightEntity02) {
                String startTime01 = mobFlightEntity01.getPlanTime();
                String startTime02 = mobFlightEntity02.getPlanTime();

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
        Collections.sort(mDatas, new Comparator<MobFlightEntity>() {
            @Override
            public int compare(MobFlightEntity mobFlightEntity01, MobFlightEntity mobFlightEntity02) {
                String startTime01 = mobFlightEntity01.getPlanArriveTime();
                String startTime02 = mobFlightEntity02.getPlanArriveTime();

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

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.parseColor("#FFCCCCCC"))
                .build());

    }

    @OnClick({R.id.tv_back, R.id.btn_sort_start_time, R.id.btn_sort_end_time})
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
        mDatas = (ArrayList<MobFlightEntity>) getIntent().getSerializableExtra("IntentDatas");
    }
}
