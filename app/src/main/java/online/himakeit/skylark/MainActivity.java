package online.himakeit.skylark;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.maning.updatelibrary.InstallUtils;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import online.himakeit.skylark.activity.AboutActivity;
import online.himakeit.skylark.activity.GankMeiZhiAcitvity;
import online.himakeit.skylark.activity.SettingActivity;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.fragment.GankFragment;
import online.himakeit.skylark.fragment.MobFragment;
import online.himakeit.skylark.fragment.NeiHanFragment;
import online.himakeit.skylark.fragment.ReadFragment;
import online.himakeit.skylark.fragment.ToolsFragment;
import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageItem;
import online.himakeit.skylark.presenter.implPresenter.ZuiMeiPresenterImpl;
import online.himakeit.skylark.presenter.implView.IZuiMeiPic;
import online.himakeit.skylark.receiver.JPushLocalBroadcastManager;
import online.himakeit.skylark.util.AlarmManagerUtils;
import online.himakeit.skylark.util.DeviceUtils;
import online.himakeit.skylark.util.DialogUtils;
import online.himakeit.skylark.util.JPushUtil;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.NotifyUtil;
import online.himakeit.skylark.util.PreferencesUtils;
import online.himakeit.skylark.util.StatusBarUtils;
import online.himakeit.skylark.util.Toasts;
/**
 * @author：LiXueLong
 * @date:2018/1/3-15:41
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des：MainActivity
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IZuiMeiPic{

    private static final String TAG = "MainActivity";
    /**
     * FragmentTransaction fragmentTransaction;  FragmentTransaction只能被提交一次，最好不要用全局的
     */
    ArrayList<Fragment> fragmentArrayList;
    public static boolean isForeground = false;
    FragmentManager fragmentManager;
    NeiHanFragment neiHanFragment;
    ReadFragment readFragment;
    GankFragment gankFragment;
    ToolsFragment toolsFragment;
    MobFragment mobFragment;

    Toolbar toolbar;
    NavigationView navigationView;
    ZuiMeiPresenterImpl zuiMeiPresenterImpl;
    private NotifyUtil notifyUtils;
    private MaterialDialog dialogUpdate;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.with(this)
                .setDrawerLayoutContentId(true, R.id.main)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .clearActionBarShadow()
                .init();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LogUtils.e("当前设备系统版本 ： " + Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT);
        DeviceUtils.getAppCanUsedMem(this);

        zuiMeiPresenterImpl = new ZuiMeiPresenterImpl(this, this);
        zuiMeiPresenterImpl.getBackground();
        zuiMeiPresenterImpl.initAppUpdate();

        //下面的那个像邮件的按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(WebActivity.newTntent(MainActivity.this, "http://himakeit.online/", "XueLong"));
            }
        });

        //整体布局
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //右侧划菜单栏
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initfragment();

        toolbarTitle("日报&头条");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_fragment, fragmentArrayList.get(0));
        fragmentTransaction.commit();

        AlarmManagerUtils.register(this);

        registerMessageReceiver();  // used for receive msg JPush

        requestSomePermission();
    }

    private void requestSomePermission() {
        // 申请权限。
        AndPermission.with(MainActivity.this)
                .requestCode(100)
                .permission(android.Manifest.permission.WAKE_LOCK,
                        android.Manifest.permission.VIBRATE,
                        android.Manifest.permission.CHANGE_NETWORK_STATE,
                        android.Manifest.permission.CHANGE_WIFI_STATE,
                        android.Manifest.permission.ACCESS_NETWORK_STATE,
                        android.Manifest.permission.ACCESS_WIFI_STATE,
                        android.Manifest.permission.INTERNET,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_PHONE_STATE
                )
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(MainActivity.this, rationale).show();
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
            if (requestCode == 100) {
                LogUtils.e("权限onSucceed:" + grantedPermissions.toString());
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 100) {
                LogUtils.e("权限onFailed:" + deniedPermissions.toString());
                // 权限申请失败回调。
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(MainActivity.this, 300)
                            .setTitle("权限申请失败")
                            .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                            .setPositiveButton("好，去设置")
                            .show();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        JPushLocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        zuiMeiPresenterImpl.unsubscrible();
        super.onDestroy();
    }

    private void toolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        //使用下面这种方式会使toolbar点击无效
//        toolbar.setTitle(title);
//        setSupportActionBar(toolbar);
    }

    private void initfragment() {
        fragmentManager = getSupportFragmentManager();

        fragmentArrayList = new ArrayList<Fragment>();
        readFragment = new ReadFragment();
        gankFragment = new GankFragment();
        neiHanFragment = new NeiHanFragment();
        mobFragment = new MobFragment();
        toolsFragment = new ToolsFragment();

        fragmentArrayList.add(readFragment);
        fragmentArrayList.add(gankFragment);
        fragmentArrayList.add(neiHanFragment);
        fragmentArrayList.add(mobFragment);
        fragmentArrayList.add(toolsFragment);
    }

    private int preIndex = 0;

    public void loadfragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentArrayList.get(index);
        Fragment pfragment = fragmentArrayList.get(preIndex);

        if (!fragment.isAdded()) {
            fragmentTransaction.hide(pfragment).add(R.id.content_fragment, fragment);
        } else {
            fragmentTransaction.hide(pfragment).show(fragment);
        }
        fragmentTransaction.commit();
        preIndex = index;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再点一次，退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //右上角菜单键
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /**
         * 处理每日提醒
         */
        MenuItem item = menu.findItem(R.id.action_notification);
        PreferencesUtils preferencesUtils = new PreferencesUtils(this);
        item.setChecked(preferencesUtils.getBoolean("每天提醒", false));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent intent = WebActivity.newTntent(this, "https://readhub.me", "Readhub");
                startActivity(intent);
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_notification:
                boolean isChecked = !item.isChecked();
                item.setChecked(isChecked);
                PreferencesUtils preferencesUtils = new PreferencesUtils(this);
                preferencesUtils.saveBoolean("每天提醒", isChecked);
                Toasts.showShort(isChecked ? "已经开启提醒" : "已经关闭提醒");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //右侧划 菜单栏
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_camera:
                loadfragment(0);
                toolbarTitle("日报&头条");
                break;
            case R.id.nav_gallery:
                loadfragment(1);
                toolbarTitle("干货集中营");
                break;
            case R.id.nav_slideshow:
                loadfragment(2);
                toolbarTitle("内涵段子");
