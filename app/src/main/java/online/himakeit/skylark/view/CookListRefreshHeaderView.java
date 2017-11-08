package online.himakeit.skylark.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;

import java.util.Random;

import online.himakeit.skylark.R;
import online.himakeit.skylark.util.LogUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/6 19:24
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class CookListRefreshHeaderView extends SwipeRefreshHeaderLayout {

    private static final String TAG = "CookListRefreshHeader";

    private int mHeaderHeight;

    private boolean rotated = false;

    private Handler mHandler = new Handler();
    private ImageView iv_cook_01;
    private ImageView iv_cook_02;
    private ImageView iv_cook_03;
    private ImageView iv_cook_04;
    private RelativeLayout iv_pan_cover;
    private ImageView iv_pan;

    public CookListRefreshHeaderView(Context context) {
        super(context, null);
    }

    public CookListRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CookListRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height_cook);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iv_cook_01 = (ImageView) findViewById(R.id.iv_cook_01);
        iv_cook_02 = (ImageView) findViewById(R.id.iv_cook_02);
        iv_cook_03 = (ImageView) findViewById(R.id.iv_cook_03);
        iv_cook_04 = (ImageView) findViewById(R.id.iv_cook_04);
        iv_pan_cover = (RelativeLayout) findViewById(R.id.iv_pan_cover);
        iv_pan = (ImageView) findViewById(R.id.iv_pan);
    }

    @Override
    public void onRefresh() {
        //正在刷新,执行动画
        iv_pan_cover.setVisibility(View.INVISIBLE);
        iv_cook_01.setVisibility(View.VISIBLE);
        iv_cook_02.setVisibility(View.VISIBLE);
        iv_cook_03.setVisibility(View.VISIBLE);
        iv_cook_04.setVisibility(View.VISIBLE);
        iv_pan_cover.setAlpha(0);
        startAnimation();
    }

    @Override
    public void onPrepare() {
        LogUtils.show(TAG,"onPrepare()");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
//        LogUtils.show(TAG,"y:" + y);
        if (!isComplete) {
            if (y >= mHeaderHeight) {
                if (!rotated) {
                    rotated = true;
                }
            } else if (y < mHeaderHeight) {
                float tan = (float) (y - mHeaderHeight * 2 / 3) / (float) (iv_pan_cover.getWidth());
                int angle = (int) (tan * 90);
                if (angle > 30) {
                    angle = 30;
                }
                if (angle < 0) {
                    angle = 0;
                }
                iv_pan_cover.setRotation(-angle);
            }
        }
    }

    @Override
    public void onRelease() {
        LogUtils.show(TAG,"onRelease()");
    }

    @Override
    public void onComplete() {
        rotated = false;
        mHandler.removeCallbacksAndMessages(null);
        iv_pan_cover.setVisibility(View.VISIBLE);
        iv_cook_01.setVisibility(View.GONE);
        iv_cook_02.setVisibility(View.GONE);
        iv_cook_03.setVisibility(View.GONE);
        iv_cook_04.setVisibility(View.GONE);
        iv_pan_cover.setAlpha(1);
        iv_pan_cover.setRotation(0);
        float[] defaultPoint = {0, 0};
        startParabolaAnimation(iv_cook_01, defaultPoint, defaultPoint, defaultPoint);
        startParabolaAnimation(iv_cook_02, defaultPoint, defaultPoint, defaultPoint);
        startParabolaAnimation(iv_cook_03, defaultPoint, defaultPoint, defaultPoint);
        startParabolaAnimation(iv_cook_04, defaultPoint, defaultPoint, defaultPoint);
    }

    @Override
    public void onReset() {
        rotated = false;
    }


    private void startAnimation() {
        float x = 0;
        float y = 0;
        final float[] startPoint = {x, y};

        Random random = new Random();
        int nextInt = random.nextInt(50);
        final float[] endPoint = {nextInt + 180, nextInt + 30};
        final float[] midPoint = {nextInt + 100, nextInt - 70};

        nextInt = random.nextInt(40);
        final float[] endPoint2 = {nextInt + 160, nextInt + 40};
        final float[] midPoint2 = {nextInt + 80, nextInt - 80};

        nextInt = random.nextInt(30);
        final float[] endPoint3 = {nextInt - 200, nextInt + 40};
        final float[] midPoint3 = {nextInt - 100, nextInt - 70};

        nextInt = random.nextInt(60);
        final float[] endPoint4 = {nextInt - 170, nextInt + 45};
        final float[] midPoint4 = {nextInt - 80, nextInt - 80};

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startParabolaAnimation(iv_cook_01, startPoint, endPoint, midPoint);
            }
        }, 100);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startParabolaAnimation(iv_cook_02, startPoint, endPoint2, midPoint2);
            }
        }, 200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startParabolaAnimation(iv_cook_03, startPoint, endPoint3, midPoint3);
            }
        }, 300);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startParabolaAnimation(iv_cook_04, startPoint, endPoint4, midPoint4);

            }
        }, 400);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 500);
    }

    /**
     * 抛物线动画
     *
     * @param view
     * @param startPoint 起点坐标
     * @param endPoint   结束点坐标
     * @param midPoint   中间点坐标
     * @return
     */
    public static ObjectAnimator startParabolaAnimation(final View view, float[] startPoint, float[] endPoint, float[] midPoint) {
        //分300帧完成动画
        int count = 200;
        //动画时间持续1.5秒
        int duration = 600;
        Keyframe[] keyframes = new Keyframe[count];
        final float keyStep = 1f / (float) count;
        float key = keyStep;
        //计算并保存每一帧x轴的位置
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, i * getDx(startPoint, endPoint) / count + startPoint[0]);
            key += keyStep;
        }
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("translationX", keyframes);
        key = keyStep;
        //计算并保存每一帧y轴的位置
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, getY(startPoint, endPoint, midPoint, i * getDx(startPoint, endPoint) / count + startPoint[0]));
            key += keyStep;
        }
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofKeyframe("translationY", keyframes);
        ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhX).setDuration(duration);
        //开始动画
        yxBouncer.start();
        return yxBouncer;
    }

    private static float getDx(float[] startPoint, float[] endPoint) {
        return endPoint[0] - startPoint[0];
    }

    private static float getDy(float[] startPoint, float[] endPoint) {
        return endPoint[1] - startPoint[1];
    }

    /**
     * 这里是根据三个坐标点{（0,0），（300,0），（150,300）}计算出来的抛物线方程
     * y = ax² + bx + c
     *
     * @param x
     * @return
     */
    private static float getY(float[] startPoint, float[] endPoint, float[] midPoint, float x) {
        float x1 = startPoint[0];
        float y1 = startPoint[1];
        float x2 = endPoint[0];
        float y2 = endPoint[1];
        float x3 = midPoint[0];
        float y3 = midPoint[1];
        float a, b, c;
        a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2))
                / (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2));
        b = (y1 - y2) / (x1 - x2) - a * (x1 + x2);
        c = y1 - (x1 * x1) * a - x1 * b;
        return a * x * x + b * x + c;
    }


}
