package com.snow.yiping.customervierpagerslidingdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.snow.yiping.customervierpagerslidingdemo.widget.CircleDisplayView;
import com.snow.yiping.customervierpagerslidingdemo.widget.CoverFlowViewPager;
import com.snow.yiping.customervierpagerslidingdemo.widget.InstrumentBoardView;
import com.snow.yiping.customervierpagerslidingdemo.widget.OnPageSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final long LOOP_DELAYED_TIME = 5000;//延时时间 5秒
    CoverFlowViewPager mCover;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        initView();
    }

    private void initView() {
        mCover = (CoverFlowViewPager) findViewById(R.id.cover_flow_view_pager);
        mCover.setViewList(getListView());
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                mCover.mViewPager.setCurrentItem(mCover.mViewPager.getCurrentItem() + 1, true);
            }
        };
        startNextViewPager();
        mCover.setOnPageSelectListener(new OnPageSelectListener() {
            @Override
            public void select(int position) {
                refreshViewPager(mCover.mViewPager.getCurrentItem(), true);
                handler.removeCallbacks(runnable);
                startNextViewPager();
            }
        });

    }


    public List<View> getListView() {
        List<View> list = new ArrayList<>();
        InstrumentBoardView cpuInsView = new InstrumentBoardView(this, CircleDisplayView.TYPE_YELLOW_CPU);
        InstrumentBoardView storageInsView = new InstrumentBoardView(this, CircleDisplayView.TYPE_RED_STORAGE);
        InstrumentBoardView ramInsView = new InstrumentBoardView(this, CircleDisplayView.TYPE_GREEN_RAM);
        InstrumentBoardView cpuInsView1 = new InstrumentBoardView(this, CircleDisplayView.TYPE_YELLOW_CPU);
        InstrumentBoardView storageInsView1 = new InstrumentBoardView(this, CircleDisplayView.TYPE_RED_STORAGE);
        InstrumentBoardView ramInsView1 = new InstrumentBoardView(this, CircleDisplayView.TYPE_GREEN_RAM);
        list.add(cpuInsView);
        list.add(storageInsView);
        list.add(ramInsView);
        list.add(cpuInsView1);
        list.add(storageInsView1);
        list.add(ramInsView1);
        return list;
    }

    /**
     * 跳转到下一页，实现轮播效果
     */
    private void startNextViewPager() {
        if (handler != null || runnable != null) {
            handler.postDelayed(runnable, LOOP_DELAYED_TIME);
        }
    }

    /**
     * 刷新view里面的数据
     * @param position
     * @param isAnim
     */
    public void refreshViewPager(int position, boolean isAnim) {
        if (mCover.mViewPager == null) return;
        if (position < 0) {
            position = mCover.mViewPager.getCurrentItem();
        }
        position = position % 3;
        FrameLayout frameLayout = (FrameLayout) (mCover.mViewList.get(position));
        InstrumentBoardView instrumentBoardView = (InstrumentBoardView) frameLayout.getChildAt(0);
        instrumentBoardView.circleDisplayView.refreshView(isAnim);
    }

}
