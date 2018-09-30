package com.han.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class VolumneView extends View {
    private Paint mPaint;

    public VolumneView(Context context) {
        super(context);
        init();
    }

    public VolumneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, 200, 200, mPaint);
    }
}
