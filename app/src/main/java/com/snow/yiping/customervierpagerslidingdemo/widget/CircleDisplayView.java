package com.snow.yiping.customervierpagerslidingdemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.snow.yiping.customervierpagerslidingdemo.R;
import com.snow.yiping.customervierpagerslidingdemo.utils.CommonUtils;

import java.math.BigDecimal;

import static com.snow.yiping.customervierpagerslidingdemo.utils.CommonUtils.getAvailableInternalMemorySize;
import static com.snow.yiping.customervierpagerslidingdemo.utils.CommonUtils.getTotalInternalMemorySize;


/**
 * TODO: document your custom view class.
 */
public class CircleDisplayView extends View {
    private TextPaint mTextPaint;
    private int baseColor;
    private int confirmColor;
    private int max;
    private int process;
    private Paint paint;
    private int centre;
    private int contentWidth;
    private int contentHeight;
    private Paint paintC;
    private static final int NUMBEROUTSIDE = 100;
    private static final int NUMBERINTERSIDE = 120;
    private float textScal;
    private float p;
    private Context mContext;
    private Paint drawPicPaint;
    private Bitmap loopBitmap;
    private Bitmap pointBitmap;
    private Paint drawOutLoopPaint;
    private Paint drawLoopProcessPaint;
    private Paint drawTextPaint;
    private Paint drawPUnitPaint;
    private Paint drawNamePaint;
    private Paint drawValuePaint;
    private boolean firstGet;
    private Paint drawTextPaintForCPU;
    private Paint drawTitlePaintForCPU;
    private boolean animIsDone;
    private String usedRAM;
    private String totalRAM;

    public CircleDisplayView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CircleDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CircleDisplayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    //由于屏幕适配，所以添加一个系数，对文字进行放大缩小处理
    private float getScalFactor() {
        //  计算与你开发时设定的屏幕大小的纵横比(这里假设你开发时定的屏幕大小是768*1280)
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        float ratioWidth = (float) screenWidth / 768;
        float ratioHeight = (float) screenHeight / 1280;
        return Math.max(ratioWidth, ratioHeight);
    }

