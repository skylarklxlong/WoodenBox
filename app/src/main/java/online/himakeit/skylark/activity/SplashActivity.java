package online.himakeit.skylark.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.ZuiMeiApiImpl;
import online.himakeit.skylark.common.BaseActivityForFullScreen;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.Config;
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

        requestSomePermission();

        tv_version.setText(String.valueOf("V " + AppContext.getVersionName()));
        ZuiMeiApiImpl.getZuiMeiPic(0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                LogUtils.show(((ZuiMeiImageItem)(results.get(0))).getImageUrl() + "");
                Picasso.with(SplashActivity.this)
                        .load(Config.ZUIMEI_PIC_BASE_URL + ((ZuiMeiImageItem)(results.get(0))).getImageUrl()
                                + "?imageMogr/v2/auto-orient/thumbnail/1024x768/quality/100")
                        .resize(iv_splash.getWidth(),iv_splash.getHeight())
                        .into(iv_splash);

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
        },4000);

    }

    private void requestSomePermission() {
        // 申请权限。
        AndPermission.with(SplashActivity.this)
                .requestCode(100)
                .permission(Manifest.permission.WAKE_LOCK,
//                        Manifest.permission.WRITE_SETTINGS,
                        Manifest.permission.VIBRATE,
//                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
//                        Manifest.permission.SYSTEM_ALERT_WINDOW,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(SplashActivity.this, rationale).show();
                    }
                })
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 100) {
                LogUtils.e("权限onSucceed:" + grantedPermissions.toString());
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 100) {
                LogUtils.e("权限onFailed:" + deniedPermissions.toString());
                // 权限申请失败回调。
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(SplashActivity.this, deniedPermissions)) {
                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(SplashActivity.this, 300)
                            .setTitle("权限申请失败")
                            .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                            .setPositiveButton("好，去设置")
                            .show();
                }
            }
        }
    };
}
