package com.han.demo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class HistogramView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float interval = 25;
    private float perWidth = 100;

    public HistogramView(Context context) {
        super(context);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF1 = new RectF(100 + 1 * interval + 0 * perWidth,600 - 5,100 + 1 * perWidth + 1 * interval,600);
        RectF rectF2 = new RectF(100 + 2 * interval + 1 * perWidth,600 - 30,100 + 2 * perWidth + 2 * interval,600);
        RectF rectF3 = new RectF(100 + 3 * interval + 2 * perWidth,600 - 30,100 + 3 * perWidth + 3 * interval,600);
        RectF rectF4 = new RectF(100 + 4 * interval + 3 * perWidth,600 - 200,100 + 4 * perWidth + 4 * interval,600);
        RectF rectF5 = new RectF(100 + 5 * interval + 4 * perWidth,600 - 300,100 + 5 * perWidth + 5 * interval,600);
        RectF rectF6 = new RectF(100 + 6 * interval + 5 * perWidth,600 - 450,100 + 6 * perWidth + 6 * interval,600);
        RectF rectF7 = new RectF(100 + 7 * interval + 6 * perWidth,600 - 180,100 + 7 * perWidth + 7 * interval,600);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(rectF1,paint);
        canvas.drawRect(rectF2,paint);
        canvas.drawRect(rectF3,paint);
        canvas.drawRect(rectF4,paint);
        canvas.drawRect(rectF5,paint);
        canvas.drawRect(rectF6,paint);
        canvas.drawRect(rectF7,paint);
        paint.setColor(Color.WHITE);
        canvas.drawLines(new float[]{100,20,100,600,100,600,1000,600},paint);

        paint.setTextSize(18);
        canvas.drawText("Froyo",100 + 1 * interval + 0.5f * perWidth,620,paint);
        canvas.drawText("GB",100 + 2 * interval + 1.5f * perWidth,620,paint);
        canvas.drawText("ICS",100 + 3 * interval + 2.5f * perWidth,620,paint);
        canvas.drawText("JB",100 + 4 * interval + 3.5f * perWidth,620,paint);
        canvas.drawText("KitKat",100 + 5 * interval + 4.5f * perWidth,620,paint);
        canvas.drawText("L",100 + 6 * interval + 5.5f * perWidth,620,paint);
        canvas.drawText("M",100 + 7 * interval + 6.5f * perWidth,620,paint);

        paint.setTextSize(30);
        canvas.drawText("直方图",500,700,paint);
    }
}
