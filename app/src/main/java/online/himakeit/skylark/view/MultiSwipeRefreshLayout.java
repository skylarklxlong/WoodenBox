package online.himakeit.skylark.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import online.himakeit.skylark.R;


/**
 * Created by：LiXueLong 李雪龙 on 2017/9/25 19:09
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {

    private CanChildScrollUpCallback mCanChildScrollUpCallback;
    private Drawable mForegroundDrawable;

    public MultiSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MultiSwipeRefreshLayout, 0, 0);
        mForegroundDrawable = array.getDrawable(R.styleable.MultiSwipeRefreshLayout_foreground);
        if (mForegroundDrawable != null){
            mForegroundDrawable.setCallback(this);
            setWillNotDraw(false);
        }
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mForegroundDrawable != null){
            mForegroundDrawable.setBounds(0,0,w,h);
        }
    }

    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null){
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }

    public interface CanChildScrollUpCallback{
        boolean canSwipeRefreshChildScrollUp();
    }

    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
        mCanChildScrollUpCallback = canChildScrollUpCallback;
    }
}
