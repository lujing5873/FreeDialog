package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;
import com.example.freedialog.adp.AddressAdp;
import com.example.freedialog.adp.TestAdapter2;
import com.example.freedialog.databinding.DialogAddressBinding;
import com.example.freedialog.model.AddressModel;
import com.example.freedialog.model.AddressTwo;
import com.example.freedialog.utils.AddressData;
import com.example.freedialog.weight.TopLinearSnapHelper;
import com.nhcz500.freedialog.FreeCusDialog;

import java.util.ArrayList;
import java.util.List;

public class AddressDialog extends FreeCusDialog {
    private DialogAddressBinding bind;

    private AddressAdp adp1,adp2,adp3;

    private OnSelectAddress onSelectAddress;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_address;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        bind = DialogAddressBinding.bind(rootView);

        setStyle(R.style.DialogPositionBottomToTop);
        bind.addressRv1.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.addressRv2.setLayoutManager(new LinearLayoutManager(getContext()));
        bind.addressRv3.setLayoutManager(new LinearLayoutManager(getContext()));

        adp1=new AddressAdp();
        adp2=new AddressAdp();
        adp3=new AddressAdp();


        bind.addressRv1.setAdapter(adp1);
        bind.addressRv2.setAdapter(adp2);
        bind.addressRv3.setAdapter(adp3);


        TopLinearSnapHelper topLinearSnapHelper1=new TopLinearSnapHelper();
        TopLinearSnapHelper topLinearSnapHelper2=new TopLinearSnapHelper();
        TopLinearSnapHelper topLinearSnapHelper3=new TopLinearSnapHelper();

        topLinearSnapHelper1.attachToRecyclerView(bind.addressRv1);
        topLinearSnapHelper2.attachToRecyclerView(bind.addressRv2);
        topLinearSnapHelper3.attachToRecyclerView(bind.addressRv3);


        bind.addressRv1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState==RecyclerView.SCROLL_STATE_IDLE){//滚动结束需要更新数据
                    setData(false,true);
                }
            }
        });

        bind.addressRv2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState==RecyclerView.SCROLL_STATE_IDLE){//滚动结束需要更新数据
                    setData(false,false);
                }
            }
        });
        setData(true,false);


       addViewListener(R.id.address_ok,R.id.address_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_ok:{
                if(onSelectAddress!=null){
                    onSelectAddress.onSelectAddress(adp1.getItemData(bind.addressRv1.getSelect())
                            ,adp2.getItemData(bind.addressRv1.getSelect())
                            ,adp3.getItemData(bind.addressRv1.getSelect()));
                }
                dismiss();
            }
            case R.id.address_cancel:{
                dismiss();
            }
        }
    }

    private void setData(boolean freshAll, boolean freshTwo){


            if(freshAll){
                List<AddressModel> all=new ArrayList<>();
                for(AddressModel tmp:AddressData.ADDRESS_DATA){
                    all.add(tmp);
                }
            adp1.setList(all);
        }

        int firstSelect=bind.addressRv1.getSelect();
        if(firstSelect<0){
            firstSelect=0;
        }

        List<AddressModel> twoList=new ArrayList<>();
        List<AddressTwo> two=AddressData.ADDRESS_DATA.get(firstSelect).getCityList();

        for(AddressModel tmp:two){
            twoList.add(tmp);
        }
        adp2.setList(twoList);

        List<AddressModel> threeList=new ArrayList<>();
        if(two.isEmpty()){ //为空设置空值
            adp3.setList(threeList);
            return;
        }

        int secondSelect=bind.addressRv2.getSelect();
        if(secondSelect<0){
            secondSelect=0;
        }

        adp3.setList(two.get(secondSelect>=two.size()?0:secondSelect).getAreaList());

        if(freshTwo){
            bind.addressRv2.scrollToPosition(0);
        }
        bind.addressRv3.scrollToPosition(0);

    }



    public AddressDialog setOnSelectAddress(OnSelectAddress onSelectAddress){
        this.onSelectAddress=onSelectAddress;
        return this;
    }
    public interface OnSelectAddress{
        void onSelectAddress(AddressModel one,AddressModel two,AddressModel three);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        onSelectAddress=null;
    }
}
