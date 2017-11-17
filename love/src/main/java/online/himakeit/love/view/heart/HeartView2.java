package online.himakeit.love.view.heart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

import java.util.ArrayList;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/13 18:41
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class HeartView2 extends TextureView {

    int offsetX;
    int offsetY;
    boolean reDraw = false;
    ArrayList<Bloom> blooms = new ArrayList<>();
    private Garden garden;
    private int width;
    private int height;
    private Paint backgroundPaint;
    private boolean isDrawing = false;
    private Bitmap bm;
    private Canvas canvas;
    private int heartRadio = 1;

    public HeartView2(Context context) {
        super(context);
        init();
    }

    public HeartView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        garden = new Garden();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.TRANSPARENT);

        setSurfaceTextureListener(new SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//                L.e("call: onSurfaceTextureAvailable([surface, width, height])-> ");
//                Canvas canvas = lockCanvas();
//                canvas.drawColor(Color.parseColor("#800000aa"));
//                unlockCanvasAndPost(canvas);

                HeartView2.this.width = width;
                HeartView2.this.height = height;
                //我的手机宽度像素是1080，发现参数设置为30比较合适，这里根据不同的宽度动态调整参数
                heartRadio = width * 30 / 1080;

                offsetX = width / 2;
                offsetY = height / 2 - 55;
//        bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bm);
                drawOnNewThread();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//                L.e("call: onSurfaceTextureSizeChanged([surface, width, height])-> ");
//                Canvas canvas = lockCanvas();
//                canvas.drawColor(Color.parseColor("800000FF"));
//                unlockCanvasAndPost(canvas);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//                L.e("call: onSurfaceTextureDestroyed([surface])-> ");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//                L.e("call: onSurfaceTextureUpdated([surface])-> ");
            }
        });
    }

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (heartRadio * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-heartRadio * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));

        return new Point(offsetX + (int) x, offsetY + (int) y);
    }


    //绘制列表里所有的花朵
    private void drawHeart() {
        canvas.drawRect(0, 0, width, height, backgroundPaint);
        Canvas c = lockCanvas();
        for (Bloom b : blooms) {
            b.draw(canvas);
        }

        c.drawBitmap(bm, 0, 0, null);
        unlockCanvasAndPost(c);
    }

    /**
     * 清理画布
     */
    private void clear() {
        Canvas c = lockCanvas();
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        unlockCanvasAndPost(c);

    }

    public void reDraw() {
        reDraw = true;
        invalidate();

        if (isDrawing) {
            return;
        }
        drawOnNewThread();
    }

    //开启一个新线程绘制
    private void drawOnNewThread() {
        new Thread() {
            @Override
            public void run() {
                if (isDrawing) return;
                isDrawing = true;

                float angle = 10;
                while (true) {

                    if (reDraw) {
                        blooms.clear();
                        clear();
                        angle = 10;
                        reDraw = false;
                    }

                    Bloom bloom = getBloom(angle);
                    if (bloom != null) {
                        blooms.add(bloom);
                    }
                    if (angle >= 30) {
                        break;
                    } else {
                        angle += 0.2;
                    }
                    drawHeart();
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isDrawing = false;
            }
        }.start();
    }


    private Bloom getBloom(float angle) {

        Point p = getHeartPoint(angle);

        boolean draw = true;
        /**循环比较新的坐标位置是否可以创建花朵,
         * 为了防止花朵太密集
         * */
        for (int i = 0; i < blooms.size(); i++) {

            Bloom b = blooms.get(i);
            PointInfo bp = b.getPointInfo();
            float distance = (float) Math.sqrt(Math.pow(p.x - bp.x, 2) + Math.pow(p.y - bp.y, 2));
            if (distance < Garden.Options.maxBloomRadius * 1.5) {
                draw = false;
                break;
            }
        }
        //如果位置间距满足要求，就在该位置创建花朵并将花朵放入列表
        if (draw) {
            Bloom bloom = garden.createRandomBloom(p.x, p.y);
            return bloom;
        }
        return null;
    }


}
