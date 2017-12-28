package online.himakeit.skylark.activity.mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobFlightEntity;
import online.himakeit.skylark.util.KeyboardUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:20
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class FlightActivity extends OtherBaseActivity {

    @Bind(R.id.rl_mob_flight_root)
    RelativeLayout rl_root;
    @Bind(R.id.ll_query)
    LinearLayout ll_query;
    @Bind(R.id.tv_start_name)
    TextView tv_start_name;
    @Bind(R.id.tv_end_name)
    TextView tv_end_name;
    @Bind(R.id.tv_time_query)
    TextView tv_time_query;
    @Bind(R.id.et_start_name)
    EditText et_start_name;
    @Bind(R.id.et_end_name)
    EditText et_end_name;
    @Bind(R.id.iv_swap)
    ImageView iv_swap;

    private SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
    private Animation animation180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_flight);

        ButterKnife.bind(this);

        initAnimation();

        initData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void initData() {
        Date date = new Date();
        tv_time_query.setText(sdf.format(date));
    }

    private void initAnimation() {
        animation180 = AnimationUtils.loadAnimation(this, R.anim.rotate_180);
    }

    @OnClick({R.id.btn_back, R.id.rl_mob_flight_root, R.id.iv_swap, R.id.btn_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.rl_mob_flight_root:
                KeyboardUtils.hideSoftInput(this);
                break;
            case R.id.iv_swap:

                LinearInterpolator linearInterpolator = new LinearInterpolator();
                animation180.setInterpolator(linearInterpolator);
                iv_swap.setAnimation(animation180);
                iv_swap.startAnimation(animation180);
                animation180.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        et_start_name.setVisibility(View.GONE);
                        et_end_name.setVisibility(View.GONE);
                        tv_start_name.setVisibility(View.VISIBLE);
                        tv_end_name.setVisibility(View.VISIBLE);
                        String startName = et_start_name.getText().toString();
                        String endName = et_end_name.getText().toString();
                        tv_start_name.setText(startName);
                        tv_end_name.setText(endName);
                        et_start_name.setText(endName);
                        et_end_name.setText(startName);
                        //开始动画
                        int[] location = new int[2];
                        et_end_name.getLocationInWindow(location);
                        int translateX = location[0] + et_end_name.getWidth() / 2;

                        TranslateAnimation translateAnimation = new TranslateAnimation(0, translateX, 0, 0);
                        translateAnimation.setDuration(600);
                        tv_start_name.setAnimation(translateAnimation);
                        tv_start_name.startAnimation(translateAnimation);

                        TranslateAnimation translateAnimation2 = new TranslateAnimation(0, -translateX, 0, 0);
                        translateAnimation2.setDuration(600);
                        tv_end_name.setAnimation(translateAnimation2);
                        tv_end_name.startAnimation(translateAnimation2);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        et_start_name.setVisibility(View.VISIBLE);
                        et_end_name.setVisibility(View.VISIBLE);
                        tv_start_name.setVisibility(View.GONE);
                        tv_end_name.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                break;
            case R.id.btn_query:
                KeyboardUtils.hideSoftInput(this);
                //获取出发城市
                final String startName = et_start_name.getText().toString();

                //获取到达城市
                final String endName = et_end_name.getText().toString();

                //判断
                if (TextUtils.isEmpty(startName)) {
                    Snackbar.make(tv_end_name, "出发城市不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(endName)) {
                    Snackbar.make(tv_end_name, "到达城市不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                showProgressDialog("正在查询...");

                MobApiImpl.queryMobFlightLineList(startName, endName, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {

                    }

                    @Override
                    public void onSuccessList(int what, List results) {
                        dissmissProgressDialog();
                        ArrayList<MobFlightEntity> mDatas = (ArrayList<MobFlightEntity>) results;
                        if (mDatas != null && mDatas.size() > 0) {
                            Intent intent = new Intent(mContext, FlightListActivity.class);
                            intent.putExtra("IntentTitle", startName + "-" + endName);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("IntentDatas", mDatas);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(tv_end_name, result, Snackbar.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}
