package online.himakeit.skylark.presenter.implView;

import android.view.animation.Animation;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/8 15:58
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ISplashView extends IBaseFragment{
    void animateBackgroundImage(Animation animation);

    void initializeViews(String versionName, String copyright, int backgroundResId);

    void navigateToHomePage();
}
