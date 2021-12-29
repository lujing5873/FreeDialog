package com.example.freedialog.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.freedialog.R;
import com.example.freedialog.databinding.DialogAnimBinding;
import com.nhcz500.freedialog.FreeCusDialog;

public class AnimDialog extends FreeCusDialog {
    DialogAnimBinding bind;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_anim;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        bind=DialogAnimBinding.bind(rootView);


        bind.animTv1.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_rotate));
        bind.animTv2.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_rotate));
    }

    @Override
    public void onExitAnimation() {
        Animation exit= AnimationUtils.loadAnimation(getContext(),R.anim.anim_rotate);

        dialog.setExitAnimation(exit);

        bind.animTv1.setAnimation(exit);
        bind.animTv2.setAnimation(exit);

        bind.animTv1.setVisibility(View.INVISIBLE);
        bind.animTv2.setVisibility(View.INVISIBLE);
    }
}
