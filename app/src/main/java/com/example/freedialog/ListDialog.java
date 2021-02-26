package com.example.freedialog;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ListDialog extends FreeCusDialog {
    float xDown, yDown;
    boolean isLongClickModule = false;
    float lastX,lastY;
    @Override
    public int getLayoutId() {
        return R.layout.dialog_list;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        RecyclerView recyclerView=findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TestAdapter testAdapter=new TestAdapter();
        List<String> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add("第"+i+"条数据");
        }
        testAdapter.submitList(list);
        recyclerView.setAdapter(testAdapter);
    }

}
