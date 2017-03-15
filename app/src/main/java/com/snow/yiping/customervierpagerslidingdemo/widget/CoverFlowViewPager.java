package com.snow.yiping.customervierpagerslidingdemo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.snow.yiping.customervierpagerslidingdemo.R;
import com.snow.yiping.customervierpagerslidingdemo.widget.pageindicator.PageIndicatorView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class CoverFlowViewPager extends RelativeLayout implements OnPageSelectListener {


    /**
     * 适配器
     */
    public CoverFlowAdapter mAdapter;

    /**
     * 用于左右滚动
     */
    public ViewPager mViewPager;

    /**
     * 需要显示的视图集合
     */
    public List<View> mViewList = new ArrayList<>();

    private OnPageSelectListener listener;
    private final PageIndicatorView pageIndicatorView;

    public CoverFlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.widget_cover_flow, this);
        mViewPager = (ViewPager) findViewById(R.id.vp_conver_flow);
        setViewPagerScroller(true);
        heheda();
        pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(3);
    }

    /**
     * 初始化方法
     */
    private void init() {
        // 构造适配器，传入数据源
        mAdapter = new CoverFlowAdapter(mViewList, getContext());
        // 设置选中的回调
        mAdapter.setOnPageSelectListener(this);
        // 设置适配器
        mViewPager.setAdapter(mAdapter);
        // 设置滑动的监听，因为adpter实现了滑动回调的接口，所以这里直接设置adpter
        mViewPager.addOnPageChangeListener(mAdapter);
        // 自己百度
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        // 设置触摸事件的分发
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 传递给ViewPager 进行滑动处理
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mViewPager.setCurrentItem(5000 * 3);

    }

    /**
     * 设置显示的数据，进行一层封装
     *
     * @param lists
     */
    public void setViewList(List<View> lists) {
        if (lists == null) {
            return;
        }
        mViewList.clear();
        for (View view : lists) {
            FrameLayout layout = new FrameLayout(getContext());
            // 设置padding 值，默认缩小
            layout.setPadding(CoverFlowAdapter.sWidthPadding, CoverFlowAdapter.sHeightPadding, CoverFlowAdapter.sWidthPadding, CoverFlowAdapter.sHeightPadding);
            layout.addView(view);
            mViewList.add(layout);
        }
        /*// 刷新数据
        mAdapter.notifyDataSetChanged();*/

        init();
    }


    /**
     * 当将某一个作为最中央时的回调
     *
     * @param listener
     */
    public void setOnPageSelectListener(OnPageSelectListener listener) {
        this.listener = listener;
    }


    // 显示的回调
    @Override
    public void select(int position) {
        pageIndicatorView.onPageSelected(position);
        if (listener != null) {
            listener.select(position);
        }
    }

    public void setViewPagerScroller(boolean changSpeed) {
        if (mViewPager == null) return;
        int multiple = 0;
        if (changSpeed) {
            multiple = 8;
        } else {
            multiple = 1;
        }
        final int multi = multiple;
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    if (mViewPager != null && dx == mViewPager.getWidth()) {
                        super.startScroll(startX, startY, dx, dy, duration * multi);    // 这里是关键，将duration变长或变短
                    } else {
                        super.startScroll(startX, startY, dx, dy, duration);
                    }

                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }


    public void heheda() {
        try {
            Field field = ViewPager.class.getDeclaredField("mTouchSlop"); // 通过ViewPager类得到字段，不能通过实例得到字段。
            field.setAccessible(true); // 设置Java不检查权限。
            field.setInt(mViewPager, (int) (mViewPager.getWidth()*0.2)); // 设置字段的值，此处应该使用ViewPager实例。设置只有滑动长度大于150px的时候，ViewPager才进行滑动	}	catch (Exception e2)	{		e2.printStackTrace();	}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
