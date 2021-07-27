package com.example.freedialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class WeakDialog extends Dialog {
    private WeakReference<Animation> exitAnimation;
    private WeakReference<onExit> onExit;
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

    public interface onExit{
        void onExitAnimation();
    }

}
