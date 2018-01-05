package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.OtherBaseActivity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:22
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class OilPriceActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_oil_price);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tv_title.setText("全国省市今日油价");
    }

    @OnClick({R.id.tv_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
