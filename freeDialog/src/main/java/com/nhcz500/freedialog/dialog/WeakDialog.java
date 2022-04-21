package com.nhcz500.freedialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nhcz500.freedialog.FreeCusDialog;

import java.lang.ref.WeakReference;

public class WeakDialog extends Dialog {
    private WeakReference<Animation> exitAnimation;
    private WeakReference<onExit> onExit;
    private WeakReference<onKeyTrans> onKey;
    private boolean isLongProgress;
    private WeakReference<Touch> touch;

    public WeakDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(WeakOnCancelListener.proxy(listener));
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(WeakOnDismissListener.proxy(listener));
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        super.setOnShowListener(WeakOnShowListener.proxy(listener));
    }

    @Override
    public void dismiss() {

        if (!isShowing()) {
            return;
        }
        if(onExit!=null&&onExit.get()!=null){
            onExit.get().onExitAnimation();
        }
        if(exitAnimation!=null&&exitAnimation.get()!=null){
            exitAnimation.get().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    WeakDialog.super.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }else{
            super.dismiss();
        }
    }
    public void setExitAnimation(Animation exitAnimation) {
        this.exitAnimation =new WeakReference<>(exitAnimation) ;
    }

    public void setOnExit(onExit exit) {
        this.onExit=new WeakReference<>(exit);
    }

    public void setOnKey(onKeyTrans onKey) {
        this.onKey = new WeakReference<>(onKey);
    }

    public void setTouch(Touch touch){this.touch=new WeakReference<Touch>(touch);}

    public interface onExit{
        void onExitAnimation();
    }


    public interface Touch{
        boolean  dispatchTouchEvent(MotionEvent ev);
        boolean  onTouchEvent(MotionEvent ev);
    }

    public interface onKeyTrans{
        boolean onKeyDown(int keyCode,KeyEvent event);
        boolean onKeyLongPress(int keyCode,KeyEvent event);
        boolean onKeyUp(int keyCode,KeyEvent event);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if(onKey!=null&&onKey.get()!=null){
            if(onKey.get().onKeyDown(keyCode, event)){
                if(event.getRepeatCount()==0){
                    event.startTracking();
                    isLongProgress=false;
                }else{
                    isLongProgress=true;
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, @NonNull KeyEvent event) {
        if(onKey!=null&&onKey.get()!=null){
            if(onKey.get().onKeyLongPress(keyCode, event)){
                return true;
            }
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if(onKey!=null&&onKey.get()!=null){//长按事件是否处理 由子类决定
            if(onKey.get().onKeyUp(keyCode, event)){
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    public boolean isLongProgress() {
        return isLongProgress;
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if(touch.get()!=null&&touch.get().dispatchTouchEvent(ev)){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if(touch.get()!=null&&touch.get().onTouchEvent(event)){
            return true;
        }
        return super.onTouchEvent(event);
    }
}
