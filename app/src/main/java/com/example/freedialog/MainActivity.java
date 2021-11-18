package com.example.freedialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nhcz500.freedialog.FreeCusDialog;
import com.nhcz500.freedialog.FreeDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int AXIS_SPECIFIED = 0x0001; //0000 0001
    private static final int AXIS_PULL_BEFORE = 0x0002;  //0000 0010
    private static final int AXIS_PULL_AFTER = 0x0004; // 0000 0100
    private static final int AXIS_PULL_ALIGN = 0x0008; // 0000 1000
    private static final int AXIS_X_SHIFT = 0; //0000 0000
    private static final int AXIS_Y_SHIFT = 4; // 0000 0100
    public static final int ALIGN_LEFT = (AXIS_PULL_ALIGN|AXIS_SPECIFIED)<<AXIS_X_SHIFT; // 0000 0011  (3)
    public static final int ALIGN_RIGHT = (AXIS_PULL_ALIGN|AXIS_PULL_BEFORE)<<AXIS_X_SHIFT; // 0000 1010  (9)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        System.out.println(">>>>>>>"+ALIGN_RIGHT);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextDialog.newInstance("继承FreeCusDialog的textDialog")
                        .setCanDrag(true)
                        .show(getSupportFragmentManager(),"text");
            }
        });

        findViewById(R.id.tv_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListDialog()
                        .setDimAmount(0)
                        .show(getSupportFragmentManager(),"list");
            }
        });

        findViewById(R.id.tv_text_free).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FreeDialog.Builder(R.layout.dialog_test_match) {
                    @Override
                    public void onCreateView(Bundle savedInstanceState) {

                    }
                }.setAnchor(findViewById(R.id.tv_text_free),0,0)
                        .show(getSupportFragmentManager(),"text_free");
            }
        });


        findViewById(R.id.tv_list_free).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FreeDialog.Builder(R.layout.dialog_list) {
                    @Override
                    public void onCreateView(Bundle savedInstanceState) {
                        RecyclerView recyclerView=getView(R.id.rv_list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        TestAdapter testAdapter=new TestAdapter();
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<10;i++){
                            list.add(i+"条数据");
                        }
                        testAdapter.submitList(list);
                        recyclerView.setAdapter(testAdapter);
                    }
                }.setAnchor(findViewById(R.id.tv_list_free),0,0).setElevation(5).setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL)
                        .show(getSupportFragmentManager(),"list_free");
            }
        });

        findViewById(R.id.tv_text_exp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExpDialog().setAnchor(findViewById(R.id.tv_text_exp),0,0).setElevation(0).setGravity(Gravity.BOTTOM)
                        .show(getSupportFragmentManager(),"dialogExp");
            }
        });

        findViewById(R.id.tv_text_lazy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没有setAnchor则不需要setTrend 为true  否则动态加载必须设置setTrend
                new LazyListDialog().setTrend(true).setAnchor(findViewById(R.id.tv_text_lazy),0,0).show(getSupportFragmentManager(),"lazyDialog");
            }
        });


        findViewById(R.id.tv_text_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NesDialog().setGravity(Gravity.BOTTOM).show(getSupportFragmentManager(),"NesDialog");
            }
        });


        findViewById(R.id.tv_text_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditDialog().setGravity(Gravity.BOTTOM).show(getSupportFragmentManager(),"NesDialog");
            }
        });


        findViewById(R.id.tv_text_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextDialog.newInstance("Aligndsfdsfdsfdsfdsfdsfdfdsdsfdsfsdfdsfdsf").setElevation(10).setAnchor(findViewById(R.id.tv_text_align_left),0,0)
                        .setGravity(FreeCusDialog.ALIGN_LEFT).show(getSupportFragmentManager(),"align");
            }
        });


        findViewById(R.id.tv_text_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextDialog.newInstance("Aligndsfdsfdsfdsfdsfdsfdfdsdsfdsfsdfdsfdsf").setElevation(15).setAnchor(findViewById(R.id.tv_text_align_right),0,0)
                        .setGravity(FreeCusDialog.ALIGN_RIGHT).show(getSupportFragmentManager(),"align");
            }
        });

    }
}