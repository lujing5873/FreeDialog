package com.example.freedialog;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TextDialog extends FreeCusDialog {
    @Override
    public int getLayoutId() {
        return R.layout.dialog_test;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        addViewListener(R.id.tv_dialog);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
