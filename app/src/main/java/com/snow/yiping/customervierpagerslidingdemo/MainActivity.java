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
    private static final long LOOP_DELAYED_TIME = 5000;
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
//                mCover.setViewPagerScroller(true);
                mCover.mViewPager.setCurrentItem(mCover.mViewPager.getCurrentItem() + 1, true);
//                mCover.setViewPagerScroller(false);

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
        //        getWindowManager();
        List<View> list = new ArrayList<>();
        InstrumentBoardView cpuInsView = new InstrumentBoardView(this, CircleDisplayView.TYPE_YELLOW_CPU);
        InstrumentBoardView storageInsView = new InstrumentBoardView(this, CircleDisplayView.TYPE_RED_STORAGE);
        InstrumentBoardView ramInsView = new InstrumentBoardView(this, CircleDisplayView.TYPE_GREEN_RAM);
        InstrumentBoardView cpuInsView1 = new InstrumentBoardView(this, CircleDisplayView.TYPE_YELLOW_CPU);
        InstrumentBoardView storageInsView1 = new InstrumentBoardView(this, CircleDisplayView.TYPE_RED_STORAGE);
        InstrumentBoardView ramInsView1 = new InstrumentBoardView(this, CircleDisplayView.TYPE_GREEN_RAM);
//        cpuInsView1.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager,CircleDisplayView.TYPE_YELLOW_CPU));
//        storageInsView1.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager,CircleDisplayView.TYPE_RED_STORAGE));
//        ramInsView1.setViewOnclickListen( new MyBoardViewListen(mCover.mViewPager,CircleDisplayView.TYPE_GREEN_RAM));
//        cpuInsView.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager, CircleDisplayView.TYPE_YELLOW_CPU));
//        storageInsView.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager, CircleDisplayView.TYPE_RED_STORAGE));
//        ramInsView.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager, CircleDisplayView.TYPE_GREEN_RAM));
        list.add(cpuInsView);
        list.add(storageInsView);
        list.add(ramInsView);
        list.add(cpuInsView1);
        list.add(storageInsView1);
        list.add(ramInsView1);

        return list;
    }


    private void startNextViewPager() {
        if (handler != null || runnable != null) {
            handler.postDelayed(runnable, LOOP_DELAYED_TIME);
        }
    }

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
