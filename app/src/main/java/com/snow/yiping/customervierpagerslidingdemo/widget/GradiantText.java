package com.snow.yiping.customervierpagerslidingdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.snow.yiping.customervierpagerslidingdemo.R;


/**
 * 横向渐变textview xml 定义起始颜色，和结尾颜色
 * Created by y on 2017/3/5.
 */

public class GradiantText extends AppCompatTextView {
    private Rect mTextBound = new Rect();
    private int startColor;
    private int endColor;

    public GradiantText(Context context) {
        super(context);
    }

    public GradiantText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.GradiantText, defStyleAttr, 0);
        startColor = a.getColor(R.styleable.GradiantText_startColor, Color.parseColor("#DC0043"));
        endColor = a.getColor(R.styleable.GradiantText_endColor, Color.parseColor("#E83B4C"));
        a.recycle();
    }

    public GradiantText(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.GradiantText, 0, 0);
        startColor = a.getColor(R.styleable.GradiantText_startColor, Color.parseColor("#DC0043"));
        endColor = a.getColor(R.styleable.GradiantText_endColor, Color.parseColor("#E83B4C"));
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mViewWidth = getMeasuredWidth();
        String mTipText = getText().toString();
        getPaint().getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        LinearGradient mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                startColor, endColor, Shader.TileMode.REPEAT);
        getPaint().setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, getPaint());

    }
}
