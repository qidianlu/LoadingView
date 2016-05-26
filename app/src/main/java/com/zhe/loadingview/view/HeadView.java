package com.zhe.loadingview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
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
    private int mIndex;
    private boolean mSkip = true;
    private final int MAX_HEIGHT = 100;
    private final int DURATION = 600;
    private final int[] mResDrawable={R.drawable.p1,R.drawable.p3,R.drawable.p5,R.drawable.p7};
    private TranslateAnimation translateAnimation;
    private TranslateAnimation backAnimation;
    private RotateAnimation rotateAnimation;
    private AnimationSet animationSet;
    private AnimationSet animationSet2;
    private Context mContext;

    public HeadView(Context context) {
        this(context,null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
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
        translateAnimation = new TranslateAnimation(0,0,0,MAX_HEIGHT);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setFillAfter(true);
        backAnimation = new TranslateAnimation(0,0,MAX_HEIGHT,0);
        backAnimation.setDuration(DURATION);
        backAnimation.setFillAfter(true);

        rotateAnimation = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(DURATION);
        rotateAnimation.setRepeatCount(0);

        animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);

        animationSet2 = new AnimationSet(true);
        animationSet2.addAnimation(rotateAnimation);
        animationSet2.addAnimation(backAnimation);

        translateAnimation.setAnimationListener(animationListener);
        backAnimation.setAnimationListener(animationListener);
        mImage.setAnimation(animationSet);
        animationSet2.setInterpolator(mContext,android.R.anim.decelerate_interpolator);
        animationSet.setInterpolator(mContext,android.R.anim.accelerate_interpolator);
        animationSet.start();

    }

    private void changeIcon(){
        mImage.clearAnimation();
        if(mSkip){
            mIndex = 2;
            mSkip = false;
        }else{
            mIndex = (mIndex == mResDrawable.length-1)?0:mIndex+1;
        }
        mImage.setImageResource(mResDrawable[mIndex]);

    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(animation == translateAnimation){
                mBendText.startAni();
                changeIcon();
                mImage.setAnimation(animationSet2);
                animationSet2.start();
            }else{
                mImage.setAnimation(animationSet);
                animationSet.start();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
