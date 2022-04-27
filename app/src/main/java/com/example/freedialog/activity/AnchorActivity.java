package com.example.freedialog.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.freedialog.R;
import com.example.freedialog.databinding.ActivityAnchorBinding;
import com.example.freedialog.dialog.ExpDialog;
import com.example.freedialog.dialog.LazyListDialog;
import com.example.freedialog.dialog.ListDialog;
import com.nhcz500.freedialog.FreeDialog;
import com.nhcz500.freedialog.config.DialogGravity;

public class AnchorActivity extends AppCompatActivity {

    private ActivityAnchorBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        bind=  DataBindingUtil.setContentView(this, R.layout.activity_anchor);



        bind.tv1.setOnClickListener(v -> {
            new FreeDialog.Builder(R.layout.dialog_match) {
                @Override
                public void onCreateView(Bundle savedInstanceState) {

                }
            }.setGravity(DialogGravity.BOTTOM).setAnchor(bind.tv1,0,0).show(getSupportFragmentManager(),"matchDialog");
        });


        bind.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListDialog().setGravity(DialogGravity.BOTTOM).setAnchor(bind.tv2,0,0).show(getSupportFragmentManager(),"listDialog");
            }
        });

        bind.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LazyListDialog().setGravity(DialogGravity.BOTTOM).setTrend(true).setAnchor(bind.tv3,0,0).show(getSupportFragmentManager(),"listDialog");
            }
        });

        bind.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExpDialog().setGravity(DialogGravity.BOTTOM).setAnchor(bind.tv4,0,0).show(getSupportFragmentManager(),"listDialog");
            }
        });
    }
}