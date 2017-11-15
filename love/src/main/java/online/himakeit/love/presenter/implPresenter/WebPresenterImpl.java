package online.himakeit.love.presenter.implPresenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import online.himakeit.love.presenter.IWebPresenter;
import online.himakeit.love.presenter.implView.IWebView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/15 18:33
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class WebPresenterImpl extends BasePresenterImpl<IWebView> implements IWebPresenter {

    private Context context;

    public WebPresenterImpl(Context context, IWebView iWebView) {
        this.context = context;
        attachView(iWebView);
    }

    @Override
    public void copy(String string) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData clipData = ClipData.newPlainText("text", string);
        cm.setPrimaryClip(clipData);
        if(mView != null) {
            mView.showToast("复制成功");
        }
    }

    @Override
    public void openBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

}