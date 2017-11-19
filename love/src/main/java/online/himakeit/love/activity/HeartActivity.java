package online.himakeit.love.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import online.himakeit.love.R;
import online.himakeit.love.base.BaseActivity;
import online.himakeit.love.view.RevealTextView;
import online.himakeit.love.view.TypeTextView;
import online.himakeit.love.view.heart.HeartView2;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/16 9:41
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class HeartActivity extends BaseActivity {

    TypeTextView typeTextView;
    RevealTextView revealTextView;
    HeartView2 heartView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        typeTextView = findViewById(R.id.typeTextView);
        revealTextView = findViewById(R.id.revealTextView);
        heartView2 = findViewById(R.id.heartView2);

        typeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {

            }
        });

        typeTextView.start(
                "心动是等你的留言，\n" +
                        "渴望是常和你见面，\n" +
                        "甜蜜是和你小路流连，\n" +
                        "温馨是看着你清澈的双眼，\n" +
                        "爱你的感觉真的妙不可言！");

        revealTextView.setAnimationDuration(2000);
        revealTextView.setLoop(true);
        revealTextView.setAnimatedText(
                "遇见你是我今生最大的幸运\n" +
                        "爱你是我这辈子无悔的选择");

        //返回按钮
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_heart);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
