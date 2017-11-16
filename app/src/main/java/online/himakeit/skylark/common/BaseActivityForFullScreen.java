package online.himakeit.skylark.common;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 16:56
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class BaseActivityForFullScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }
}
