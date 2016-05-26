package com.zhe.loadingview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
        Paint mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#666666"));
        mTextPaint.setTextSize(PAINT_TEXTSIZE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.
    }

    public void setText(@StringRes int id){
        mText = context.getResources().getString(id);
    }
}
