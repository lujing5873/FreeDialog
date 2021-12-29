package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.freedialog.R;
import com.nhcz500.freedialog.FreeCusDialog;
import com.nhcz500.freedialog.utils.AndroidBug5497Workaround;


public class EditWarpDialog extends FreeCusDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_edit_warp;
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
        rootView.scrollTo(0,marginBottom);
    }
}
