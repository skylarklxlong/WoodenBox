package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobCalendarInfoEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:17
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ChineseCalendarActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.iv_lastday)
    ImageView iv_lastday;
    @Bind(R.id.tv_lunar)
    TextView tv_lunar;
    @Bind(R.id.iv_nextday)
    ImageView iv_nextday;
    @Bind(R.id.tv_lunar_year)
    TextView tv_lunar_year;
    @Bind(R.id.tv_suit)
    TextView tv_suit;
    @Bind(R.id.tv_avoid)
    TextView tv_avoid;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_chinese_calendar);
        ButterKnife.bind(this);
        init();

        //查询数据
        queryData(calendar.getTime());
    }

    private void queryData(Date date) {
        showProgressDialog("正在查询。。。");
        String dateString = sdf.format(date);
        MobApiImpl.queryMobCalendarInfo(dateString, 0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                MobCalendarInfoEntity calendarInfoEntity = (MobCalendarInfoEntity) result;
                refreshView(calendarInfoEntity);
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                Snackbar.make(tv_back, result, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshView(MobCalendarInfoEntity calendarInfoEntity) {
        if (calendarInfoEntity != null) {
            tv_date.setText(calendarInfoEntity.getDate());
            tv_lunar.setText(calendarInfoEntity.getLunar());
            tv_lunar_year.setText(calendarInfoEntity.getLunarYear() + " ("
                    + calendarInfoEntity.getZodiac() + ") " + calendarInfoEntity.getWeekday());
            tv_avoid.setText(calendarInfoEntity.getAvoid());
            tv_suit.setText(calendarInfoEntity.getSuit());
        }
    }

    private void init() {
        tv_title.setText("老黄历");
    }

    @OnClick({R.id.tv_back, R.id.iv_lastday, R.id.iv_nextday})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_lastday:
                //上一天
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                queryData(calendar.getTime());
                break;

            case R.id.iv_nextday:
                //下一天
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                queryData(calendar.getTime());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
