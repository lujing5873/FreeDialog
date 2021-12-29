package com.example.freedialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.freedialog.databinding.ActivityAnchorBinding;
import com.example.freedialog.databinding.ActivityEditBinding;
import com.example.freedialog.dialog.EditDialog;
import com.example.freedialog.dialog.EditWarpDialog;
import com.example.freedialog.dialog.ExpDialog;
import com.example.freedialog.dialog.LazyListDialog;
import com.example.freedialog.dialog.ListDialog;
import com.nhcz500.freedialog.FreeDialog;
import com.nhcz500.freedialog.config.DialogGravity;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        bind=  DataBindingUtil.setContentView(this,R.layout.activity_edit);

        bind.tv1.setOnClickListener(v -> {
            new EditDialog().setGravity(DialogGravity.BOTTOM).show(getSupportFragmentManager(),"editDialog");
        });

        bind.tv2.setOnClickListener(v -> {
            new EditWarpDialog().setGravity(DialogGravity.BOTTOM).setTrend(true).show(getSupportFragmentManager(),"editDialog");
        });

    }
}