package com.optimum.Avica.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DonutChartView extends View {
    private Paint paint;
    private RectF rectF;
    private int[] values;
    private int[] colors;

    public DonutChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(60);
        rectF = new RectF();
    }

    public void setValues(int[] values) {
        this.values = values;
        invalidate();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (values == null || colors == null) return;

        float total = 0;
        for (int value : values) total += value;

        float startAngle = -90;
        float gapAngle = 20;
        float sweepAngle;

        int width = getWidth();
        int height = getHeight();

        rectF.set(30, 30, width - 30, height - 30);

        for (int i = 0; i < values.length; i++) {
            paint.setColor(colors[i]);
            sweepAngle = (values[i] / total) * (360 - gapAngle * values.length);
            canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
            startAngle += sweepAngle + gapAngle;
        }
    }
}
