package com.example.freedialog;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LazyListDialog extends FreeCusDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_lazy_list;
    }
    TestAdapter testAdapter;
    RecyclerView recyclerView;
    @Override
    protected void createView(Bundle savedInstanceState) {
        recyclerView=getView(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testAdapter=new TestAdapter();
        List<String> list=new ArrayList<>();
        testAdapter.submitList(list);
        recyclerView.setAdapter(testAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);//模拟网络加载数据
                    List<String> list=new ArrayList<>();
                    for(int i=0;i<5;i++){
                        list.add("第"+i+"条数据");
                    }
                    testAdapter.submitList(list);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
