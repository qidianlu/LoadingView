package com.zhe.loadingview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhe.loadingview.R;

/**
 * Created by zhangyr on 2016/5/26.
 */
public class HeadView extends FrameLayout{

    private ImageView mImage;
    private BendText mBendText;
    private final int MAX_HEIGHT = 100;
    private final int DURATION = 600;
    private final int[] mResDrawable={R.drawable.p1,R.drawable.p3,R.drawable.p5,R.drawable.p7};
    public HeadView(Context context) {
        this(context,null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.head_view,this);
        mImage = (ImageView) findViewById(R.id.head_image);
        mImage.setImageResource(mResDrawable[1]);
        mBendText = (BendText) findViewById(R.id.head_text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,MAX_HEIGHT,0,0);
        mBendText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,(MAX_HEIGHT+mImage.getMeasuredHeight()+mBendText.getMeasuredHeight()));
        loadAnim();
    }

    private void loadAnim(){
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,MAX_HEIGHT);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setFillAfter(true);
        TranslateAnimation backAnimation = new TranslateAnimation(0,0,MAX_HEIGHT,0);
        backAnimation.setDuration(DURATION);
        backAnimation.setFillAfter(true);

    }
}
