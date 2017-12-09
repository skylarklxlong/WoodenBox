package online.himakeit.skylark.presenter.implPresenter;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;

import java.io.File;
import java.util.List;

import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.listeners.MobCallBack;
import online.himakeit.skylark.model.Config;
import online.himakeit.skylark.model.fir.AppUpdateInfo;
import online.himakeit.skylark.presenter.ISettingPresenter;
import online.himakeit.skylark.presenter.implView.ISettingView;
import online.himakeit.skylark.util.FileUtils;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.SharePreUtil;

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
        new GetDiskCacheSizeTask().execute(new File(context.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
    }

    @Override
    public void cleanCache() {
        iSettingView.showBasesProgressSuccess("正在清理缓存。。。");
        //开启后台线程，清理缓存
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(AppContext.getAppContext()).clearDiskCache();
                AppContext.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearMemory();
                        if (iSettingView != null) {
                            iSettingView.showBasesProgressSuccess("清除完毕");
                            initCache();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void checkAppUpdate() {
        //版本更新检查
        if (NetUtils.hasNetWorkConection(context)) {
            //版本更新
            MobApiImpl.getAppUpdateInfo(0x001, httpCallBack);
        } else {
            if (iSettingView != null) {
                iSettingView.showToast("请检查网络设置");
            }
        }

    }

    private MobCallBack httpCallBack = new MobCallBack() {
        @Override
        public void onSuccessList(int what, List results) {
        }

        @Override
        public void onSuccess(int what, Object result) {
            if (iSettingView == null) {
                return;
            }
            switch (what) {
                case 0x001:
                    if (result == null) {
                        return;
                    }
                    //获取当前APP的版本号
                    int newVersion;
                    AppUpdateInfo appUpdateInfo;
                    try {
                        appUpdateInfo = (AppUpdateInfo) result;
                        newVersion = Integer.parseInt(appUpdateInfo.getBuild());
                    } catch (Exception e) {
                        newVersion = 1;
                        appUpdateInfo = new AppUpdateInfo();
                    }
                    if (AppContext.getVersionCode() < newVersion) {
                        SharePreUtil.saveBooleanData(context, Config.SPAppUpdate + AppContext.getVersionCode(), true);
                        //需要版本更新
                        if (iSettingView != null) {
                            iSettingView.showAppUpdateDialog(appUpdateInfo);
                        }
                    }

                    break;
            }
        }

        @Override
        public void onFail(int what, String result) {
            if (!TextUtils.isEmpty(result)) {
                iSettingView.showToast(result);
            }
        }
    };

    class GetDiskCacheSizeTask extends AsyncTask<File, Long, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (iSettingView != null) {
                iSettingView.setCacheSize("计算中。。。");
            }
        }

        @Override
        protected Long doInBackground(File... files) {
            long totalSize = 0;
            for (File dir : files) {
                publishProgress(totalSize);
                totalSize += FileUtils.getFileLength(dir);
            }
            return totalSize;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            String sizeText = Formatter.formatFileSize(context, aLong);
            if (iSettingView != null) {
                iSettingView.setCacheSize(sizeText);
            }
        }
    }
}
