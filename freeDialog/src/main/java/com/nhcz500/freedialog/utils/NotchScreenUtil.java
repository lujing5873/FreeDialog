package com.nhcz500.freedialog.utils;


import android.content.Context;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
public final class NotchScreenUtil {
        public static boolean hasNotStatus(View decorView){
            boolean result=false;
            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                WindowInsets rootWindowInsets=decorView.getRootWindowInsets();
                if(rootWindowInsets==null){
                    return false;
                }
                DisplayCutout displayCutout =rootWindowInsets.getDisplayCutout();
                if(displayCutout==null){
                     return false;
                }    
                int top=displayCutout.getSafeInsetTop();
                result=top!=0;
            }

            return result;
        }

    /**
     * dp转px
     * @param dpValue
     * @return
     */
    private  int dip2px(float dpValue, Context context) {
        final float scale =context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
