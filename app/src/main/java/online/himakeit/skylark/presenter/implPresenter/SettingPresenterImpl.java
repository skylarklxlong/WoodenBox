package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;

import online.himakeit.skylark.presenter.ISettingPresenter;
import online.himakeit.skylark.presenter.implView.ISettingView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/8 13:46
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SettingPresenterImpl extends BasePresenterImpl implements ISettingPresenter {

    Context context;
    ISettingView iSettingView;

    public SettingPresenterImpl(Context context, ISettingView iSettingView) {
        if (iSettingView == null) {
            throw new IllegalArgumentException("IZuiMeiPic must not be null!");
        }
        this.context = context;
        this.iSettingView = iSettingView;
    }

    @Override
    public void initCache() {

    }

    @Override
    public void cleanCache() {

    }

    @Override
    public void initAppUpdateState() {

    }

    @Override
    public void checkAppUpdate() {

    }
}
