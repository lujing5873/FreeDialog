package com.example.freedialog;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.nhcz500.freedialog.FreeCusDialog;

public class TextDialog extends FreeCusDialog {
    public static TextDialog newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("test",text);
        TextDialog fragment = new TextDialog();
        fragment.setArguments(args);
        return fragment;
    }
    TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_test;
    }
    @Override
    protected void createView(Bundle savedInstanceState) {
        String text=getArguments().getString("test","def");
        textView=getView(R.id.tv_dialog);
        textView.setText(text);
        addViewListener(R.id.tv_dialog);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_dialog:
                System.out.println("点击了textview");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(!dialog.isLongProgress()){
            System.out.println("onKeyUp"+keyCode);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        System.out.println("onKeyLongPress"+keyCode);
        return super.onKeyLongPress(keyCode, event);
    }
}
