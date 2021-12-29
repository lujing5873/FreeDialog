package com.example.freedialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nhcz500.freedialog.FreeCusDialog;
import com.nhcz500.freedialog.FreeDialog;
import com.nhcz500.freedialog.config.DialogGravity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_1).setOnClickListener(v -> {
            startActivity(new Intent(this,NormalActivity.class));
        });

        findViewById(R.id.tv_2).setOnClickListener(v -> {
            startActivity(new Intent(this,AnchorActivity.class));
        });
        findViewById(R.id.tv_3).setOnClickListener(v -> {
            startActivity(new Intent(this,EditActivity.class));
        });
    }
}