package online.himakeit.skylark.common;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-13 下午6:52
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BaseActivity extends AppCompatActivity {

    private SVProgressHUD svProgressHUD;
    /**
     * 写文件的请求码
     */
    public static final int REQUEST_CODE_WRITE_FILE = 200;

    /**
     * 读取文件的请求码
     */
    public static final int REQUEST_CODE_READ_FILE = 201;

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

        initDialog();
    }

    private void initDialog() {
        svProgressHUD = new SVProgressHUD(this);
    }

    public void showProgressDialog(){
        dissmissProgressDialog();
        svProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressDialog(String msg){
        if (TextUtils.isEmpty(msg)){
            showProgressDialog();
        }else {
            dissmissProgressDialog();
            svProgressHUD.showWithStatus(msg, SVProgressHUD.SVProgressHUDMaskType.Black);
        }
    }

    public void showProgressSuccess(String msg){
        dissmissProgressDialog();
        svProgressHUD.showSuccessWithStatus(msg, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void showProgressError(String msg){
        dissmissProgressDialog();
        svProgressHUD.showErrorWithStatus(msg, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    public void dissmissProgressDialog(){
        if (svProgressHUD.isShowing()){
            svProgressHUD.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public Context getContext() {
        return mContext;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
