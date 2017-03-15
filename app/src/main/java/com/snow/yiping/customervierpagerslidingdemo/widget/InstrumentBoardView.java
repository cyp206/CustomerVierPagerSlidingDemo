package com.snow.yiping.customervierpagerslidingdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.snow.yiping.customervierpagerslidingdemo.R;

import java.util.ArrayList;
import java.util.List;



public class InstrumentBoardView extends RelativeLayout {
    /**
     * 需要显示的视图集合
     */
    private List<View> mViewList = new ArrayList<>();

    public interface BoardViewListen {
        void viewOnclick();
    }

    private BoardViewListen boardViewListen;

    private OnPageSelectListener listener;
    public CircleDisplayView circleDisplayView;

    public InstrumentBoardView(Context context, int circleViewType) {
        super(context, null);
        inflate(context, R.layout.widget_instrument_board, this);
        init(circleViewType);

    }

    public InstrumentBoardView(Context context, AttributeSet attrs, int circleViewType) {
        super(context, attrs);
        inflate(context, R.layout.widget_instrument_board, this);
        init(circleViewType);
    }

    /**
     * 初始化方法
     */
    private void init(int viewType) {

        circleDisplayView = (CircleDisplayView) findViewById(R.id.circle_view);
        circleDisplayView.setViewType(viewType);
//        findViewById(R.id.view_onclick).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (boardViewListen != null) {
//                    boardViewListen.viewOnclick();
//                }
//            }
//        });
    }


    /**
     * 当将某一个作为最中央时的回调
     *
     * @param listener
     */
    public void setOnPageSelectListener(OnPageSelectListener listener) {
        this.listener = listener;
    }

    public void setViewOnclickListen(BoardViewListen boardViewListen) {
        this.boardViewListen = boardViewListen;
    }
}
