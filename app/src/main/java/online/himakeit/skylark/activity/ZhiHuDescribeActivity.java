package online.himakeit.skylark.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.LinkMovementMethodExt;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.model.zhuhu.ZhiHuStory;
import online.himakeit.skylark.presenter.implPresenter.ZhiHuStoryPresenterImpl;
import online.himakeit.skylark.presenter.implView.IZhiHuStory;
import online.himakeit.skylark.util.DensityUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 9:49
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuDescribeActivity extends BaseActivity implements IZhiHuStory {

    /**
     * topbar
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    //---------
    @Bind(R.id.rl_zhihu_desc)
    RelativeLayout rl_top_news_desc;
    @Bind(R.id.iv_tnd_image)
    ImageView iv_tnd_image;
    @Bind(R.id.tv_tnd_title)
    TextView tv_tnd_title;
    @Bind(R.id.tv_tnd_content)
    TextView tv_tnd_content;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    private String docid;
    private String title;
    private String imageUrl;

    int[] mDeviceInfo;
    int width;
    int heigh;

    private RequestListener glideLoadListener = new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            // TODO: 2017/8/22 这里暂时不做操作，日后在修改
            return false;
        }
    };

    private ZhiHuStoryPresenterImpl mZhiHuStoryPresenter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取图片路径列表

                    break;
                case 2://图片点击事件

                    break;
            }
        }
    };

    HtmlSpanner htmlSpanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_desc);
        ButterKnife.bind(this);


        mDeviceInfo = DensityUtils.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width * 3 / 4;

        htmlSpanner = new HtmlSpanner(this, width, handler);

        initData();
        getData();
    }

    private void initData() {
        docid = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        imageUrl = getIntent().getStringExtra("image");

        tv_title.setText("知乎日报");
        tv_tnd_title.setText(title);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.imageview_loading)//设置加载中的图片
                .error(R.drawable.imageview_loading)//设置加载错误时的图片
                .override(width, heigh)
                .listener(glideLoadListener)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv_tnd_image);

        mZhiHuStoryPresenter = new ZhiHuStoryPresenterImpl(this);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void getData() {
        mZhiHuStoryPresenter.getZhiHuStory(docid);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(rl_top_news_desc, "请检查网络", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        }).show();
    }

    @Override
    public void showZhiHuStory(final ZhiHuStory zhiHuStory) {
        progressBar.setVisibility(View.INVISIBLE);
        if (zhiHuStory != null) {

            final Spannable spannable = htmlSpanner.fromHtml(zhiHuStory.getBody());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_tnd_content.setText(spannable);
                            tv_tnd_content.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
                        }
                    });
                }
            }).start();
        } else {
            Snackbar.make(rl_top_news_desc, "请检查网络", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            }).show();
        }
    }
}
