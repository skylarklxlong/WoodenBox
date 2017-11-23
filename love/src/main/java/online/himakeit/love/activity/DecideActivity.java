package online.himakeit.love.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import online.himakeit.love.R;
import online.himakeit.love.base.BaseActivity;

public class DecideActivity extends BaseActivity {
    ImageView iv_youinmyeyes;
    ImageView iv_yourdecide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);

        iv_youinmyeyes = (ImageView) findViewById(R.id.iv_you_in_my_eyes);
        iv_yourdecide = (ImageView) findViewById(R.id.iv_you_decide);

        Glide.with(this).load(R.drawable.youinmyeyes).into(iv_youinmyeyes);
        Glide.with(this).load(R.drawable.xyxl).into(iv_yourdecide);

        CardView cv_you = (CardView) findViewById(R.id.cv_you);
        cv_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DecideActivity.this, YouinmyeyesActivity.class);
                startActivity(intent);
            }
        });
        CardView cv_decide = (CardView) findViewById(R.id.cv_decide);
        cv_decide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DecideActivity.this, MyPromiseActivity.class);
                startActivity(intent);
            }
        });

        //返回按钮
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_dc);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //返回按钮
    }

    //返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //返回按钮
}
