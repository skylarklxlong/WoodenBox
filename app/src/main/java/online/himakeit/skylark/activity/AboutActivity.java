package online.himakeit.skylark.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.BuildConfig;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.util.Shares;


/**
 * Created by：LiXueLong 李雪龙 on 2017/9/28 10:46
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_version)
    TextView mTvVersion;
    @Bind(R.id.collapsing_tollbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setUpVersionName();
        mCollapsingToolbarLayout.setTitle("一个木匣");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.onBackPressed();
            }
        });
    }

    private void setUpVersionName() {
        mTvVersion.setText("Version " + BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about_share:
                Shares.share(this, "发现了一款非常美观的App「一个木匣」，每天一张精选妹纸图、一个精选小视频（视频源地址播放），一篇程序猿干货，完全开源不收费，太赞了! 推荐~：http://fir.im/woodenbox ");
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
