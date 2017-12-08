package online.himakeit.skylark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseActivityForFullScreen;
import online.himakeit.skylark.presenter.implPresenter.SplashPresenterImpl;
import online.himakeit.skylark.presenter.implView.ISplashView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/31 18:51
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SplashActivity extends BaseActivityForFullScreen implements ISplashView {

    private static final String TAG = "SplashActivity";

    @Bind(R.id.splash_image)
    ImageView mSplashImage;
    @Bind(R.id.splash_version_name)
    TextView mVersionName;
    @Bind(R.id.splash_copyright)
    TextView mCopyright;

    SplashPresenterImpl splashPresenter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashPresenter = new SplashPresenterImpl(this, this);
        splashPresenter.initialized();

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void animateBackgroundImage(Animation animation) {
        mSplashImage.startAnimation(animation);
    }

    @Override
    public void initializeViews(String versionName, String copyright, int backgroundResId) {
        mCopyright.setText(copyright);
        mVersionName.setText(versionName);
        mSplashImage.setImageResource(backgroundResId);
    }

    @Override
    public void navigateToHomePage() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        SplashActivity.this.finish();
    }
}
