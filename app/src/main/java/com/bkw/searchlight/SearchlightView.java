package com.bkw.searchlight;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * 探照灯View
 * 解析：
 * 简单来说，其实就是采用思路：
 * <p>
 * 用两只笔，一只负责画背景（黑布），一只负责橡皮擦的功能（画圆），
 * 这时由于Activity是透明的，所以就可以直接看到下一层内容，
 * 然后在手指移动时更新橡皮擦（圆形）的位置，重绘就可以了
 *
 * @author bkw
 */
public class SearchlightView extends AppCompatImageView {
    /**
     * 两支笔
     */
    private Paint paint1, paint2;
    private float x = 200, y = 200;
    /**
     * 屏幕宽高，用于判断移动路径是否越界
     */
    private int screenWidth, screenHeight;

    public SearchlightView(Context context) {
        super(context);
        initData();
    }

    public SearchlightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public SearchlightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        //获取屏幕宽高
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        setMyEraseSize(40);
        setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawMyCircle(canvas);
    }

    //画圆
    private void drawMyCircle(Canvas canvas) {
        canvas.drawCircle(x, y, 100, paint1);
    }

    //画背景颜色
    private void drawBackground(Canvas canvas) {
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        //将背景涂满，相当于一块布满屏幕的画布
        canvas.drawRect(rect, paint2);
    }

    /**
     * 原理：通过不断改变x,y坐标重绘透明圆，实现探照灯功能，因为Android中的橡皮擦擦除实际上就是用透明笔把原先笔迹覆盖掉
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        // 获取当前触摸点的X轴坐标
        final int x = (int) event.getX();
        // 获取当前触摸点的Y轴坐标
        final int y = (int) event.getY();
        this.x = x;
        this.y = y;
        // 重绘画布
        invalidate();
        return true;
    }

    /**
     * 设置橡皮擦大小
     *
     * @param size
     */
    public void setMyEraseSize(int size) {
        paint1 = new Paint();
        paint1.reset();
        paint1.setAntiAlias(true);//抗锯齿
        paint1.setDither(true);//防抖动
        paint1.setColor(Color.WHITE);
        paint1.setStrokeWidth(size);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        BlurMaskFilter bmf = new BlurMaskFilter((0.5f), BlurMaskFilter.Blur.SOLID);
        paint1.setMaskFilter(bmf);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setStrokeJoin(Paint.Join.ROUND);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setSubpixelText(true);
        paint1.setTextSize(50);
    }

    /**
     * 设置比颜色
     *
     * @param color
     */
    public void setColor(int color) {
        paint2 = new Paint();
        paint2.reset();
        //抗锯齿
        paint2.setAntiAlias(true);
        //防抖动
        paint2.setDither(true);
        paint2.setStrokeWidth(5);
        paint2.setTextSize(50);
        paint2.setSubpixelText(true);
        BlurMaskFilter bmf = new BlurMaskFilter((0.5f), BlurMaskFilter.Blur.SOLID);
        paint2.setMaskFilter(bmf);
        paint2.setPathEffect(new CornerPathEffect(20));
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setColor(color);
    }
}
