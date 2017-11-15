package online.himakeit.love.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import online.himakeit.love.R;
import online.himakeit.love.base.BaseActivity;

public class DecideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);

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
                Intent intent = new Intent(DecideActivity.this, YourdecideActivity.class);
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