//                Toasts.showShort("正在努力开发中。。。");
                break;
            case R.id.nav_manage:
                loadfragment(3);
                toolbarTitle("工具集合");
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingActivity.class));
//                loadfragment(4);
//                toolbarTitle("test");
                break;
            case R.id.nav_xuelong:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void updateZuiMeiPic(final ZuiMeiImageItem zuiMeiImageItem) {
        if (zuiMeiImageItem.getImageUrl() != null) {
            View headerView = navigationView.getHeaderView(0);
            ImageView imageView = (ImageView) headerView.findViewById(R.id.imageview);
            TextView textView = (TextView) headerView.findViewById(R.id.tv_msg);
            textView.setText(zuiMeiImageItem.getDescription());
            Picasso.with(MainActivity.this).load(Config.ZUIMEI_PIC_BASE_URL + zuiMeiImageItem.getImageUrl()
                    + "?imageMogr/v2/auto-orient/thumbnail/240x160/quality/100")
                    .resize(headerView.getWidth(), headerView.getHeight())
                    .into(imageView);
            LogUtils.e(TAG, "updateZuiMeiPic Picasso load pic finish!");

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, GankMeiZhiAcitvity.class);
                    intent.putExtra("imageUrl", Config.ZUIMEI_PIC_BASE_URL + zuiMeiImageItem.getImageUrl());
                    startActivity(intent);
                }
            });
        }

        /*File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File directory = new File(externalStorageDirectory, "Skylark");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (new File(directory, "/bg.jpg").exists()) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), directory + "/bg.jpg");
            linearLayout.setBackground(bitmapDrawable);
        }*/

    }

    @Override
    public void showToast(String msg) {
        Toasts.showShort(msg);
    }

    @Override
    public void showAppUpdateDialog(final AppUpdateInfo appUpdateInfo) {
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
                        showDownloadDialog(appUpdateInfo);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private void showDownloadDialog(AppUpdateInfo appUpdateInfo) {
        dialogUpdate = new MaterialDialog.Builder(MainActivity.this)
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

        new InstallUtils(MainActivity.this, appUpdateInfo.getInstall_url(), "一个木匣_" + appUpdateInfo.getVersionShort(), new InstallUtils.DownloadCallBack() {
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
                InstallUtils.installAPK(MainActivity.this, path, getPackageName() + ".fileProvider", new InstallUtils.InstallCallBack() {
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

    public interface LoadingMore {
        void loadingStart();

        void loadingFinish();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        JPushLocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!JPushUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        /*if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(android.view.View.VISIBLE);
        }*/
    }
}
