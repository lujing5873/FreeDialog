package com.example.freedialog.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.freedialog.R;
import com.example.freedialog.dialog.AddressDialog;
import com.example.freedialog.model.AddressModel;
import com.example.freedialog.model.AddressOne;
import com.example.freedialog.utils.AddressData;
import com.example.freedialog.utils.GsonUtils;
import com.example.freedialog.utils.RxTimerUtil;
import com.nhcz500.freedialog.config.DialogGravity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
            startActivity(new Intent(this, NormalActivity.class));
        });

        findViewById(R.id.tv_2).setOnClickListener(v -> {
            startActivity(new Intent(this, AnchorActivity.class));
        });
        findViewById(R.id.tv_3).setOnClickListener(v -> {
            startActivity(new Intent(this, EditActivity.class));
        });
        findViewById(R.id.tv_4).setOnClickListener(v -> {
            new AddressDialog().setOnSelectAddress(new AddressDialog.OnSelectAddress() {
                @Override
                public void onSelectAddress(AddressModel one, AddressModel two, AddressModel three) {
                    Toast.makeText(MainActivity.this,one.getName()+two.getName()+three.getName(),Toast.LENGTH_LONG).show();
                }
            }).setTrend(true).setGravity(DialogGravity.BOTTOM).show(getSupportFragmentManager(), "select");
        });


        Schedulers.io().createWorker().schedule(() -> {
            StringBuffer text = new StringBuffer();
            try {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(getAssets().open("city.json")));
                String line = null;
                while ((line = buffer.readLine()) != null) {
                    text.append(line);
                }
            } catch (Exception ex) {
                text = new StringBuffer();
            }

            if(TextUtils.isEmpty(text)){
                return;
            }
            AddressData.ADDRESS_DATA.addAll(GsonUtils.getInstance().toList(text.toString(), AddressOne.class));
        });
    }
}