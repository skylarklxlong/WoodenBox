package online.himakeit.love.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import online.himakeit.love.R;
import online.himakeit.love.base.BaseActivity;

public class YouinmyeyesActivity extends BaseActivity {

    FloatingActionButton fab;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView title_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youinmyeyes);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        fab = (FloatingActionButton) findViewById(R.id.fb_decide);
        title_image_view = (ImageView) findViewById(R.id.title_image_view);

        Glide.with(this).load(R.drawable.xuechan).into(title_image_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YouinmyeyesActivity.this, YourdecideActivity.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("我眼中的你");

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
}
