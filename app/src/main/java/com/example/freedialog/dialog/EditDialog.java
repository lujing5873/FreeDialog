package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;

import com.example.freedialog.R;
import com.example.freedialog.RxTimerUtil;
import com.nhcz500.freedialog.FreeCusDialog;
import com.nhcz500.freedialog.config.DialogGravity;
import com.nhcz500.freedialog.utils.AndroidBug5497Workaround;


public class EditDialog extends FreeCusDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_edit;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistView(this);
    }

    @Override
    public void showJustPan(boolean isShow) {
        int marginBottom=0;
        ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) rootView.getLayoutParams();
        if(params!=null){
            marginBottom=params.bottomMargin;
        }
        if(isShow){
            View found=rootView.findFocus();
            if(found!=null){
                int diff=rootView.getHeight()-found.getBottom();//去除键盘剩余空间

                if(diff<marginBottom){
                    rootView.scrollTo(0, marginBottom -diff);
                }
            }
        }else{
            rootView.scrollTo(0,0);
        }
    }
}
