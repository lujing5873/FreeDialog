package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.KeyEvent;

import com.example.freedialog.R;
import com.example.freedialog.databinding.DialogTextBinding;
import com.nhcz500.freedialog.FreeCusDialog;

public class TextDialog extends FreeCusDialog {
    public static TextDialog newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text",text);
        TextDialog fragment = new TextDialog();
        fragment.setArguments(args);
        return fragment;
    }
    private DialogTextBinding bind;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_text;
    }
    @Override
    protected void createView(Bundle savedInstanceState) {
        String text=getArguments().getString("text","");

        bind=DialogTextBinding.bind(rootView);
        bind.tvDialog.setText(text);

        addViewListener(R.id.cancel,R.id.ok);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
