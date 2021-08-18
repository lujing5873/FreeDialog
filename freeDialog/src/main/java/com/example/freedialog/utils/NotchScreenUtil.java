package com.example.freedialog.utils;


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
                int top=displayCutout.getSafeInsetTop();
                result=top!=0;
            }

            return result;
        }
}