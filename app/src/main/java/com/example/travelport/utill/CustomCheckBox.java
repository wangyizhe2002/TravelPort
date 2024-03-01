package com.example.travelport.utill;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class CustomCheckBox extends AppCompatCheckBox {

    private Paint checkPaint;
    private boolean isChecked = false;

    public CustomCheckBox(Context context) {
        super(context);
        init();
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        checkPaint = new Paint();
        checkPaint.setAntiAlias(true);
        checkPaint.setStyle(Paint.Style.STROKE);
        checkPaint.setStrokeWidth(5); // 设置勾勾的线宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isChecked) {
            // 绘制勾勾
            float startX = getWidth() * 0.2f;
            float startY = getHeight() * 0.5f;
            float midX = getWidth() * 0.4f;
            float midY = getHeight() * 0.7f;
            float endX = getWidth() * 0.8f;
            float endY = getHeight() * 0.3f;

            canvas.drawLine(startX, startY, midX, midY, checkPaint);
            canvas.drawLine(midX, midY, endX, endY, checkPaint);
        }
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        invalidate(); // 重新绘制CheckBox
    }
}