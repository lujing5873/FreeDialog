package com.example.freedialog.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Printer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

public class SelectRv extends RecyclerView {
    private LinearLayoutManager layoutManager;
    private int mItemH;
    private Paint mHoloPaint;
    private int mWheelSize=4;
    private int itemWith=0;

    public SelectRv(@NonNull Context context) {
        this(context,null);
    }

    public SelectRv(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SelectRv(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHoloPaint = new Paint();
        mHoloPaint.setStrokeWidth(1);
        mHoloPaint.setColor(Color.parseColor("#E1E1E1"));


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                itemWith=getWidth();
                if (getChildCount() > 0 && mItemH == 0) {
                    mItemH = getChildAt(0).getHeight();if (mItemH != 0) {
                        ViewGroup.LayoutParams params = getLayoutParams();
                        params.height = mItemH * mWheelSize;
                        requestLayout();
                    }
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        layoutManager= (LinearLayoutManager) getLayoutManager();
    }

    @Override
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        int first=layoutManager.findFirstVisibleItemPosition();
        int center=  ((mWheelSize-1)>> 1)+first;
        int position=getChildAdapterPosition(child);
        if(position==center){
            child.setAlpha(1.0F);
        }else{
            child.setAlpha(0.4F);
        }
        return super.drawChild(canvas, child, drawingTime);
    }




    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mItemH != 0) {
            int size = (mWheelSize-1) >> 1;
            canvas.drawLine(0, mItemH * size, itemWith, mItemH
                    * size, mHoloPaint);
            canvas.drawLine(0, mItemH * (size + 1), itemWith, mItemH
                    * (size + 1), mHoloPaint);
        }
    }

    public int getSelect(){
        return layoutManager==null ?-1:layoutManager.findFirstVisibleItemPosition();
    }

}
