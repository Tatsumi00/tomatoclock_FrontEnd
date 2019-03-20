package com.njupt.qmj.tomatotodo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.njupt.qmj.tomatotodo.R;

import static com.njupt.qmj.tomatotodo.Util.DensityUtil.dip2px;

public class CountdownView extends View {

    private int maxValue;

    private int detectValue = 0;

    private float alphaAngle;

    private int innerCircleColor;

    private int outsideCircleColor;

    private int centerTextColor = Color.WHITE;

    private int centerTextSize = 40;

    private int circleWidth;

    private Paint innercirclePaint,textPaint,outsidecirclePaint;

    private int result,count,repeatCount;

    private String percent;

    private onFinishListener listener;

    public CountdownView(Context context){
        this(context,null);
    }

    public CountdownView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Countdownview, defStyleAttr, 0);
        int n = ta.getIndexCount();

        for (int i = 0;i<n;i++){
            int attr = ta.getIndex(i);
            switch(attr){
                case R.styleable.Countdownview_countDown_innerCircleColor:
                    innerCircleColor = ta.getColor(attr, Color.LTGRAY);
                    break;
                case R.styleable.Countdownview_countDown_outsideCircleColor:
                    outsideCircleColor = ta.getColor(attr,Color.WHITE);
                    break;
                case R.styleable.Countdownview_countDown_centerTextSize:
                    centerTextSize = ta.getDimensionPixelSize(attr,(int) dip2px(40));
                    break;
                case R.styleable.Countdownview_countDown_circleWidth:
                    circleWidth = ta.getDimensionPixelSize(attr, (int) dip2px(6f));
                    break;
                case R.styleable.Countdownview_countDown_centerTextColor:
                    centerTextColor = ta.getColor(attr, Color.WHITE);
                    break;
                default:
                    break;
            }
        }
        ta.recycle();

        innercirclePaint = new Paint();
        innercirclePaint.setAntiAlias(true);
        innercirclePaint.setDither(true);
        innercirclePaint.setStrokeWidth(circleWidth);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);

        outsidecirclePaint = new Paint();
        outsidecirclePaint.setAntiAlias(true);
        outsidecirclePaint.setDither(true);
        outsidecirclePaint.setStrokeWidth(circleWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height =MeasureSpec.getSize(heightMeasureSpec);
        int minWidth = Math.min(widthPixels,width);
        int minHeight = Math.min(heightPixels,height);
        setMeasuredDimension(Math.min(minWidth,minHeight),Math.min(minWidth,minHeight));
    }

    @Override
    protected void onDraw(Canvas canvas){
        int center = this.getWidth()/2;
        int radius = center - circleWidth / 2;
        drawInnerCircle(canvas,center,radius);
        drawText(canvas,center);
        drawOutsideCircle(canvas,center,radius);
    }

    private void drawOutsideCircle(Canvas canvas, int center, int radius){
        if (detectValue <= 1){
            maxValue = result;
            detectValue ++;
        }
        outsidecirclePaint.setShader(null);
        outsidecirclePaint.setStyle(Paint.Style.STROKE);
        outsidecirclePaint.setColor(outsideCircleColor);
        outsidecirclePaint.setStrokeCap(Paint.Cap.ROUND);
        outsidecirclePaint.setShadowLayer(10,10,10,Color.LTGRAY);
        RectF oval = new RectF(center - radius, center - radius,center + radius,center + radius);
        if (detectValue == 2){
            alphaAngle = count * 360.0f/(maxValue)* 1.0f;
            canvas.drawArc(oval, 270, alphaAngle,false,outsidecirclePaint);
        }

    }


    private void drawInnerCircle(Canvas canvas, int center, int radius){
        innercirclePaint.setShader(null);
        innercirclePaint.setColor(innerCircleColor);
        innercirclePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(center,center,radius,innercirclePaint);
    }

    private void drawText(Canvas canvas, int center){
        if (result == -1){
            percent = "第" + repeatCount + "次循环完成";
            textPaint.setTextSize(centerTextSize);
        }
        else if (result==0){
            percent = "完成";
            textPaint.setTextSize(centerTextSize);
        }else if (result > 0){
            percent = (result / 60 < 10 ? "0" + result / 60 : result / 60) + ":" + (result % 60 < 10 ? "0" + result % 60 : result % 60);
            textPaint.setTextSize(centerTextSize + centerTextSize/3);
        }
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(centerTextColor);

        textPaint.setStrokeWidth(0);
        Rect bounds = new Rect();
        textPaint.getTextBounds(percent, 0, percent.length(), bounds);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(percent, center, baseline, textPaint);
    }

    public void onUpdateTime(int duration, int count){
        this.count = count - 1;
        result = duration;
        if (result == 0 && CountdownView.this.listener != null){
            CountdownView.this.listener.onFinish();
        }
        invalidate();
    }

    public void clearView(int repeatCount){
        this.repeatCount = repeatCount;
        detectValue = 0;
        result = -1;
        invalidate();
    }



    public interface onFinishListener{
        void onFinish();
    }

    public void setListener(CountdownView.onFinishListener listener){
        this.listener = listener;
    }



}
