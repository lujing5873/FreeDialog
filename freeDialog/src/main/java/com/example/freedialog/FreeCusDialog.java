package com.example.freedialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;


public abstract class FreeCusDialog extends DialogFragment implements View.OnClickListener {
    private static final int AXIS_SPECIFIED = 0x0001;
    private static final int AXIS_PULL_BEFORE = 0x0002;  //0000 0010
    private static final int AXIS_PULL_AFTER = 0x0004; // 0000 0100
    private static final int AXIS_X_SHIFT = 0; //0000 0000
    private static final int AXIS_Y_SHIFT = 4; // 0000 0100
    public static final int TOP = (AXIS_PULL_BEFORE|AXIS_SPECIFIED)<<AXIS_Y_SHIFT;// 0011 0000  (48)
    public static final int BOTTOM = (AXIS_PULL_AFTER|AXIS_SPECIFIED)<<AXIS_Y_SHIFT; // 0101 0000  (80)
    public static final int LEFT = (AXIS_PULL_BEFORE|AXIS_SPECIFIED)<<AXIS_X_SHIFT; // 0000 0011  (3)
    public static final int RIGHT = (AXIS_PULL_AFTER|AXIS_SPECIFIED)<<AXIS_X_SHIFT;// 0000 0101  (5)
    public static final int CENTER_VERTICAL = AXIS_SPECIFIED<<AXIS_Y_SHIFT; // 0001 0000  (16)
    public static final int CENTER_HORIZONTAL = AXIS_SPECIFIED<<AXIS_X_SHIFT;// 0000 0001  (1)
    public static final int CENTER = CENTER_VERTICAL|CENTER_HORIZONTAL; // 0001 0001  (17)

    private View rootView;
    protected Dialog dialog;
    private int elevation=3;//0不带阴影 其他则带阴影
    private float dimAmount=-1;//遮罩层透明度
    private int[] location= new int[2];
    private int xOffset,yOffset;//相对于view的x轴y轴偏移位置
    private View otherView;//依附的view
    private int gravity;
    private boolean cancel=true;

    protected ViewClick listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().addFlags(FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明
//        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent); //设置背景

        ViewGroup view= (ViewGroup) dialog.getWindow().getDecorView();
        view.removeAllViews();
        if(otherView!=null) {
            elevation=elevation==0?3:elevation;//如果没有设置阴影 则给予默认值2
        }
        int pxElevation=dip2px(elevation);
        setDialogView(view, pxElevation);
        if(otherView!=null) {
            setAnchorView(view, pxElevation);
        }


        // 遮罩层透明度
        dialog.getWindow().setDimAmount(otherView!=null?0:dimAmount==-1?0.5f:dimAmount);
        dialog.setCanceledOnTouchOutside(cancel);
        dialog.setCancelable(cancel);
        return dialog;
    }

