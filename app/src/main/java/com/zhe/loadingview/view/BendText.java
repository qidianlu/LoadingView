package com.zhe.loadingview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangyr on 2016/5/26.
 */
public class BendText extends View{
    private Context context;
    private String mDefaultText;
    private String mText;
    private int PAINT_TEXTSIZE = 40;
    private final int PAINT_TEXT_BASE = PAINT_TEXTSIZE;
    private int mTextWidth;
    private Paint mTextPaint;
    private int mRecfSpace = 0;//矩形高度
    private final int DEFAULT_RECF_SPACE = 6;//默认画弧线的间距，值越大速度越快，不能超过最大值
    private final int MAX_RECT_SPACE = 36;//最大画弧形的间距(下方)
    private final int MIN_RECT_SPACE = -12;//最大画弧形的间距(上方)
    private final int STATUS_DOWN_CURVE = 0;//向下弯曲状态
    private final int STATUS_UP_CURVE = 1;//向上弯曲状态
    private final int STATUS_FLAT_CURVE = 2;//平坦状态
    private int mCurveStatus = STATUS_FLAT_CURVE;
    private int mTextColor;
    private Path mPath;
    private final int MAX_SRPRING_COUNT = 18;//最大弹动次数
    private int mSpringCount = MAX_SRPRING_COUNT;//当前弹动次数

    public BendText(Context context) {
        this(context,null);
    }

    public BendText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BendText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    private void initPaint(){
        mDefaultText = "会弯曲的文字";
        mText = mDefaultText;
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#666666"));
        mTextPaint.setTextSize(PAINT_TEXTSIZE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextWidth = (int) mTextPaint.measureText(mText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int baseLineSpace = 20;
        setMeasuredDimension(mTextWidth,PAINT_TEXTSIZE+baseLineSpace);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLinePathAndText(canvas);
    }

    public void setTextColor(int color){
        mTextColor = color;
        mTextPaint.setColor(mTextColor);
    }

    public void setText(@StringRes int id){
        mText = context.getResources().getString(id);
    }

    private void drawLinePathAndText(Canvas canvas){
        if(mPath == null){
            mPath = new Path();
            drawLinePath();
        }else{
            drawArcPath();
            mRecfSpace = getRectSpace();
            if(mRecfSpace > MAX_RECT_SPACE){
                mCurveStatus = STATUS_UP_CURVE;
            }else if(mRecfSpace < MIN_RECT_SPACE){
                mCurveStatus = STATUS_DOWN_CURVE;
            }
        }
        if(mSpringCount<MAX_SRPRING_COUNT){
            mSpringCount++;
            invalidate();
        }else{
            reset(canvas);
        }
        canvas.drawTextOnPath(mDefaultText,mPath,0,0,mTextPaint);
    }

    private void reset(Canvas canvas){
        mRecfSpace = 0;
        mCurveStatus = STATUS_FLAT_CURVE;
        drawArcPath();
    }

    /**
     * 动态绘制曲线高度每次绘制6
     * @return
     */
    private int getRectSpace(){
        if(mCurveStatus == STATUS_DOWN_CURVE){
            return mRecfSpace+DEFAULT_RECF_SPACE;
        }else if(mCurveStatus == STATUS_UP_CURVE){
            return mRecfSpace - DEFAULT_RECF_SPACE;
        }else{
            return 0;
        }
    }

    /**
     * 画出直线
     */
    private void drawLinePath(){
        mPath.moveTo(0,PAINT_TEXT_BASE);
        mPath.lineTo(mTextWidth,PAINT_TEXT_BASE);
        mPath.close();
    }

    /***
     * 获取弧线
     */
    private void drawArcPath(){
        mPath.reset();
        mPath.moveTo(0,PAINT_TEXT_BASE);
        mPath.quadTo(0,PAINT_TEXT_BASE,5,PAINT_TEXT_BASE);
        mPath.quadTo(mTextWidth/2,PAINT_TEXT_BASE+mRecfSpace,mTextWidth-5,PAINT_TEXT_BASE);
        mPath.quadTo(mTextWidth*5/6,PAINT_TEXT_BASE,mTextWidth,PAINT_TEXT_BASE);
        mPath.close();
    }

    public void startAni(){
        mSpringCount = 0;
        mCurveStatus = STATUS_DOWN_CURVE;
        invalidate();
    }

}
