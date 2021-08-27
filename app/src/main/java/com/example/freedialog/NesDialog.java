package com.example.freedialog;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.nhcz500.freedialog.FreeCusDialog;

public class NesDialog  extends FreeCusDialog {
    RadioGroup radioGroup;
    View nes;
    @Override
    public int getLayoutId() {
        return R.layout.dialog_time;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        radioGroup=getView(R.id.rb_group);
        nes=getView(R.id.nes_bottom);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.rb3){
                nes.setVisibility(View.VISIBLE);
            }else {
                nes.setVisibility(View.GONE);
            }
        });
    }
}
