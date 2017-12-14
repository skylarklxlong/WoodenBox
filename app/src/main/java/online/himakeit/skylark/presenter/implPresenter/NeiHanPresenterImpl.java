package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import online.himakeit.skylark.api.NeiHanApiImpl;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.neihan.NeiHanDataEntity;
import online.himakeit.skylark.presenter.INeiHanPresenter;
import online.himakeit.skylark.presenter.implView.INeiHanFragment;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/13 16:28
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanPresenterImpl extends BasePresenterImpl implements INeiHanPresenter {

    Context context;
    INeiHanFragment iNeiHanFragment;

    public NeiHanPresenterImpl(Context context, INeiHanFragment iNeiHanFragment) {
        if (iNeiHanFragment == null) {
            throw new IllegalArgumentException("INeiHanFragment must not be null!");
        }
        this.context = context;
        this.iNeiHanFragment = iNeiHanFragment;
    }

    @Override
    public void getNeiHanData(String type, int count) {
        NeiHanApiImpl.getNeiHanData(context, type, count, 0x001, httpCallBack);
    }

    private MobCallBack httpCallBack = new MobCallBack() {
        @Override
        public void onSuccessList(int what, List results) {
        }

        @Override
        public void onSuccess(int what, Object result) {
            if (iNeiHanFragment == null) {
                return;
            }
            iNeiHanFragment.hideProgressDialog();
            switch (what) {
                case 0x001:
                    if (result == null) {
                        return;
                    }
                    if (iNeiHanFragment != null) {
                        iNeiHanFragment.updateNeiHanData((NeiHanDataEntity) result);
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, String result) {
            if (!TextUtils.isEmpty(result)) {
                iNeiHanFragment.hideProgressDialog();
                iNeiHanFragment.showError(result);
            }
        }
    };
}
