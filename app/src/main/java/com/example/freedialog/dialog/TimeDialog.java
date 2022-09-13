package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freedialog.R;
import com.example.freedialog.adp.AddressAdp;
import com.example.freedialog.databinding.DialogAddressBinding;
import com.example.freedialog.model.AddressModel;
import com.example.freedialog.model.AddressTwo;
import com.example.freedialog.utils.AddressData;
import com.example.freedialog.utils.TimeUtils;
import com.example.freedialog.weight.TopLinearSnapHelper;
import com.nhcz500.freedialog.FreeCusDialog;

public class TimeDialog extends FreeCusDialog {
    private DialogAddressBinding bind;

    private AddressAdp adp1, adp2, adp3;

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

        adp1 = new AddressAdp();
        adp2 = new AddressAdp();
        adp3 = new AddressAdp();


        bind.addressRv1.setAdapter(adp1);
        bind.addressRv2.setAdapter(adp2);
        bind.addressRv3.setAdapter(adp3);


        TopLinearSnapHelper topLinearSnapHelper1 = new TopLinearSnapHelper();
        TopLinearSnapHelper topLinearSnapHelper2 = new TopLinearSnapHelper();
        TopLinearSnapHelper topLinearSnapHelper3 = new TopLinearSnapHelper();

        topLinearSnapHelper1.attachToRecyclerView(bind.addressRv1);
        topLinearSnapHelper2.attachToRecyclerView(bind.addressRv2);
        topLinearSnapHelper3.attachToRecyclerView(bind.addressRv3);


        bind.addressRv1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//滚动结束需要更新数据
                    setData();
                }
            }
        });

        bind.addressRv2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//滚动结束需要更新数据
                    setData();
                }
            }
        });

        adp1.setList(AddressData.TIME_YEAR);
        adp2.setList(AddressData.TIME_MONTH);
        setData();

        addViewListener(R.id.address_ok, R.id.address_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_ok: {
                if (onSelectAddress != null) {
                    onSelectAddress.onSelectAddress(adp1.getItemData(bind.addressRv1.getSelect())
                            , adp2.getItemData(bind.addressRv1.getSelect())
                            , adp3.getItemData(bind.addressRv1.getSelect()));
                }
                dismiss();
            }
            case R.id.address_cancel: {
                dismiss();
            }
        }
    }

    private void setData() {

        int secondSelect = bind.addressRv2.getSelect();
        int firstSelect = bind.addressRv1.getSelect();
        if (firstSelect < 0) {
            firstSelect = 0;
        }
        if (secondSelect < 0) {
            secondSelect = 0;
        }
        int year= new Integer(AddressData.TIME_YEAR.get(firstSelect).getCode());
        int month= new Integer(AddressData.TIME_MONTH.get(secondSelect).getCode());
        adp3.setList(TimeUtils.getDays(year,month));

    }


    public TimeDialog setOnSelectAddress(OnSelectAddress onSelectAddress) {
        this.onSelectAddress = onSelectAddress;
        return this;
    }

    public interface OnSelectAddress {
        void onSelectAddress(AddressModel one, AddressModel two, AddressModel three);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        onSelectAddress = null;
    }
}
