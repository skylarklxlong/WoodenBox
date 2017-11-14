package online.himakeit.love.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import online.himakeit.love.MainActivity;
import online.himakeit.love.R;
import online.himakeit.love.view.LoveView;
import online.himakeit.love.view.TypeTextView;

public class SplashActivity extends AppCompatActivity {

    LoveView loveView;
    TypeTextView typeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        loveView = (LoveView) findViewById(R.id.loveView);
        typeTextView = (TypeTextView)findViewById(R.id.typeTextView);
        typeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {

            }
        });
        typeTextView.start("心动是等你的留言，渴望是常和你见面，甜蜜是和你小路流连，温馨是看着你清澈的双眼，爱你的感觉真的妙不可言！");
        loveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
    }
}
