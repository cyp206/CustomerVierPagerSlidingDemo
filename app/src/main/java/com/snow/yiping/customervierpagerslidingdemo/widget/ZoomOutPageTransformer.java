package com.snow.yiping.customervierpagerslidingdemo.widget;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by y on 2017/3/3.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.6f;
    private static final float MIN_ALPHA = 0.5f;

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);

            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);


    }
}