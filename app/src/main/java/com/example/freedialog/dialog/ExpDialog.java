package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;
import com.example.freedialog.TestAdapter;
import com.nhcz500.freedialog.FreeCusDialog;

import java.util.ArrayList;
import java.util.List;

public class ExpDialog extends FreeCusDialog {
    RecyclerView recyclerView;
    View bg;
    @Override
    public int getLayoutId() {
        return R.layout.dialog_exp;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        recyclerView=getView(R.id.rv);
        bg=getView(R.id.bg);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TestAdapter testAdapter=new TestAdapter();
        List<String> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(i+"条数据");
        }
        testAdapter.submitList(list);
        recyclerView.setAdapter(testAdapter);
        bg.setOnClickListener(v -> dismiss());




        //添加进入动画
        recyclerView.setAnimation( AnimationUtils.loadAnimation(getContext(),R.anim.anim_dialog_position_top_bottom_enter));
        bg.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_dialog_mask_in));
    }

    @Override
    public void onExitAnimation() {
        Animation exit= AnimationUtils.loadAnimation(getContext(),R.anim.anim_dialog_position_top_bottom_exit);
        setExitAnimation(exit);

        recyclerView.setAnimation(exit);
        bg.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_dialog_mask_out));

        recyclerView.setVisibility(View.INVISIBLE);
        bg.setVisibility(View.INVISIBLE);
    }
}
