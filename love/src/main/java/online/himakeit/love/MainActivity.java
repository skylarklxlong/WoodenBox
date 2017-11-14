package online.himakeit.love;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.maning.updatelibrary.InstallUtils;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import online.himakeit.love.activity.DecideActivity;
import online.himakeit.love.activity.DeveloperActivity;
import online.himakeit.love.activity.SettingsActivity;
import online.himakeit.love.bean.AppUpdateInfo;
import online.himakeit.love.presenter.implPresenter.MainPresenterImpl;
import online.himakeit.love.presenter.implView.IMainView;
import online.himakeit.love.utils.DialogUtils;
import online.himakeit.love.utils.MyToast;
import online.himakeit.love.utils.NetUtils;
import online.himakeit.love.utils.NotifyUtil;

public class MainActivity extends AppCompatActivity implements IMainView, NavigationView.OnNavigationItemSelectedListener {

    private TextView textView, textView1, textView2, textView4, textView5, textView6;
    private ProgressBar pb_timeprogress;
    private Timer timer;
    private Handler handler;
    private Boolean begined = true;

    private NotifyUtil notifyUtils;
    private MainPresenterImpl mainPresenter;
    private MaterialDialog dialogUpdate;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mainPresenter = new MainPresenterImpl(this, this);
        mainPresenter.initAppUpdate();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (begined == true) {
                    Calendar loveday = new GregorianCalendar(2011, 10, 3);
                    Calendar now = Calendar.getInstance();

                    int day = now.get(Calendar.DAY_OF_MONTH) - loveday.get(Calendar.DAY_OF_MONTH);
                    int month = now.get(Calendar.MONTH) - loveday.get(Calendar.MONTH);
                    int year = now.get(Calendar.YEAR) - loveday.get(Calendar.YEAR);
                    if (day < 0) {
                        month -= 1;
                        now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
                        day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
                    }
                    if (month < 0) {
                        month = (month + 12) % 12;
                        year--;
                    }


                    int tday = day + 1;
                    int tmonth = month + 2;
                    if (tmonth > 12) {
                        tmonth = 1;
                        year = year + 1;
                    }

                    textView1 = (TextView) findViewById(R.id.tw_year);
                    textView1.setText(year + "年");

                    textView2 = (TextView) findViewById(R.id.tw_month);
                    textView2.setText(tmonth + "月");

                    textView5 = (TextView) findViewById(R.id.tw_dayl);
                    textView5.setText(tday + "日");


                    Date date = new Date();
                    Calendar calendar = new GregorianCalendar(2011, 10, 3);
                    //2015.10.8 8:00计时起
                    long x = date.getTime() - calendar.getTimeInMillis();
                    long y = x - 8 * 3600 * 1000;
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    String submittime = format.format(y);
                    long days = x / (1000 * 3600 * 24);
                    long dayss = days + 30;

                    textView = (TextView) findViewById(R.id.tw_day);
                    textView.setText(dayss + "");

                    textView6 = (TextView) findViewById(R.id.tw_hour);
                    textView6.setText(submittime);

                    textView4 = (TextView) findViewById(R.id.tw_sec);
                    long seconds = x / 1000;
                    textView4.setText(seconds + "秒");


                }
            }
        };


        ProgressBar pb_time = (ProgressBar) findViewById(R.id.pb_time);
        pb_time.setIndeterminate(true);

        Date d = new Date();
        Calendar c = new GregorianCalendar(2011, 10, 3);
        //2015.10.8 8:00计时起
        long a = (d.getTime() - c.getTimeInMillis());
        long s = a / (1000 * 3600 * 24);
        long i = a / 1000 - s * 86400;
        long hours = a / (1000 * 60 * 60) - s * 24;
        long mins = a / (1000 * 60) - s * 24 * 60 - hours * 60;
        long secs = a / 1000 - s * 24 * 60 * 60 - hours * 60 * 60 - mins * 60;
        int t = (int) i;
        int tss = (int) secs * 1440;
        pb_timeprogress = (ProgressBar) findViewById(R.id.pb_timeprogress);
        pb_timeprogress.incrementProgressBy(t);

        TextView tw_info = (TextView) findViewById(R.id.tw_sec);
        tw_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("说明").setMessage("计时器从2011.10.3 0:00计时起，每天0点整跳动一次" +
                        "\nX年X月X日   XX：XX：XX为计时的具体时间，精确到秒" +
                        "\n使用了新算法，新算法中只能精确到日，时分秒都不行的~" +
                        "\n最上面天数和最下面时钟是绝对没问题的，主要是中间的那个日期...调了两天了应该不会有毛病...吧..." +
                        "\nSorry我的能力还达不到那么高的水平233就先这样凑合吧"
                ).setCancelable(true)
                        .setPositiveButton("噗....", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dialog.show();
            }
        });

        //申请权限
        requestSomePermission();
    }

    private void requestSomePermission() {

        // 先判断是否有权限。
        if (!AndPermission.hasPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !AndPermission.hasPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                ) {
            // 申请权限。
            AndPermission.with(MainActivity.this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                    .send();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            KLog.i("权限onSucceed:" + grantedPermissions.toString());
            MyToast.showShortToast("权限申请成功");
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            KLog.i("权限onFailed:" + deniedPermissions.toString());
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
    };

    void init() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = (int) (Math.random() * 999 + 100);
                handler.sendMessage(message);
            }
        }, 1000, 1000);        //从1000ms即1s开始，1000ms为数字改变周期
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override//右上角菜单
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_updatelog) {
            Snackbar.make(getWindow().getDecorView(), "历史N天，精心打磨，只为蛊惑住你的心~\n希望纸琪能够喜欢~", Snackbar.LENGTH_LONG)
                    .setAction("详细", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("日志").setMessage(R.string.update_log).setCancelable(true)
                                    .setPositiveButton("OK~", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            dialog.show();
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_decide) {
            Intent intent = new Intent(MainActivity.this, DecideActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_developer) {
            Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showToast(String msg) {
        MyToast.showShortToast(msg);
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

        new InstallUtils(context, appUpdateInfo.getInstall_url(), "Love_" + appUpdateInfo.getVersionShort(), new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                KLog.i("installAPK-----onStart");
                if (dialogUpdate != null) {
                    dialogUpdate.setProgress(0);
                }
            }

            @Override
            public void onComplete(String path) {
                KLog.i("installAPK----onComplete:" + path);
                /**
                 * 安装APK工具类
                 * @param context       上下文
                 * @param filePath      文件路径
                 * @param authorities   ---------Manifest中配置provider的authorities字段---------
                 * @param callBack      安装界面成功调起的回调
                 */
                InstallUtils.installAPK(context, path, getPackageName() + ".fileProvider", new InstallUtils.InstallCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(context, "安装失败:" + e.toString(), Toast.LENGTH_SHORT).show();
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
                KLog.i("installAPK-----onLoading:-----total:" + total + ",current:" + current);
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
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.ic_launcher;
        String ticker = "正在下载Love更新包...";
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(this, 0);
        notifyUtils.notify_progress(rightPendIntent, smallIcon, ticker, "Love 下载", "正在下载中...", false, false, false);
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }
}