    //获得实际文字尺寸
    private int getRelTextSize(int text) {
        return (int) (text * getScalFactor());
    }


    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
//        refreshView(false);
        paint = new Paint();
        paintC = new Paint();
        paint.setAntiAlias(true);
        paintC.setAntiAlias(true);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleDisplayView, defStyle, 0);
        baseColor = a.getColor(R.styleable.CircleDisplayView_baseColor, getResources().getColor(R.color.batteryViewBaseColor));
        confirmColor = a.getColor(R.styleable.CircleDisplayView_confirmColor, getResources().getColor(R.color.batteryViewConfirColor));
        max = a.getInteger(R.styleable.CircleDisplayView_max, 100);
        process = a.getInteger(R.styleable.CircleDisplayView_process, 0);
        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);


    }


    public void setViewType(int type) {
        mType = type;
        refreshView(false);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - paddingLeft - paddingRight;
//        contentHeight = getHeight() - paddingTop - paddingBottom;

        contentHeight = contentWidth;
        centre = getWidth() / 2;
        float percent = (float) process / (float) max;


        // 内环
        drawloop(canvas);

        //外环进度
        drawoutLoop(canvas);


        //锯齿形 进度条
        drawLoopProcess(degrees, canvas);

        //指针
        drawPoint(canvas);


        //画百分比数值
        if (mType == TYPE_YELLOW_CPU) {
            drawDateForCPU(canvas);

        } else {
            drawPowerpercent(canvas);

        }

    }

    private void drawDateForCPU(Canvas canvas) {
        String percent = temperatureNoUnitTransform((int) (degrees * 100));
        canvas.drawText(percent, centre, (float) (centre - ((float) contentWidth / 2 * 0.1)), getDrawTextPaintForCPU());
        canvas.drawText(getTypeName(), centre, (float) (centre + ((float) contentWidth / 2 * 0.35)), getDrawTextTitleForCPU());
        canvas.save();
    }


    //    int COLORRED = Color.parseColor("#FF0000");
    int COLORRED = Color.parseColor("#e02354");
    //    int COLORGREEN = Color.parseColor("#00FF00");
    int COLORGREEN = Color.parseColor("#09bf8d");
    //    int COLORORANGE = Color.parseColor("#FFA500");
    int COLORORANGE = Color.parseColor("#ffa400");
    int COLORBLACK = Color.parseColor("#000000");
    int COLORGRAY = Color.parseColor("#a1a1a1");

    public Paint getDrawTextPaint() {
        if (drawTextPaint == null) {
            drawTextPaint = new Paint();
            drawTextPaint.setAntiAlias(true);
            drawTextPaint.setTextSize(sp2px(getContext(), getRelTextSize(60)));
        }
//        drawTextPaint.setColor(getLvColor());
        drawTextPaint.setColor(COLORBLACK);

        return drawTextPaint;
    }

    public int getLvColor() {
        if (mType == TYPE_GREEN_RAM) {
            if (degrees >= 0.9) {
                return COLORRED;
            } else if (degrees >= 0.6) {
                return COLORORANGE;
            } else {
                return COLORGREEN;
            }
        } else if (mType == TYPE_RED_STORAGE) {
            if (degrees >= 0.9) {
                return COLORRED;
            } else if (degrees >= 0.8) {
                return COLORORANGE;
            } else {
                return COLORGREEN;
            }
        } else {
            if (degrees >= 0.6) {
                return COLORRED;
            } else if (degrees >= 0.5) {
                return COLORORANGE;
            } else {
                return COLORGREEN;
            }
        }

    }

    public Paint getDrawTextPaintForCPU() {
        if (drawTextPaintForCPU == null) {
            drawTextPaintForCPU = new Paint();
            drawTextPaintForCPU.setAntiAlias(true);
            drawTextPaintForCPU.setTextAlign(Paint.Align.CENTER);
            drawTextPaintForCPU.setTextSize(sp2px(getContext(), getRelTextSize(45)));
        }
//        drawTextPaintForCPU.setColor(getLvColor());
        drawTextPaintForCPU.setColor(COLORBLACK);

        return drawTextPaintForCPU;
    }


    public Paint getDrawTextTitleForCPU() {
        if (drawTitlePaintForCPU == null) {
            drawTitlePaintForCPU = new Paint();
            drawTitlePaintForCPU.setAntiAlias(true);
            drawTitlePaintForCPU.setColor(Color.parseColor("#000000"));
            drawTitlePaintForCPU.setTextAlign(Paint.Align.CENTER);
            drawTitlePaintForCPU.setTypeface(Typeface.DEFAULT_BOLD);
            drawTitlePaintForCPU.setTextSize(sp2px(getContext(), getRelTextSize(30)));
        }
        return drawTitlePaintForCPU;


    }

    public Paint getDrawPUnitPaint() {
        if (drawPUnitPaint == null) {
            drawPUnitPaint = new Paint();
            drawPUnitPaint.setAntiAlias(true);
            drawPUnitPaint.setTextSize(sp2px(getContext(), getRelTextSize(26)));
        }
//        drawPUnitPaint.setColor(getLvColor());
        drawPUnitPaint.setColor(COLORBLACK);

        return drawPUnitPaint;

    }

    public Paint getDrawNamePaint() {
        if (drawNamePaint == null) {
            drawNamePaint = new Paint();
            drawNamePaint.setAntiAlias(true);
            drawNamePaint.setTextAlign(Paint.Align.CENTER);
            drawNamePaint.setColor(Color.parseColor("#333333"));
            drawNamePaint.setTextSize(sp2px(getContext(), getRelTextSize(16)));
            drawNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return drawNamePaint;

    }

    public Paint getDrawValuePaint(boolean changColor) {
        if (drawValuePaint == null) {
            drawValuePaint = new Paint();
            drawValuePaint.setAntiAlias(true);
            drawValuePaint.setTextSize(sp2px(getContext(), getRelTextSize(16)));
        }
        if (changColor) {
            drawValuePaint.setColor(getLvColor());
        } else {
            drawValuePaint.setColor(COLORGRAY);
        }
        return drawValuePaint;

    }


    public String getTypeName() {
        if (mType == TYPE_GREEN_RAM) {
            return getResources().getString(R.string.device_ram);
        } else if (mType == TYPE_YELLOW_CPU) {
            return getResources().getString(R.string.cpu);
        } else {
            return getResources().getString(R.string.device_storage);
        }
    }

    private void drawPowerpercent(Canvas canvas) {
        String percent = (int) (degrees * 100) + "";
        float unitWidth = getDrawPUnitPaint().measureText("%");
        float percentWidth = getDrawTextPaint().measureText(percent);
        canvas.drawText(percent, centre - (unitWidth + percentWidth) / 2, (float) (centre - ((float) contentWidth / 2 * 0.04)), getDrawTextPaint());
        canvas.drawText("%", centre + ((unitWidth + percentWidth) / 2 - unitWidth), (float) (centre - ((float) contentWidth / 2 * 0.06)), getDrawPUnitPaint());
        canvas.drawText(getTypeName(), centre, (float) (contentWidth * 0.75), getDrawNamePaint());
        String showDate = usedRAM + "/" + totalRAM;
        float v = getDrawValuePaint(false).measureText(showDate) / 2;
        canvas.drawText(showDate, centre - v, (float) (contentWidth * 0.63), getDrawValuePaint(false));
        canvas.drawText(usedRAM, centre - v, (float) (contentWidth * 0.63), getDrawValuePaint(true));
        canvas.save();
    }


    public void setProcess(float process) {
        if (process <= 1 && process > 0) {
            this.degrees = process;
            invalidate();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }


    public void refreshView(boolean isAnim) {
        if (mType == TYPE_GREEN_RAM) {
            refreshRAM();
        } else if (mType == TYPE_RED_STORAGE) {
            refreshStorage();
        } else if (mType == TYPE_YELLOW_CPU) {
            refreshCPUTem();
        }

        if (!isAnim) return;
        if (!animIsDone) {
            animIsDone = true;
            setProcess(degrees, 1500, 0);
        }

    }

    private void refreshStorage() {
        long cleanSpace = 0;
        long usedStorageL = (getTotalInternalMemorySize() - getAvailableInternalMemorySize() - cleanSpace);
        usedRAM = CommonUtils.FormetFileSize(usedStorageL);
        totalRAM = CommonUtils.FormetFileSize(getTotalInternalMemorySize());
        int percent = (int) ((usedStorageL * 100) / (float) getTotalInternalMemorySize());
        degrees = (float) percent / 100;
    }

    private void refreshCPUTem() {
    }

    private void refreshRAM() {
        degrees = (float) 0.56;
        long totalMemory = (1024 * 1024) * 800;
        long currentMemory = (1024 * 1024) * 300;
        totalRAM = CommonUtils.FormetFileSize(totalMemory);
        usedRAM = CommonUtils.FormetFileSize(currentMemory);
    }

    public void setProcess(float process, long duration, long startDeley) {
        if (process > 1) {
            process = process / 100;
        }
        if (process >= 1) {
            process = 1;
        }
        ValueAnimator va = ValueAnimator.ofFloat(process);
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float aValue = (Float) animation.getAnimatedValue();
                setProcess(aValue);
            }
        });
        va.start();


    }


    /**
     * 获得图片画笔
     *
     * @return
     */
    public Paint getDrawPicPaint() {
        if (drawPicPaint == null) {
            drawPicPaint = new Paint();
            drawPicPaint.setAntiAlias(true);
        }
        return drawPicPaint;
    }

    public static final int TYPE_GREEN_RAM = 2;
    public static final int TYPE_RED_STORAGE = 1;
    public static final int TYPE_YELLOW_CPU = 3;

    public int mType = 1;
    public float degrees = 0.8f;

    /**
     * get bitmap by type
     *
     * @return
     */
    public Bitmap getLoopBitmap() {
        if (loopBitmap == null) {
            loopBitmap = BitmapFactory.decodeResource(getResources(), getLoopBitResId());
            Log.i("snow", "loopBitmap_bytecount:" + loopBitmap.getByteCount());
            Log.i("snow", "loopBitmap_getWidth:" + loopBitmap.getWidth());
            Log.i("snow", "loopBitmap_getHeight:" + loopBitmap.getHeight());

        }
        return loopBitmap;
    }

    /**
     * get resid for loopBitmap
     *
     * @return
     */
    public int getLoopBitResId() {
        if (mType == TYPE_GREEN_RAM) {
            return R.drawable.view_loop_green;
        } else if (mType == TYPE_YELLOW_CPU) {
            return R.drawable.view_loop_yellow;
        } else {
            return R.drawable.view_loop_red;
        }
    }


    /**
     * get resid for pointBitmap
     *
     * @return
     */
    public int getPointBitResId() {
        if (mType == TYPE_GREEN_RAM) {
            return R.drawable.view_point_green;
        } else if (mType == TYPE_YELLOW_CPU) {
            return R.drawable.view_point_yellow;
        } else {
            return R.drawable.view_point_red;
        }
    }

    /**
     * get pointBitmap by type
     *
     * @return
     */
    public Bitmap getPointBitmap() {
        if (pointBitmap == null) {
            pointBitmap = BitmapFactory.decodeResource(getResources(), getPointBitResId());
        }
        return pointBitmap;
    }

    public void drawPoint(Canvas canvas) {
        canvas.save();
        float rote = degrees * 360;
        canvas.rotate(rote, centre, centre);
        RectF rectF = new RectF(0, 0, contentWidth, contentWidth);
        Rect rect = new Rect(0, 0, getPointBitmap().getWidth(), getPointBitmap().getHeight());
        canvas.drawBitmap(getPointBitmap(), rect, rectF, getDrawPicPaint());
        canvas.restore();
    }

    public void drawloop(Canvas canvas) {
        canvas.save();
        float rote = degrees * 360;
        canvas.rotate(rote, centre, centre);
        int margin = (int) (contentHeight * 0.003);
        RectF rectF = new RectF(margin, margin, contentWidth - margin, contentHeight - margin);
        Rect rect = new Rect(0, 0, getLoopBitmap().getWidth(), getLoopBitmap().getHeight());
        canvas.drawBitmap(getLoopBitmap(), rect, rectF, getDrawPicPaint());
        canvas.restore();

    }


    /**
     * get the OutLoop Paint
     */

    public Paint getDrawOutLoopPaint() {
        if (drawOutLoopPaint == null) {
            drawOutLoopPaint = new Paint();
            drawOutLoopPaint.setAntiAlias(true);
            drawOutLoopPaint.setStyle(Paint.Style.STROKE);
            drawOutLoopPaint.setColor(getOutLoopColor());
            drawOutLoopPaint.setStrokeWidth(dp2px(3));
        }
        return drawOutLoopPaint;

    }

    public static final int colorRed = Color.parseColor("#dd2857");
    public static final int colorYellow = Color.parseColor("#EF9C0E");
    public static final int colorGreen = Color.parseColor("#19B28A");

    /**
     * get the outloop paint color
     *
     * @return
     */
    private int getOutLoopColor() {
        if (mType == TYPE_GREEN_RAM) {
            return colorGreen;
        } else if (mType == TYPE_YELLOW_CPU) {
            return colorYellow;
        } else {
            return colorRed;
        }
    }

    private Paint getDrawLoopProcessPaint() {
        if (drawLoopProcessPaint == null) {
            drawLoopProcessPaint = new Paint();
            drawLoopProcessPaint.setAntiAlias(true);
            drawLoopProcessPaint.setStrokeWidth(dp2px(3));
            drawLoopProcessPaint.setColor(Color.parseColor("#50ffffff"));
        }
        return drawLoopProcessPaint;
    }


    private void drawLoopProcess(float percent, Canvas canvas) {
        canvas.save();
        float a = NUMBEROUTSIDE - (NUMBEROUTSIDE * degrees);
        for (int i = 1; i <= NUMBEROUTSIDE; i++) {
            canvas.rotate(-(360f / NUMBEROUTSIDE), getWidth() / 2, getWidth() / 2);
            if (i > a) {
                canvas.drawLine(centre, (float) (contentHeight * 0.93), centre, (float) (contentHeight * 0.868), getDrawLoopProcessPaint());
            }
        }
        canvas.restore();

    }


    private void drawoutLoop(Canvas canvas) {
        canvas.save();
        int margin = (int) (contentWidth * 0.04);
        canvas.drawArc(new RectF(margin, margin, contentHeight - margin, contentHeight - margin), 90, degrees * 360, false, getDrawOutLoopPaint());
        canvas.restore();
    }

    /**
     * dp 转 px
     *
     * @param dp
     * @return px
     */
    public int dp2px(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        return px;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    //气温单位标准化（无单位）
    public static String temperatureNoUnitTransform(double c) {
        return (int) c + "℃";
    }

}