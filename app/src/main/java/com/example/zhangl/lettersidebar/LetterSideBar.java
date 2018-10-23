package com.example.zhangl.lettersidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class LetterSideBar extends View {

    private Paint mPaint;

    private String[] mLetter = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    //当前触摸字母
    private String mCurrentTouchLetter;

    public LetterSideBar(Context context) {
        this(context,null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(26);
        mPaint.setColor(Color.RED);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算宽度

        int textWidth = (int) mPaint.measureText("A");


        int width = getPaddingLeft() + getPaddingRight() + textWidth;

        //高度
        int height = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画26 个字母


        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) /mLetter.length;




        for (int i = 0;i < mLetter.length;i++) {

            //中心位置
            int letterCenterY = i * itemHeight + itemHeight/2 + getPaddingTop();
            //基线
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;

            int textWith = (int) mPaint.measureText(mLetter[i]);
            int x = getWidth()/2 - textWith/2;

            //当前字母  高亮
            if (mLetter[i].equals(mCurrentTouchLetter)){
                mPaint.setColor(Color.RED);
                canvas.drawText(mLetter[i],x,baseLine,mPaint);

            }else {
                mPaint.setColor(Color.BLACK);
                canvas.drawText(mLetter[i],x,baseLine,mPaint);
            }



        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算出当前触摸字母
                float currentMoveY = event.getY();

                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) /mLetter.length;


                int currentPosition = (int) (currentMoveY / itemHeight);

                if (currentPosition < 0){
                    currentPosition = 0;
                }

                if (currentPosition >=  mLetter.length - 1){
                    currentPosition = mLetter.length - 1;

                }
                mCurrentTouchLetter = mLetter[currentPosition];

                Log.e("aaa",mCurrentTouchLetter);
                if (null != mCurrentTouchLetter)
                mListener.touch(mCurrentTouchLetter,true);

                //重新绘制
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mCurrentTouchLetter = "";
                mListener.touch(mCurrentTouchLetter,false);
                invalidate();
                break;


        }


        return true;




    }





    private TouchLetterListener mListener;
    public void setOnLetterTouchListener(TouchLetterListener listener){

        this.mListener = listener;
    }

    public interface TouchLetterListener{

         void touch(CharSequence letter,boolean isTouch);

    }


}
