package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Calendar;

import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.R;
import online.himakeit.skylark.presenter.ISplashPresenter;
import online.himakeit.skylark.presenter.implView.ISplashView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/8 16:03
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SplashPresenterImpl extends BasePresenterImpl implements ISplashPresenter {

    Context context;
    ISplashView iSplashView;

    public SplashPresenterImpl(Context context, ISplashView iSplashView) {
        if (iSplashView == null) {
            throw new IllegalArgumentException("IZuiMeiPic must not be null!");
        }
        this.context = context;
        this.iSplashView = iSplashView;
    }

    @Override
    public void initialized() {
        iSplashView.initializeViews(getVersionName(context), getCopyright(context),
                getBackgroundImageResID());
        Animation animation = getBackgroundImageAnimation(context);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iSplashView.navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iSplashView.animateBackgroundImage(animation);
    }

    @Override
    public int getBackgroundImageResID() {
        int resId;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour <= 12) {
            resId = R.drawable.morning;
        } else if (hour > 12 && hour <= 18) {
            resId = R.drawable.afternoon;
        } else {
            resId = R.drawable.night;
        }
        return resId;
    }

    @Override
    public String getCopyright(Context context) {
        return context.getResources().getString(R.string.splash_copyright);
    }

    @Override
    public String getVersionName(Context context) {
        return String.valueOf("V " + AppContext.getVersionName());
    }

    @Override
    public Animation getBackgroundImageAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.splash);
    }
}
