package online.himakeit.skylark.common;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-13 下午6:52
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 写文件的请求码
     */
    public static final int REQUEST_CODE_WRITE_FILE = 200;

    /**
     * 读取文件的请求码
     */
    public static final int REQUEST_CODE_READ_FILE = 201;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        //设置StatuBar和BottomBar透明
//        StatusBarUtils.setStatuBarAndBottomBarTranslucent(this);
        super.onCreate(savedInstanceState);

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
