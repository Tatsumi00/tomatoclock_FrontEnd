package com.njupt.qmj.tomatotodo.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import static com.njupt.qmj.tomatotodo.Util.DensityUtil.dip2px;

public class CircleBackGroundSpan implements LineBackgroundSpan {
        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
            Paint paint = new Paint();
            paint.setAntiAlias(true); //消除锯齿
            paint.setStyle(Paint.Style.STROKE);//绘制空心圆或 空心矩形
            float ringWidth = dip2px(1);//圆环宽度
            paint.setColor(Color.parseColor("#056f00"));
            paint.setStrokeWidth(ringWidth);
            c.drawCircle((right - left) / 2, (bottom - top) / 2, dip2px(14), paint);
        }
    }

