package online.himakeit.skylark;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import online.himakeit.skylark.activity.AboutActivity;
import online.himakeit.skylark.activity.GankMeiZhiAcitvity;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.fragment.MobFragment;
import online.himakeit.skylark.fragment.ToolsFragment;
import online.himakeit.skylark.fragment.GankFragment;
import online.himakeit.skylark.fragment.KCFragment;
import online.himakeit.skylark.fragment.ReadFragment;
import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.zuimei.ZuiMeiImageItem;
import online.himakeit.skylark.presenter.implPresenter.ZuiMeiPresenterImpl;
import online.himakeit.skylark.presenter.implView.IZuiMeiPic;
import online.himakeit.skylark.receiver.JPushLocalBroadcastManager;
import online.himakeit.skylark.util.AlarmManagerUtils;
import online.himakeit.skylark.util.DeviceUtils;
import online.himakeit.skylark.util.JPushUtil;
import online.himakeit.skylark.util.LogUtils;
import online.himakeit.skylark.util.PreferencesUtils;
import online.himakeit.skylark.util.Toasts;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IZuiMeiPic {

    private static final String TAG = "MainActivity";
    public static boolean isForeground = false;

    FragmentManager fragmentManager;
    //FragmentTransaction fragmentTransaction;  FragmentTransaction只能被提交一次，最好不要用全局的
    ArrayList<Fragment> fragmentArrayList;
    KCFragment kCFragment;
    ReadFragment readFragment;
    GankFragment gankFragment;
    ToolsFragment toolsFragment;
    MobFragment mobFragment;

    Toolbar toolbar;
    NavigationView navigationView;
    ZuiMeiPresenterImpl zuiMeiPresenterImpl;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        int flag = getWindow().getDecorView().getSystemUiVisibility();
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        LogUtils.e(flag + "----");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LogUtils.e("当前设备系统版本 ： " + Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT);
        DeviceUtils.getAppCanUsedMem(this);

        zuiMeiPresenterImpl = new ZuiMeiPresenterImpl(this, this);
        zuiMeiPresenterImpl.getBackground();

        //下面的那个像邮件的按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        toolbarTitle("ReadHub");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_fragment, fragmentArrayList.get(0));
        fragmentTransaction.commit();

        AlarmManagerUtils.register(this);

        requestSomePermission();

        registerMessageReceiver();  // used for receive msg JPush

    }

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
        super.onDestroy();
    }

    private void requestSomePermission() {
        // 申请权限。
        AndPermission.with(MainActivity.this)
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
        mobFragment = new MobFragment();
        toolsFragment = new ToolsFragment();
        kCFragment = new KCFragment();

        fragmentArrayList.add(readFragment);
        fragmentArrayList.add(gankFragment);
        fragmentArrayList.add(mobFragment);
        fragmentArrayList.add(kCFragment);
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
//        fragmentTransaction.replace(R.id.content_fragment,fragmentArrayList.get(index));
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
        item.setChecked(preferencesUtils.getBoolean("每天提醒", true));
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
                toolbarTitle("ReadHub");
                break;
            case R.id.nav_gallery:
                loadfragment(1);
                toolbarTitle("Gank.io");
                break;
            case R.id.nav_slideshow:
                loadfragment(2);
                toolbarTitle("工具集合");
                break;
            case R.id.nav_manage:
                loadfragment(3);
                toolbarTitle("00004");
                break;
            case R.id.nav_share:
                loadfragment(4);
                toolbarTitle("00005");
                break;
            case R.id.nav_send:
                loadfragment(4);
                toolbarTitle("00006");
                break;
            case R.id.nav_settings:

                break;
            case R.id.nav_about:

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
                    + "?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100")
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
