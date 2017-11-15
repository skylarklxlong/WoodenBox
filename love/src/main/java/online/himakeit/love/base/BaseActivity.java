package online.himakeit.love.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by maning on 16/3/2.
 * <p/>
 * 父类
 */
public class BaseActivity extends AppCompatActivity {

    private SVProgressHUD mSVProgressHUD;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initDialog();

    }

    private void initDialog() {
        mSVProgressHUD = new SVProgressHUD(this);
    }

    public void showProgressDialog() {
        dissmissProgressDialog();
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressDialog(String message) {
        if (TextUtils.isEmpty(message)) {
            showProgressDialog();
        } else {
            dissmissProgressDialog();
            mSVProgressHUD.showWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
        }
    }

    public void showProgressSuccess(String message) {
        dissmissProgressDialog();
        mSVProgressHUD.showSuccessWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressError(String message) {
        dissmissProgressDialog();
        mSVProgressHUD.showErrorWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void dissmissProgressDialog() {
        if (mSVProgressHUD.isShowing()) {
            mSVProgressHUD.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mSVProgressHUD.isShowing()) {
                mSVProgressHUD.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        dissmissProgressDialog();
        Glide.with(this.getApplicationContext()).pauseRequests();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // MainActivity 内嵌套Fragment，应在Fragment中统计页面
        String className = this.getClass().getSimpleName();
        if (!"MainActivity".equals(className)) {
            MobclickAgent.onPageStart(className);   // 统计页面
        }
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        // MainActivity 内嵌套Fragment，应在Fragment中统计页面
        String className = this.getClass().getSimpleName();
        if (!"MainActivity".equals(className)) {
            MobclickAgent.onPageEnd(className);   // 统计页面
        }
        MobclickAgent.onPause(this);
    }


}
