package com.example.freedialog.dialog;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;
import com.example.freedialog.SelectModel;
import com.example.freedialog.TestAdapter;
import com.example.freedialog.TestAdapter2;
import com.nhcz500.freedialog.FreeCusDialog;

import java.util.ArrayList;
import java.util.List;

public class SelectDialog extends FreeCusDialog {
    private RecyclerView left,center,right;
    private TestAdapter2 leftAdp;
    private List<SelectModel> leftList;
    @Override
    public int getLayoutId() {
        return R.layout.dialog_select;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        left=getView(R.id.left);


        left.setLayoutManager(new LinearLayoutManager(getContext()));
        leftAdp=new TestAdapter2();
        left.setAdapter(leftAdp);

        LinearSnapHelper mSnap=new LinearSnapHelper();
        mSnap.attachToRecyclerView(left);

        leftList=new ArrayList<>();
        leftList.add(new SelectModel("",false));
        leftList.add(new SelectModel("",false));
        leftList.add(new SelectModel("北京1",true));
        leftList.add(new SelectModel("北京2",false));
        leftList.add(new SelectModel("北京3",false));
        leftList.add(new SelectModel("北京4",false));
        leftList.add(new SelectModel("北京5",false));
        leftList.add(new SelectModel("北京6",false));
        leftList.add(new SelectModel("北京7",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));
        leftList.add(new SelectModel("北京",false));


        leftAdp.submitList(leftList);

    }
}