    private void setDialogView(ViewGroup view, int pxElevation) {
        if(getLayoutId()!=0){
            if(otherView==null){
                dialog.getWindow().setGravity(gravity==0? Gravity.CENTER:gravity);//必须设置
            }else{
                dialog.getWindow().setGravity(Gravity.TOP| Gravity.LEFT);//必须设置
            }
            FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) rootView.getLayoutParams();
            //设置window位布局的设置  如果为固定值的需要加上margin数值
            dialog.getWindow().setLayout(params.width>0
                            ?params.width+pxElevation*2+params.leftMargin+params.rightMargin
                            :params.width
                            ,params.height>0
                            ?params.height+pxElevation*2+params.bottomMargin+params.topMargin
                            :params.height);

            DisplayMetrics screen=getWindowSize();//屏幕宽高
            int withSpec=0, heightSpec=0;
            switch (params.width){
                case ViewGroup.LayoutParams.MATCH_PARENT:
                    withSpec = View.MeasureSpec.makeMeasureSpec(screen.widthPixels-pxElevation*2, View.MeasureSpec.EXACTLY);
                    break;
                case ViewGroup.LayoutParams.WRAP_CONTENT:
                    withSpec = View.MeasureSpec.makeMeasureSpec(screen.widthPixels-pxElevation*2, View.MeasureSpec.AT_MOST);
                    break;
                default: //固定值
                    withSpec = View.MeasureSpec.makeMeasureSpec(params.width, View.MeasureSpec.EXACTLY);
                    break;
            }
            switch (params.height){
                case ViewGroup.LayoutParams.MATCH_PARENT:
                    heightSpec = View.MeasureSpec.makeMeasureSpec(screen.heightPixels-pxElevation*2, View.MeasureSpec.EXACTLY);
                    break;
                case ViewGroup.LayoutParams.WRAP_CONTENT:
                    heightSpec = View.MeasureSpec.makeMeasureSpec(screen.heightPixels-pxElevation*2, View.MeasureSpec.AT_MOST);
                    break;
                default: //固定值
                    heightSpec = View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
                    break;
            }

            //手动measure获取view大小 用于后续位置调整
            rootView.measure(withSpec, heightSpec);
            params.leftMargin+=pxElevation;
            params.rightMargin+=pxElevation;
            params.topMargin+=pxElevation;
            params.bottomMargin+=pxElevation;
            rootView.setElevation(pxElevation);
            view.addView(rootView);
        }
    }

    private void setAnchorView(ViewGroup view, int pxElevation) {

                    if(view!=null) {
                        int pxyOffset=dip2px(yOffset);
                        int pxxOffset=dip2px(xOffset);
                        dialog.getWindow().setGravity(Gravity.TOP | Gravity.LEFT);//必须设置
                        otherView.getLocationOnScreen(location);
                        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();//设置位置
                        // 获取rootView的高宽
                        final int rHeight = rootView.getMeasuredHeight();
                        final int rWidth = rootView.getMeasuredWidth();
                        int yGravity = gravity & 0xf0;//获取前4位 得到y轴
                        int xGravity = gravity & 0x0f;//获取后4位 得到x轴
                        int x = 0, y = 0;
                        //处理y轴
                        switch (yGravity) {
                            case TOP:
                                y = location[1] + pxyOffset - getStatusBarHeight() - rHeight - pxElevation * 2;
                                break;
                            case CENTER_VERTICAL:
                                y = location[1] + pxyOffset - getStatusBarHeight() - (rHeight - otherView.getHeight()) / 2;
                                break;
                            default://BOTTOM
                                y = location[1] + pxyOffset - getStatusBarHeight() + otherView.getHeight()-pxElevation;
                                break;
                        }

                        //处理x轴
                        switch (xGravity) {
                            case LEFT:
                                x = location[0] + pxxOffset - rWidth - pxElevation * 2;
                                break;
                            case RIGHT:
                                x = location[0] + pxxOffset + otherView.getWidth();
                                break;
                            default: //center_horizontal
                                x = location[0] + pxxOffset - pxElevation - (rWidth - otherView.getWidth()) / 2;
                                break;
                        }
                        params.x=x;
                        params.y=y;
                    }


    }

    public abstract int getLayoutId();


    public int getElevation() {
        return elevation;
    }

    public FreeCusDialog setElevation(int elevation) {
        this.elevation = elevation;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }
    protected abstract void createView(Bundle savedInstanceState);

    public float getDimAmount() {
        return dimAmount;
    }

    public FreeCusDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }


    public boolean isCancel() {
        return cancel;
    }

    public FreeCusDialog setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

    /**
     * 设置dialog位置
     * @param xOffset  x轴偏移
     * @param yOffset  y轴偏移
     * @return
     */
    public FreeCusDialog setPosition(View view, int xOffset, int yOffset){
        this.otherView=view;
        this.xOffset=xOffset;
        this.yOffset=yOffset;
        return this;
    }

    /**
     * dp转px
     * @param dpValue
     * @return
     */
    private  int dip2px( float dpValue) {
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     * @return
     */
    private  int getStatusBarHeight() {
//        如果是全屏了 则返回0
        if ( (getActivity().getWindow().getAttributes().flags & FLAG_FULLSCREEN)
                == FLAG_FULLSCREEN) {
            System.out.println("！！！！全屏显示");
            return 0;
        }
        //获取状态栏高度
        Resources resources = getActivity().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取屏幕宽高
     * @return
     */
    private DisplayMetrics getWindowSize(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public int getGravity() {
        return gravity;
    }

    public FreeCusDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }


    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        return super.show(transaction, tag);
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        if(dialog==null){
            dialog=new Dialog(getActivity());
        }
        if(getLayoutId()!=0){
            rootView= LayoutInflater.from(getActivity()).inflate(getLayoutId(), (ViewGroup) dialog.getWindow().getDecorView(),false);
        }
        createView(savedInstanceState);
        return super.onGetLayoutInflater(savedInstanceState);
    }

    public interface  ViewClick{
        void onViewClick(View view);
    }

    public FreeCusDialog setListener(ViewClick listener) {
        this.listener = listener;
        return this;
    }

    protected void addViewListener(int ids){
        findViewById(ids).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onViewClick(v);
        }
    }

    @Override
    public void dismiss() {
//        listener=null;
//        otherView=null;
//        dialog=null;
        super.dismiss();
    }

}
