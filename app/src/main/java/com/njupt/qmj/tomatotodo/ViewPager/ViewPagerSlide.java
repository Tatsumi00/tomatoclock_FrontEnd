package com.njupt.qmj.tomatotodo.ViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class ViewPagerSlide extends ViewPager {

    private boolean isSlide = false;

    public void setSlide(boolean slide) {
        isSlide = slide;
    }
    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSlide;
    }
}
