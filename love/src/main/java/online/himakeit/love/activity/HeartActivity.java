package online.himakeit.love.activity;

import android.os.Bundle;

import online.himakeit.love.R;
import online.himakeit.love.base.BaseActivity;
import online.himakeit.love.view.TypeTextView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/16 9:41
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class HeartActivity extends BaseActivity {

    TypeTextView typeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        typeTextView = findViewById(R.id.typeTextView);

        typeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {

            }
        });

        typeTextView.start("心动是等你的留言，渴望是常和你见面，甜蜜是和你小路流连，温馨是看着你清澈的双眼，爱你的感觉真的妙不可言！");
    }
}
