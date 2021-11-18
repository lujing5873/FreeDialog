package com.example.freedialog;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.core.widget.NestedScrollView;
import com.nhcz500.freedialog.FreeCusDialog;
import com.nhcz500.freedialog.utils.AndroidBug5497Workaround;


public class EditDialog extends FreeCusDialog {

    NestedScrollView nestedScrollView;
    @Override
    public int getLayoutId() {
        return R.layout.dialog_edit;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        setGravity(BOTTOM);
        AndroidBug5497Workaround.assistView(this);
        nestedScrollView=getView(R.id.edit_ns);
    }

    @Override
    public void showJustPan(boolean isShow) {
//        if(isShow){
//            //显示键盘的时候 滚动到底部
//            nestedScrollView.smoothScrollTo(0,nestedScrollView.getChildAt(0).getHeight());
//        }

    }
}
