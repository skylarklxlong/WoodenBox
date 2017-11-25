package online.himakeit.love.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import online.himakeit.love.R;
import online.himakeit.love.base.BaseActivity;
import online.himakeit.love.view.TypeTextView;

public class MyPromiseActivity extends BaseActivity {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView title_image_view;
    TypeTextView typeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypromise);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.tb_yd);
        title_image_view = (ImageView) findViewById(R.id.title_image_view);
        typeTextView = (TypeTextView) findViewById(R.id.typeTextView);

        typeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {

            }
        });

        typeTextView.start(
                "00110000001110000001100111100\n" +
                         "00100000000100000000100111100\n" +
                         "00110000000000000001100111100\n" +
                         "00111100000000000011100111100\n" +
                         "00111111000000001111100111100\n" +
                         "00111111110000111111100111100\n" +
                         "00111111111001111111110000001" +
                        "\n\n\n" +
                        "永远爱你\n" +
                        "永远把你放在第一位\n" +
                        "一直陪伴着你\n" +
                        "绝对不对你撒谎说假话\n" +
                        "永远宠着你\n" +
                        "不惹你生气\n" +
                        "把最好的都留给你\n" +
                        "永远保护你，不让你受伤害\n" +
                        "\n\nlove you forever");

        Glide.with(this).load(R.drawable.xyxl).into(title_image_view);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("我的承诺");
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