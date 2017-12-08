package online.himakeit.skylark.presenter;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/8 16:02
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface ISplashPresenter extends BasePresenter {
    void initialized();

    int getBackgroundImageResID();

    String getCopyright(Context context);

    String getVersionName(Context context);

    Animation getBackgroundImageAnimation(Context context);
}
