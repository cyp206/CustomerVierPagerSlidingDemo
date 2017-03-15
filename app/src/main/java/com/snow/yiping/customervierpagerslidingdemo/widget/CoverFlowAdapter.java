package com.snow.yiping.customervierpagerslidingdemo.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * 滚动的适配器
 * Created by alex_mahao on 2016/8/25.
 */
public class CoverFlowAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    /**
     *  默认缩小的padding值
     */
    public static int sWidthPadding;

    public static int sHeightPadding;

    /**
     * 子元素的集合
     */
    private List<View> mViewList;

    private int count = Integer.MAX_VALUE;
    /**
     * 滑动监听的回调接口
     */
    private OnPageSelectListener listener;

    /**
     * 上下文对象
     */
    private Context mContext;

    public CoverFlowAdapter(List<View> mImageViewList, Context context) {
        this.mViewList = mImageViewList;
        mContext = context;
        // 设置padding值
        sWidthPadding = dp2px(0);
        sHeightPadding = dp2px(0);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= mViewList.size();
        if (position<0) {
            position = mViewList.size() + position;
        }
        View view = mViewList.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。

        ViewParent viewParent = view.getParent();
        if (viewParent!=null){
            ViewGroup parent = (ViewGroup)viewParent;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        // 回调选择的接口
        if (listener != null) {
            listener.select(position % 3);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 当将某一个作为最中央时的回调
     *
     * @param listener
     */
    public void setOnPageSelectListener(OnPageSelectListener listener) {
        this.listener = listener;
    }


    /**
     * dp 转 px
     *
     * @param dp
     * @return
     */
    public int dp2px(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());

        return px;
    }



}