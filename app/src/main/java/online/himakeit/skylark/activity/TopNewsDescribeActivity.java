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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivity;
import online.himakeit.skylark.model.topnews.NewsDetailBean;
import online.himakeit.skylark.presenter.implPresenter.TopNewsNewDesPresenterImpl;
import online.himakeit.skylark.presenter.implView.ITopNewsDesFragment;
import online.himakeit.skylark.util.DensityUtils;
import online.himakeit.skylark.util.JsonUtils;
import online.himakeit.skylark.util.LogUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 9:49
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TopNewsDescribeActivity extends BaseActivity implements ITopNewsDesFragment {

    /**
     * topbar
     */
    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    //---------
    @Bind(R.id.rl_top_news_desc)
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
    private String source;

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

    private TopNewsNewDesPresenterImpl mTopNewsDesPresenter;

    // TODO: 2017/8/24 由于今日头条中的图片链接并不是直接放在文章中的，而是通过一个标签来控制的，所有暂时用不上
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://获取图片路径列表

                    break;
                case 2://图片点击事件

                    break;
            }
        }
    };

    HtmlSpanner htmlSpanner;
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_desc);
        ButterKnife.bind(this);



        mDeviceInfo = DensityUtils.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width * 3 / 4;

        htmlSpanner = new HtmlSpanner(this,width,handler);

//        initDataFromNet();

        initData();
        getData();
    }

    private void initDataFromNet() {
        new Thread(){
            @Override
            public void run() {
                String urlString = "http://c.m.163.com/nc/article/CSC6JTQT00018AOQ/full.html";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    int code = connection.getResponseCode();
                    if (code == 200){
                        InputStream inputStream = connection.getInputStream();

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int length = 0;
                        byte[] buffer = new byte[1024];

                        while ((length = inputStream.read(buffer)) > 0) {

                            baos.write(buffer, 0, length);
                        }
                        String result = baos.toString();

                        LogUtils.show(result);
                        NewsDetailBean newsDetailBean = JsonUtils.readJsonNewsDetailBeans(result, "CSC6JTQT00018AOQ");
                        LogUtils.show(newsDetailBean.toString());
                        inputStream.close();
                        baos.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void initData() {
        docid = getIntent().getStringExtra("docid");
        title = getIntent().getStringExtra("title");
        imageUrl = getIntent().getStringExtra("image");
        source = getIntent().getStringExtra("source");

        tv_title.setText(source);
        tv_tnd_title.setText(title);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.imageview_loading)//设置加载中的图片
                .error(R.drawable.imageview_loading)//设置加载错误时的图片
                .override(width,heigh)
                .listener(glideLoadListener)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv_tnd_image);

        mTopNewsDesPresenter = new TopNewsNewDesPresenterImpl(this);
    }

    private void getData() {
        mTopNewsDesPresenter.getDescribleMessage(docid);
    }


    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(rl_top_news_desc,"请检查网络",Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        }).show();
    }

    @Override
    public void updateListItem(NewsDetailBean newsList) {
        progressBar.setVisibility(View.INVISIBLE);
        if (newsList != null){
//            tv_tnd_content.setText(Html.fromHtml(newsList.getBody()));
            final Spannable spannable = htmlSpanner.fromHtml(newsList.getBody());
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
        }else {
            Snackbar.make(rl_top_news_desc,"内容加载失败！",Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            }).show();
        }
    }
}
