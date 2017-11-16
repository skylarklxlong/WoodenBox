package online.himakeit.skylark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.ZuiMeiApiImpl;
import online.himakeit.skylark.common.BaseActivityForFullScreen;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageItem;
import online.himakeit.skylark.util.LogUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/31 18:51
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SplashActivity extends BaseActivityForFullScreen{

    private static final String TAG = "SplashActivity";

    @Bind(R.id.iv_splash)
    ImageView iv_splash;
    @Bind(R.id.tv_version_splash)
    TextView tv_version;
    @Bind(R.id.tv_desc_splash)
    TextView tv_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);



        tv_version.setText(String.valueOf("V " + AppContext.getVersionName()));
        ZuiMeiApiImpl.getZuiMeiPic(0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                LogUtils.show(((ZuiMeiImageItem)(results.get(0))).getImageUrl() + "");
                /*Picasso.with(SplashActivity.this)
                        .load(Config.ZUIMEI_PIC_BASE_URL + ((ZuiMeiImageItem)(results.get(0))).getImageUrl()
                                + "?imageMogr/v2/auto-orient/thumbnail/1024x768/quality/100")
                        .resize(iv_splash.getWidth(),iv_splash.getHeight())
                        .into(iv_splash);*/

                tv_desc.setText(((ZuiMeiImageItem)(results.get(0))).getDescription());
                LogUtils.show(((ZuiMeiImageItem)(results.get(0))).getDescription() + "");
            }

            @Override
            public void onFail(int what, String result) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        },2000);

    }
}
