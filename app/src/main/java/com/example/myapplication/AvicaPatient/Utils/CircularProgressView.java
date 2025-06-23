package com.example.myapplication.AvicaPatient.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressView extends View {
    private Paint paint;
    private Paint backgroundPaint;
    private RectF rectF;
    private float progress = 0f;

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#0080FF")); // blue progress color
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.LTGRAY);
        backgroundPaint.setStrokeWidth(20);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float padding = 20;
        rectF.set(padding, padding, getWidth() - padding, getHeight() - padding);

        // Background arc
        canvas.drawArc(rectF, -90, 360, false, backgroundPaint);

        // Foreground arc (progress)
        canvas.drawArc(rectF, -90, 360 * (progress / 100f), false, paint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate(); // Redraw view with new progress
    }
}
