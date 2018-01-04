package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobLotteryDetailsRecyclerAdapter;
import online.himakeit.skylark.adapter.MobLotteryNumberRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobLotteryEntity;

/**
 * @author：LiXueLong
 * @date：2018/1/3
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class LotteryDetailActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_period)
    TextView tv_period;
    @Bind(R.id.tv_award_time)
    TextView tv_award_time;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_sales)
    TextView tv_sales;
    @Bind(R.id.tv_pool)
    TextView tv_pool;
    @Bind(R.id.recycler_lottery_detail)
    RecyclerView recyclerViewDetail;


    public static final String IntentKey_LotteryName = "IntentKey_LotteryName";
    private String lotteryName = "";
    private MobLotteryEntity mMobLotteryEntity = new MobLotteryEntity();
    private MobLotteryNumberRecyclerAdapter numberRecyclerAdapter;
    private MobLotteryDetailsRecyclerAdapter detailsRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_lottery_detail);
        ButterKnife.bind(this);

        init();
        loadData();
    }

    private void loadData() {
        showProgressDialog();
        MobApiImpl.queryMoblotteryDetail(lotteryName, 0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                mMobLotteryEntity = (MobLotteryEntity) result;

                tv_period.setText("第" + mMobLotteryEntity.getPeriod() + "期");
                tv_award_time.setText("开奖时间: " + mMobLotteryEntity.getAwardDateTime());
                tv_pool.setText(String.valueOf(mMobLotteryEntity.getPool()));
                tv_sales.setText(String.valueOf(mMobLotteryEntity.getSales()));

                //开奖号码
                initNumberAdpater();

                //中奖信息
                initDetailsAdpater();
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                Snackbar.make(recyclerView, result, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initDetailsAdpater() {
        List<MobLotteryEntity.MobLotteryDetailsBean> lotteryDetails = mMobLotteryEntity.getLotteryDetails();
        if (lotteryDetails != null && lotteryDetails.size() > 0) {
            detailsRecyclerAdapter = new MobLotteryDetailsRecyclerAdapter(this, lotteryDetails);
            recyclerViewDetail.setAdapter(detailsRecyclerAdapter);
        }
    }

    private void initNumberAdpater() {
        List<String> lotteryNumber = mMobLotteryEntity.getLotteryNumber();
        if (lotteryNumber != null && lotteryNumber.size() > 0) {
            numberRecyclerAdapter = new MobLotteryNumberRecyclerAdapter(this, lotteryNumber);
            recyclerView.setAdapter(numberRecyclerAdapter);
        }
    }

    private void init() {
        lotteryName = getIntent().getStringExtra(IntentKey_LotteryName);
        tv_title.setText(lotteryName);

        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerHorizontal);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManagerVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDetail.setLayoutManager(layoutManagerVertical);
        recyclerViewDetail.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.tv_back)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
