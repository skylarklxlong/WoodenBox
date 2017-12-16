package online.himakeit.skylark.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import online.himakeit.skylark.util.DensityUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/16 11:23
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ShowMaxImageView extends android.support.v7.widget.AppCompatImageView {
    private float mHeight = 0;

    public ShowMaxImageView(Context context) {
        super(context);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {

        if (bm != null) {
            getHeight(bm);
        }

        super.setImageBitmap(bm);
        requestLayout();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {

        if (drawable != null) {
            getHeight(drawableToBitamp(drawable));
        }

        super.setImageDrawable(drawable);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mHeight != 0) {

            int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
            int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

            int resultHeight = (int) Math.max(mHeight, sizeHeight);

            if (resultHeight >= DensityUtils.getScreenHeight((Activity) getContext())) {
                resultHeight = DensityUtils.getScreenHeight((Activity) getContext()) / 3;
            }

            setMeasuredDimension(sizeWidth, resultHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    private void getHeight(Bitmap bm) {

        float bitmapWidth = bm.getWidth();
        float bitmapHeight = bm.getHeight();

        if (bitmapWidth > 0 && bitmapHeight > 0) {
            float scaleWidth = getWidth() / bitmapWidth;
            mHeight = bitmapHeight * scaleWidth;
        }

    }


    private Bitmap drawableToBitamp(Drawable drawable) {

        if (drawable != null) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        } else {
            return null;
        }
    }
}
