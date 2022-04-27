package com.example.freedialog.weight;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TopLinearSnapHelper  extends LinearSnapHelper {
    private OrientationHelper mVerticalHelper;
    private OrientationHelper mHorizontalHelper;

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToTop(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToTop(targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }
    private int distanceToTop(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView);
    }


    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper helper=null;
        if (layoutManager.canScrollHorizontally()) {
            helper= getHorizontalHelper(layoutManager);
        }else{
            helper= getVerticalHelper(layoutManager);
        }


        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        View closestChild=null;
        View first=layoutManager.getChildAt(0);
        int itemHalf=helper.getDecoratedMeasurement(first)/2;  //获取item一半的位置
        int itemTop=helper.getDecoratedStart(first);

        closestChild=first;
        if(Math.abs(itemTop)>itemHalf){ //滑动超过一半 去下一个
            if(childCount>1) { //必须大于1条
                closestChild = layoutManager.getChildAt(1);
            }
        }
        return closestChild;
    }



}
