package com.han.demo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.han.framework.utils.ScreenUtils;

public class SuperView extends View {
    private Paint mPaint;
    private int HEIGHT;

    public SuperView(Context context) {
        super(context);
        init();
    }

    public SuperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path(); // 初始化 Path 对象
        mPaint.setColor(Color.parseColor("#ff0000"));
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 0, 400, 200, -225, 225);
        path.arcTo(400, 0, 600, 200, -180, 225, false);
        path.lineTo(400, 342);
        canvas.drawPath(path, mPaint);
        // 直方图
        // 先画背景
        /*mPaint.setColor(Color.parseColor("#506E7A"));
        canvas.drawRect(0, HEIGHT, ScreenUtils.getScreenWidth(getContext()), 720, mPaint);
        drawZhifang(canvas);
        HEIGHT += 720;*/

        HEIGHT += 400;
        // 画线
        mPaint.setColor(Color.RED);
        canvas.drawText("线", 50, HEIGHT + 50, mPaint);

        mPaint.setStrokeWidth(5);
        canvas.drawLine(100, HEIGHT, 300, HEIGHT + 100, mPaint);
        HEIGHT += 100;

        // 画圆
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(200, HEIGHT + 100, 100, mPaint);
        HEIGHT += 200;

        // 画圆
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawCircle(500, HEIGHT + 100, 100, mPaint);
        HEIGHT += 200;

        // 矩形
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, HEIGHT, ScreenUtils.getScreenWidth(getContext()), HEIGHT + 150, mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(100, HEIGHT + 50, 500, HEIGHT + 100, mPaint);
        HEIGHT += 150;



    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawZhifang(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        float interval = 25;
        float perWidth = 100;
        RectF rectF1 = new RectF(100 + 1 * interval + 0 * perWidth, 600 - 5, 100 + 1 * perWidth + 1 * interval, 600);
        RectF rectF2 = new RectF(100 + 2 * interval + 1 * perWidth, 600 - 30, 100 + 2 * perWidth + 2 * interval, 600);
        RectF rectF3 = new RectF(100 + 3 * interval + 2 * perWidth, 600 - 30, 100 + 3 * perWidth + 3 * interval, 600);
        RectF rectF4 = new RectF(100 + 4 * interval + 3 * perWidth, 600 - 200, 100 + 4 * perWidth + 4 * interval, 600);
        RectF rectF5 = new RectF(100 + 5 * interval + 4 * perWidth, 600 - 300, 100 + 5 * perWidth + 5 * interval, 600);
        RectF rectF6 = new RectF(100 + 6 * interval + 5 * perWidth, 600 - 450, 100 + 6 * perWidth + 6 * interval, 600);
        RectF rectF7 = new RectF(100 + 7 * interval + 6 * perWidth, 600 - 180, 100 + 7 * perWidth + 7 * interval, 600);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(rectF1, mPaint);
        canvas.drawRect(rectF2, mPaint);
        canvas.drawRect(rectF3, mPaint);
        canvas.drawRect(rectF4, mPaint);
        canvas.drawRect(rectF5, mPaint);
        canvas.drawRect(rectF6, mPaint);
        canvas.drawRect(rectF7, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawLines(new float[]{100, 20, 100, 600, 100, 600, 1000, 600}, mPaint);

        mPaint.setTextSize(18);
        canvas.drawText("Froyo", 100 + 1 * interval + 0.5f * perWidth, 620, mPaint);
        canvas.drawText("GB", 100 + 2 * interval + 1.5f * perWidth, 620, mPaint);
        canvas.drawText("ICS", 100 + 3 * interval + 2.5f * perWidth, 620, mPaint);
        canvas.drawText("JB", 100 + 4 * interval + 3.5f * perWidth, 620, mPaint);
        canvas.drawText("KitKat", 100 + 5 * interval + 4.5f * perWidth, 620, mPaint);
        canvas.drawText("L", 100 + 6 * interval + 5.5f * perWidth, 620, mPaint);
        canvas.drawText("M", 100 + 7 * interval + 6.5f * perWidth, 620, mPaint);

        mPaint.setTextSize(30);
        canvas.drawText("直方图", 500, 700, mPaint);
    }
}
