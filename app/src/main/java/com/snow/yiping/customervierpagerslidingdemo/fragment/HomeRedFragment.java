package com.snow.yiping.customervierpagerslidingdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.snow.yiping.customervierpagerslidingdemo.R;
import com.snow.yiping.customervierpagerslidingdemo.widget.CircleDisplayView;
import com.snow.yiping.customervierpagerslidingdemo.widget.CoverFlowViewPager;
import com.snow.yiping.customervierpagerslidingdemo.widget.InstrumentBoardView;
import com.snow.yiping.customervierpagerslidingdemo.widget.OnPageSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leiiiooo on 2017/2/21.
 */

public class HomeRedFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeRedFragment";
    private CoverFlowViewPager mCover;
    private Handler handler;
    private Runnable runnable;
    private long LOOP_DELAYED_TIME = 5000;

    public static HomeRedFragment newInstance() {
        HomeRedFragment fragment = new HomeRedFragment();
        return fragment;
    }

    private View mContainerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContainerView == null) {
            mContainerView = inflater.inflate(R.layout.fragment_red_home_layout, container, false);
            initView(mContainerView);
        }
        return mContainerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View containerView) {
        mCover = (CoverFlowViewPager) containerView.findViewById(R.id.cover_flow_view_pager);
//        containerView.findViewById(R.id.rl_phone_cooling).setOnClickListener(this);
//        containerView.findViewById(R.id.rl_phone_saving).setOnClickListener(this);
//        containerView.findViewById(R.id.rl_file_clean).setOnClickListener(this);
//        containerView.findViewById(R.id.rl_speed_boost).setOnClickListener(this);
        handler = new Handler();
        //  设置显示的数据
        mCover.setViewList(getListView());
        // 设置滑动的监听，该监听为当前页面滑动到中央时的索引

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

    private void startNextViewPager() {
        if (handler != null || runnable != null) {
            handler.postDelayed(runnable, LOOP_DELAYED_TIME);
        }
    }



    @Override
    public void onClick(View view) {


    }


    public List<View> getListView() {
        //        getWindowManager();
        List<View> list = new ArrayList<>();
        InstrumentBoardView cpuInsView = new InstrumentBoardView(getContext(), CircleDisplayView.TYPE_YELLOW_CPU);
        InstrumentBoardView storageInsView = new InstrumentBoardView(getContext(), CircleDisplayView.TYPE_RED_STORAGE);
        InstrumentBoardView ramInsView = new InstrumentBoardView(getContext(), CircleDisplayView.TYPE_GREEN_RAM);
        InstrumentBoardView cpuInsView1 = new InstrumentBoardView(getContext(), CircleDisplayView.TYPE_YELLOW_CPU);
        InstrumentBoardView storageInsView1 = new InstrumentBoardView(getContext(), CircleDisplayView.TYPE_RED_STORAGE);
        InstrumentBoardView ramInsView1 = new InstrumentBoardView(getContext(), CircleDisplayView.TYPE_GREEN_RAM);
        cpuInsView1.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager,CircleDisplayView.TYPE_YELLOW_CPU));
        storageInsView1.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager,CircleDisplayView.TYPE_RED_STORAGE));
        ramInsView1.setViewOnclickListen( new MyBoardViewListen(mCover.mViewPager,CircleDisplayView.TYPE_GREEN_RAM));
        cpuInsView.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager, CircleDisplayView.TYPE_YELLOW_CPU));
        storageInsView.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager, CircleDisplayView.TYPE_RED_STORAGE));
        ramInsView.setViewOnclickListen(new MyBoardViewListen(mCover.mViewPager, CircleDisplayView.TYPE_GREEN_RAM));
        list.add(cpuInsView);
        list.add(storageInsView);
        list.add(ramInsView);
        list.add(cpuInsView1);
        list.add(storageInsView1);
        list.add(ramInsView1);

        return list;
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

    @Override
    public void onResume() {
        super.onResume();
        refreshViewPager(-1, false);
    }

//    @Override
//    protected void onVisibilityChangedToUser(boolean isVisibleToUser, boolean isHappenedInSetUserVisibleHintMethod) {
//        super.onVisibilityChangedToUser(isVisibleToUser, isHappenedInSetUserVisibleHintMethod);
//        if (isVisibleToUser) {
//            Analytics.sendEvent(AnalyticsEvents.HomeRed.MainShowCount, null, null);
//            handler.removeCallbacks(runnable);
//            startNextViewPager();
//        } else {
//            if (handler != null && runnable != null) {
//                handler.removeCallbacks(runnable);
//            }
//        }
//
//    }

    class MyBoardViewListen implements InstrumentBoardView.BoardViewListen {
        private int mType;
        private final ViewPager mViewPager;

        public MyBoardViewListen(ViewPager viewPager, int type) {
            mType = type;
            mViewPager = viewPager;
        }

        @Override
        public void viewOnclick() {
            Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();


        }
    }
}