package com.example.freedialog.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.freedialog.R;
import com.example.freedialog.databinding.ActivityNormalBinding;
import com.example.freedialog.dialog.AnimDialog;
import com.example.freedialog.dialog.TextDialog;
import com.nhcz500.freedialog.config.DialogGravity;

public class NormalActivity extends AppCompatActivity {

    private ActivityNormalBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        bind=  DataBindingUtil.setContentView(this, R.layout.activity_normal);



        bind.tv1.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个居中的dialog,遮罩透明度0.5").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv2.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个顶部的dialog,遮罩透明度0.8").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.TOP).setDimAmount(0.8F).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv3.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个底部|左侧的dialog,遮罩透明度0").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.BOTTOM|DialogGravity.LEFT).setDimAmount(0F).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv4.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个左侧的dialog,遮罩透明度0.2").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.LEFT).setDimAmount(0.2F).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv5.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个右侧的dialog,遮罩透明度1").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.RIGHT).setDimAmount(1F).show(getSupportFragmentManager(),"textDialog");
        });


        bind.tv6.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个顶部弹出动画的dialog,遮罩透明度0").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.BOTTOM).setStyle(R.style.DialogPositionBottomToTop).setDimAmount(0F).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv7.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个底部弹出动画的dialog,遮罩透明度0").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.TOP).setStyle(R.style.DialogPositionTopToBottom).setDimAmount(0F).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv8.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个左侧弹出动画的dialog,遮罩透明度0").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.LEFT).setStyle(R.style.DialogPositionLeftToRight).setDimAmount(0F).show(getSupportFragmentManager(),"textDialog");
        });

        bind.tv9.setOnClickListener(v -> {
            TextDialog.newInstance("我是一个右侧弹出动画的dialog,遮罩透明度0").setListener((view, dialog) -> {
                if(view.getId()==R.id.ok){
                    Toast.makeText(NormalActivity.this, "点击了ok", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }).setGravity(DialogGravity.RIGHT).setStyle(R.style.DialogPositionRightToLeft).setDimAmount(0F).show(getSupportFragmentManager(),"textDialog");
        });


        bind.tv10.setOnClickListener(v -> {
            new AnimDialog().setGravity(DialogGravity.RIGHT).setStyle(R.style.DialogPositionRightToLeft).setDimAmount(0F).show(getSupportFragmentManager(),"animDialog");
        });

    }
}