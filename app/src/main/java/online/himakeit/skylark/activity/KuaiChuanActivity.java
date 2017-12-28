package online.himakeit.skylark.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.util.NavigatorUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/7 14:14
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class KuaiChuanActivity extends OtherBaseActivity {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuaichuan);
        ButterKnife.bind(this);

        tv_title.setText("文件快传");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @OnClick({R.id.tv_back,R.id.btn_send,R.id.btn_get,R.id.web_transfer})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.btn_send:
                NavigatorUtils.toChooseFileUI(getContext());
                break;
            case R.id.btn_get:
                NavigatorUtils.toReceiverWaitingUI(getContext());
                break;
            case R.id.web_transfer:
                Snackbar.make(tv_title,"此功能尚待开发！",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
