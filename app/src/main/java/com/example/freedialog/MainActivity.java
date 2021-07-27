package com.example.freedialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        RecyclerView recyclerView=getViewById(R.id.rv_list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        TestAdapter testAdapter=new TestAdapter();
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<10;i++){
                            list.add(i+"条数据");
                        }
                        testAdapter.submitList(list);
                        recyclerView.setAdapter(testAdapter);
                    }
                }.setAnchor(findViewById(R.id.tv_list_free),0,0).setElevation(5).setGravity(Gravity.RIGHT)
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

    }
}