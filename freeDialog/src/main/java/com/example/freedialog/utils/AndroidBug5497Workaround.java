package com.example.freedialog.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.freedialog.FreeCusDialog;

public class AndroidBug5497Workaround {
    // For more information, see https://issuetracker.google.com/issues/36911528
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistView (FreeCusDialog view) {
        new AndroidBug5497Workaround(view);
    }
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean needFlush;
    private boolean isShow;
    private AndroidBug5497Workaround(FreeCusDialog dialog) {
        if (dialog != null&&dialog.getRootView()!=null) {
            mChildOfContent =dialog.getRootView();
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                if(needFlush){
                    needFlush=false;
                    dialog.showJustPan(isShow);
                }
                possiblyResizeChildOfContent(dialog);
            });
            frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
            System.out.println(frameLayoutParams);
        }
    }

    private void possiblyResizeChildOfContent(FreeCusDialog dialog) {
        int usableHeightNow = computeUsableHeight();
        if(usableHeightPrevious==0){
            usableHeightPrevious = usableHeightNow;
        }
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致 //将计算的可视高度设置成视图的高度
            int diff=usableHeightPrevious-usableHeightNow;
            frameLayoutParams.bottomMargin = diff>0?diff:0;
            mChildOfContent.requestLayout();
            needFlush=true;
            isShow=usableHeightPrevious>usableHeightNow;
            //请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        //计算视图可视高度
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
    }
}