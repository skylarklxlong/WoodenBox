package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maning.calendarlibrary.MNCalendar;
import com.maning.calendarlibrary.listeners.OnCalendarItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobHistoryTodayRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.mob.MobHistoryTodayEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class HistoryTodayActivity extends BaseActivity {

    /**
     * toolbar
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    /**
     * 其他UI
     */
    @Bind(R.id.recycler_historytoday)
    RecyclerView recyclerView;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.mnCalendar)
    MNCalendar mnCalendar;
    @Bind(R.id.calendar_bg)
    RelativeLayout calendar_bg;

    private ArrayList<MobHistoryTodayEntity> mDatas;

    private Date currentDate = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日");
    private MobHistoryTodayRecyclerAdapter historyTodayRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_history_today);
        ButterKnife.bind(this);

        initMyToolBar();

        initRecyclerView();

        initCalendar();

        queryData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void initMyToolBar() {
        tv_title.setText("历史上的今天");
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initCalendar() {
        mnCalendar.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {
            @Override
            public void onClick(Date date) {
                currentDate = date;
                calendar_bg.setVisibility(View.GONE);
                queryData();
            }

            @Override
            public void onLongClick(Date date) {

            }
        });
    }

    private void queryData() {
        showProgressDialog("查询中...");

        String timeString = sdf.format(currentDate);
        String timeString2 = sdf2.format(currentDate);
        tv_time.setText(timeString2);

        MobApiImpl.queryMobHistory(timeString, 0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                dissmissProgressDialog();
                mDatas = (ArrayList<MobHistoryTodayEntity>) results;
                initAdapter();
            }

            @Override
            public void onFail(int what, String result) {
                Snackbar.make(tv_back, result, Snackbar.LENGTH_SHORT).show();
                dissmissProgressDialog();
            }
        });
    }

    private void initAdapter() {
        if (historyTodayRecyclerAdapter == null) {
            historyTodayRecyclerAdapter = new MobHistoryTodayRecyclerAdapter(this, mDatas);
            recyclerView.setAdapter(historyTodayRecyclerAdapter);
        } else {
            historyTodayRecyclerAdapter.upddateDatas(mDatas);
        }

    }

    @OnClick({R.id.tv_back, R.id.tv_time, R.id.calendar_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_time:
                calendar_bg.setVisibility(View.VISIBLE);
                break;
            case R.id.calendar_bg:
                calendar_bg.setVisibility(View.GONE);
                break;
        }
    }
}
