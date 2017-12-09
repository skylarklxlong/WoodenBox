package online.himakeit.skylark.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.maning.updatelibrary.InstallUtils;
import com.yanzhenjie.permission.AndPermission;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.presenter.implPresenter.SettingPresenterImpl;
import online.himakeit.skylark.presenter.implView.ISettingView;
import online.himakeit.skylark.util.DialogUtils;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.NotifyUtil;
import online.himakeit.skylark.util.Toasts;
import online.himakeit.skylark.view.SettingItemView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/7 14:14
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SettingActivity extends BaseActivity implements ISettingView {

    private static final String TAG = "FxcSalaryActivity";
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_back)
    ImageView tv_back;

    @Bind(R.id.setting_item_clean_cache)
    SettingItemView clean_cache;
    @Bind(R.id.setting_item_check_version)
    SettingItemView check_version;
    @Bind(R.id.setting_item_open_frame)
    SettingItemView open_frame;
    @Bind(R.id.setting_item_support)
    SettingItemView support;

    SettingPresenterImpl settingPresenter;
    private NotifyUtil notifyUtils;
    private MaterialDialog dialogUpdate;
    private AppUpdateInfo appUpdateInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initView();

        settingPresenter = new SettingPresenterImpl(this, this);

        settingPresenter.initCache();
        settingPresenter.initAppUpdateState();
    }

    private void initView() {
        tv_title.setText("设置");
        support.setTitleColor(getResources().getColor(R.color.colorAccent));
    }

    @OnClick({R.id.tv_back, R.id.setting_item_check_version, R.id.setting_item_clean_cache,
            R.id.setting_item_open_frame, R.id.setting_item_support})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;

            case R.id.setting_item_clean_cache:
                showCacheDialog();
                break;

            case R.id.setting_item_check_version:
                settingPresenter.checkAppUpdate();
                break;
            case R.id.setting_item_open_frame:
//                NavigatorUtils.toOpenFrameUI(this);
                break;
            case R.id.setting_item_support:
//                NavigatorUtils.toSupportPayUI(this);
                break;
        }
    }

    private void showCacheDialog() {
        DialogUtils.showMyDialog(this, "缓存清理", "确定要清除图片的缓存吗？", "确定", "取消", new DialogUtils.OnDialogClickListener() {

            @Override
            public void onConfirm() {
                // 先判断是否有权限。
                if (AndPermission.hasPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 有权限，直接do anything.
                    settingPresenter.cleanCache();
                } else {
                    // 申请权限。
                    AndPermission.with(SettingActivity.this)
                            .requestCode(101)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .send();
                }
            }

            @Override
            public void onCancel() {

            }
        });

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void setCacheSize(String cacheSize) {
        clean_cache.setRightText(cacheSize);
    }

    @Override
    public void setAppUpdateState(boolean flag) {
        check_version.setRedDot(flag);
    }

    @Override
    public void showAppUpdateDialog(AppUpdateInfo appUpdateInfo) {
        this.appUpdateInfo = appUpdateInfo;
        String title = "检测到新版本:V" + appUpdateInfo.getVersionShort();
        Double appSize = Double.parseDouble(appUpdateInfo.getBinary().getFsize() + "") / 1024 / 1024;
        DecimalFormat df = new DecimalFormat(".##");
        String resultSize = df.format(appSize) + "M";
        boolean isWifi = NetUtils.isWifiConnected(this);
        String content = appUpdateInfo.getChangelog() +
                "\n\n新版大小：" + resultSize +
                "\n当前网络：" + (isWifi ? "wifi" : "非wifi环境(注意)");

        DialogUtils.showMyDialog(this,
                title, content, "立马更新", "稍后更新",
                new DialogUtils.OnDialogClickListener() {
                    @Override
                    public void onConfirm() {
                        //更新版本
                        showDownloadDialog(SettingActivity.this.appUpdateInfo);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private void showDownloadDialog(AppUpdateInfo appUpdateInfo) {
        dialogUpdate = new MaterialDialog.Builder(SettingActivity.this)
                .title("正在下载最新版本")
                .content("请稍等")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .progress(false, 100, false)
                .negativeText("后台下载")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startNotifyProgress();
                    }
                })
                .show();

        new InstallUtils(SettingActivity.this, appUpdateInfo.getInstall_url(), "一个木匣_" + appUpdateInfo.getVersionShort(), new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                LogUtils.i("installAPK-----onStart");
                if (dialogUpdate != null) {
                    dialogUpdate.setProgress(0);
                }
            }

            @Override
            public void onComplete(String path) {
                LogUtils.i("installAPK----onComplete:" + path);
                /**
                 * 安装APK工具类
                 * @param context       上下文
                 * @param filePath      文件路径
                 * @param authorities   ---------Manifest中配置provider的authorities字段---------
                 * @param callBack      安装界面成功调起的回调
                 */
                InstallUtils.installAPK(SettingActivity.this, path, getPackageName() + ".fileProvider", new InstallUtils.InstallCallBack() {
                    @Override
                    public void onSuccess() {
                        Toasts.showShort("正在安装程序");
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toasts.showShort("安装失败:" + e.toString());
                    }
                });
                if (dialogUpdate != null && dialogUpdate.isShowing()) {
                    dialogUpdate.dismiss();
                }
                if (notifyUtils != null) {
                    notifyUtils.setNotifyProgressComplete();
                    notifyUtils.clear();
                }
            }

            @Override
            public void onLoading(long total, long current) {
                LogUtils.i("installAPK-----onLoading:-----total:" + total + ",current:" + current);
                int currentProgress = (int) (current * 100 / total);
                if (dialogUpdate != null) {
                    dialogUpdate.setProgress(currentProgress);
                }
                if (notifyUtils != null) {
                    notifyUtils.setNotifyProgress(100, currentProgress, false);
                }
            }

            @Override
            public void onFail(Exception e) {
                if (dialogUpdate != null && dialogUpdate.isShowing()) {
                    dialogUpdate.dismiss();
                }
                if (notifyUtils != null) {
                    notifyUtils.clear();
                }
            }
        }).downloadAPK();

    }

    /**
     * 开启通知栏
     */
    private void startNotifyProgress() {
        //设置想要展示的数据内容
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.zero;
        String ticker = "正在下载木匣更新包...";
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(this, 0);
        notifyUtils.notify_progress(rightPendIntent, smallIcon, ticker, "木匣 下载", "正在下载中...", false, false, false);
    }

    @Override
    public void showBasesProgressSuccess(String msg) {
        showProgressSuccess(msg);
    }

    @Override
    public void showToast(String msg) {
        Snackbar.make(tv_title, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
