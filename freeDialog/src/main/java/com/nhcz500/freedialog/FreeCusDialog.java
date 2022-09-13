package com.nhcz500.freedialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nhcz500.freedialog.config.DialogGravity;
import com.nhcz500.freedialog.dialog.WeakDialog;
import com.nhcz500.freedialog.utils.NotchScreenUtil;
import com.nhcz500.freedialog.utils.SoftKeyboardUtils;


public abstract class FreeCusDialog extends DialogFragment implements
        View.OnClickListener , WeakDialog.onExit, WeakDialog.onKeyTrans,WeakDialog.Touch{



    protected View rootView;
    protected WeakDialog dialog;
    private View anchorView;//依附的view
    private ViewClick listener;
    Animation exitAnimation;
    OnDisMissFreeDialog dismiss;
    private  DialogConfigs configs=new DialogConfigs();
    private static final String  configsString="configs";

    DisplayMetrics screen;//缓存屏幕数据
//    Rect screenV2; //dialog 实际宽高
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent); //设置背景
        ViewGroup view= (ViewGroup) dialog.getWindow().getDecorView();

        configs.isLiuHai= NotchScreenUtil.hasNotStatus(getActivity().getWindow().getDecorView());
        view.removeAllViews();//不要其附属的子FrameLayout

        if(configs.anchorViewId>0&&anchorView==null){
            anchorView=getActivity().findViewById(configs.anchorViewId);//尝试查找view
        }

        int pxElevation=dip2px(configs.elevation);
        setDialogView(view, pxElevation);

        if(configs.canDrag){
            setDrag(view);
        }


        if(anchorView!=null) {
            if(anchorView.getWidth()==0){
                anchorView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        if(anchorView!=null&&anchorView.getWidth()!=0){
                            anchorView.removeOnLayoutChangeListener(this);
                            setAnchorView(view, pxElevation);
                        }
                    }
                });
            }else{
                setAnchorView(view, pxElevation);
            }
        }
        // 遮罩层透明度
        float dim=anchorView!=null?configs.dimAmount==-1?0:configs.dimAmount :configs.dimAmount==-1?0.5f:configs.dimAmount;
        dialog.getWindow().setDimAmount(dim);

        if(dim==0){
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        if(configs.style>0){
            setStyle(dialog.getWindow());
        }
        dialog.setCanceledOnTouchOutside(configs.cancel);
        dialog.setCancelable(configs.cancel);
        dialog.setOnExit(this);
        return dialog;
    }

    private void setStyle(Window window){ //window动画
        window.setWindowAnimations(configs.style);
    }

    private void setDrag(ViewGroup view) {
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        configs.xDown =motionEvent.getRawX();
                        configs.yDown =motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //当滑动时背景为选中状态 //检测是否长按,在非长按时检测
                        if (!configs.isLongClickModule) {
                            configs.isLongClickModule = isLongPressed(configs.xDown, configs.yDown, motionEvent.getRawX(),
                                    motionEvent.getRawY(), motionEvent.getDownTime(), motionEvent.getEventTime()
                                    , configs.timeMillis);
                        }
                        if (configs.isLongClickModule){
                            params.x = params.x+(int) (motionEvent.getRawX()-configs.lastX);
                            params.y = params.y+(int) (motionEvent.getRawY()-configs.lastY);
                            dialog.getWindow().setAttributes(params);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        configs.isLongClickModule=false;
                        break;
                }

                configs.lastX=motionEvent.getRawX();
                configs.lastY=motionEvent.getRawY();
                return false;
            }
        });
    }

    private void setDialogView(ViewGroup view, int pxElevation) {
        if(getLayoutId()>0){
            if(anchorView==null){
                dialog.getWindow().setGravity(configs.gravity);//必须设置
            }else{
                dialog.getWindow().setGravity(Gravity.TOP| Gravity.LEFT);//必须设置
            }
            if(configs.softMode>0){
                dialog.getWindow().setSoftInputMode(configs.softMode);
            }else{
                dialog.getWindow().setSoftInputMode(configs.softMode|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
            }
            //因为rootView inflate的依赖的是DecorView 所以LayoutParams 必定为FrameLayout.LayoutParams
            FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) rootView.getLayoutParams();
            //设置window位布局的设置  如果为固定值的需要加上margin数值
            dialog.getWindow().setLayout(params.width>0
                            ?params.width+pxElevation*2+params.leftMargin+params.rightMargin
                            :params.width
                    ,params.height>0
                            ?params.height+pxElevation*2+params.bottomMargin+params.topMargin
                            :params.height);


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
            int yGravity = configs.gravity & 0xf0;//获取前4位 得到y轴
            int xGravity = configs.gravity & 0x0f;//获取后4位 得到x轴
            int pxYOffset=dip2px(configs.yOffset);
            int pxXOffset=dip2px(configs.xOffset);

            measureRoot(pxElevation);

            //重新设置窗口大小
            if(!configs.isTrend){
                dialog.getWindow().setLayout(rootView.getMeasuredWidth()+pxElevation*2,rootView.getMeasuredHeight()+pxElevation*2);
            }
            dialog.getWindow().setGravity(DialogGravity.TOP | DialogGravity.LEFT);//必须设置
            //获取window的attributes用于设置位置
            WindowManager.LayoutParams windowParams = dialog.getWindow().getAttributes();
            // 获取rootView的高宽
            final int rHeight = rootView.getMeasuredHeight();
            final int rWidth = rootView.getMeasuredWidth();
            int x = 0, y = 0;
            //location[1]已经包含了状态栏高度

            //处理y轴
            switch (yGravity) {
                case DialogGravity.TOP:
                    y = Math.max(0,configs.location[1]-(configs.isLiuHai?configs.statusHeight:0)+ pxYOffset  - rHeight - pxElevation * 2);
                    break;
                case DialogGravity.CENTER_VERTICAL:
                    y = Math.max(configs.location[1] + pxYOffset -configs.statusHeight- (rHeight - anchorView.getHeight()) / 2-pxElevation,configs.isLiuHai?0:configs.statusHeight);
                    break;
                default://BOTTOM
                    y = Math.max(0,configs.location[1] + pxYOffset + anchorView.getHeight()-pxElevation-(configs.isLiuHai?configs.statusHeight:0));
                    break;
            }
            //处理x轴
            switch (xGravity) {
                //左右对齐忽略阴影
                case DialogGravity.ALIGN_LEFT:
                    x=Math.max(0,configs.location[0]+ pxXOffset-pxElevation);
                    break;
                case DialogGravity.ALIGN_RIGHT:
                    x = Math.max(0,configs.location[0] + pxXOffset-rWidth +anchorView.getWidth()-pxElevation);
                    break;
                case DialogGravity.LEFT:
                    x = Math.max(0,configs.location[0] + pxXOffset - rWidth - pxElevation);
                    break;
                case DialogGravity.RIGHT:
                    x = Math.max(0,configs.location[0] + pxXOffset + anchorView.getWidth()-pxElevation);
                    break;
                default: //center_horizontal
                    x =Math.max(0, configs.location[0] + pxXOffset- (rWidth - anchorView.getWidth()) / 2 - pxElevation) ;
                    break;
            }
            windowParams.x=x;
            windowParams.y=y;
            // 拥有穿透效果 dialog布局之外可以相应事件传递
//            windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

            dialog.getWindow().setAttributes(windowParams);
        }
    }
    //测算rootView 宽高
    private void measureRoot(int pxElevation){
        //因为rootView inflate的依赖的是DecorView 所以LayoutParams 必定为FrameLayout.LayoutParams
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) rootView.getLayoutParams();
        screen=getWindowSize();//屏幕宽高

        if(anchorView!=null){
            anchorView.getLocationInWindow(configs.location);
        }
        int withSpec, heightSpec;
        int yGravity = configs.gravity & 0xf0;//获取前4位 得到y轴
        int xGravity = configs.gravity & 0x0f;//获取后4位 得到x轴
        configs.statusHeight=getStatusBarHeight();
        configs.barHeight=getNavigationBarHeight();
        int heightMax,withMax;
        int defMaxHeight=screen.heightPixels-pxElevation*2;
        int defMaxWith=screen.widthPixels-pxElevation*2;


//        defMaxHeight=screenV2.height()-pxElevation*2;
//        defMaxWith=screenV2.width()-pxElevation*2;


        //处理y轴
        switch (yGravity) {
            case DialogGravity.TOP:
                heightMax = Math.min(defMaxHeight,configs.location[1]-configs.statusHeight-pxElevation*2);//最大值不能超过Y
                break;
            case DialogGravity.CENTER_VERTICAL:
                heightMax =Math.min(defMaxHeight,(configs.location[1]-configs.statusHeight)*2+anchorView.getHeight()-pxElevation*2);
                break;
            default://BOTTOM
                heightMax = Math.min(defMaxHeight,screen.heightPixels-configs.location[1]-anchorView.getHeight()-configs.barHeight-pxElevation*2);
                break;
        }


        //处理x轴
        switch (xGravity) {

            case DialogGravity.ALIGN_LEFT:
                withMax = Math.min(defMaxWith,screen.widthPixels-configs.location[0]-pxElevation*2);
                break;
            case DialogGravity.ALIGN_RIGHT:
                withMax= Math.min(defMaxWith,configs.location[0]+anchorView.getWidth()-pxElevation*2);
                break;
            case DialogGravity.LEFT:
                withMax = Math.min(defMaxWith,configs.location[0]-pxElevation*2);
                break;
            case DialogGravity.RIGHT:
                withMax = Math.min(defMaxWith,screen.widthPixels-configs.location[0]-anchorView.getWidth()-pxElevation*2);
                break;
            case DialogGravity.CENTER_HORIZONTAL:
                withMax = Math.min(defMaxWith,configs.location[0]*2+anchorView.getWidth()-pxElevation*2);
                break;
            default: //center_horizontal
                withMax = defMaxWith;
                break;
        }

        //处理宽度
        switch (params.width){
            case ViewGroup.LayoutParams.MATCH_PARENT:
                withSpec = View.MeasureSpec.makeMeasureSpec(withMax, View.MeasureSpec.EXACTLY);
                break;
            case ViewGroup.LayoutParams.WRAP_CONTENT:
                withSpec = View.MeasureSpec.makeMeasureSpec(withMax, View.MeasureSpec.AT_MOST);
                break;
            default: //固定值
                withSpec = View.MeasureSpec.makeMeasureSpec(Math.min(params.width,withMax), View.MeasureSpec.EXACTLY);
                break;
        }
        //处理高度
        switch (params.height){
            case ViewGroup.LayoutParams.MATCH_PARENT:
                heightSpec = View.MeasureSpec.makeMeasureSpec(heightMax, View.MeasureSpec.EXACTLY);
                break;
            case ViewGroup.LayoutParams.WRAP_CONTENT:
                heightSpec = View.MeasureSpec.makeMeasureSpec(heightMax, View.MeasureSpec.AT_MOST);
                break;
            default: //固定值
                heightSpec = View.MeasureSpec.makeMeasureSpec(Math.min(params.height,heightMax), View.MeasureSpec.EXACTLY);
                break;
        }
        //手动measure获取view大小 用于后续位置调整
        rootView.measure(withSpec, heightSpec);
    }


    /**
     * dialog的id
     * @return
     */
    public abstract int getLayoutId();



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(configs!=null){
            outState.putParcelable(configsString,configs);
        }
        super.onSaveInstanceState(outState);
    }


    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        //获取是否有config保存
        if(savedInstanceState!=null){
            configs=savedInstanceState.getParcelable(configsString);
        }
        //防止bundle中获取的是null
        if(configs==null){
            configs=new DialogConfigs();
        }
        if(dialog==null){
            dialog=new WeakDialog(getActivity());
            dialog.setOnKey(this);
            dialog.setTouch(this);
        }
        if(getLayoutId()>0){
            rootView= LayoutInflater.from(getActivity()).inflate(getLayoutId(), (ViewGroup) dialog.getWindow().getDecorView(),false);
        }else{
            TextView textView=new TextView(getActivity());
            textView.setText("no layout id");
            rootView=textView;
        }
        createView(savedInstanceState);
        return super.onGetLayoutInflater(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    /**
     * dialog初始化view
     * @param savedInstanceState
     */
    protected abstract void createView(Bundle savedInstanceState);


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
//        int flags=getActivity().getWindow().getAttributes().flags;
//        int sysUi=getActivity().getWindow().getDecorView().getSystemUiVisibility();
//        if ((flags & FLAG_FULLSCREEN)== FLAG_FULLSCREEN) {
//            return 0;
//        }
//        if ( (sysUi & SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)== SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN &&!isNot) {
//            return 0;
//        }
        //获取状态栏高度
        Resources resources = getActivity().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    public int getNavigationBarHeight() {
        boolean hasNavigationBar = false;
        View content = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        if(content!=null){
            hasNavigationBar=content.getBottom()==screen.heightPixels;
        }
        if(hasNavigationBar){
            return 0;
        }
        Resources resources = getActivity().getResources();
        int resourceId=resources.getIdentifier("navigation_bar_height","dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;

    }
    /**
     * 获取屏幕宽高
     * @return
     */
    private DisplayMetrics getWindowSize(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取屏幕宽高
     * @return
     */
    private Rect getWindowSizeV2(){
        Rect outMetrics = new Rect();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getRectSize(outMetrics);
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public <T extends View> T getView(@IdRes int id) {
        return rootView.findViewById(id);
    }

    public interface  ViewClick{
        void onViewClick(View view,FreeCusDialog dialog);
    }
    public interface  OnDisMissFreeDialog{
        void onDisFree();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onViewClick(v,this);
        }
    }

    private boolean isLongPressed(float lastX, float lastY,
                                  float thisX, float thisY,
                                  long lastDownTime, long thisEventTime,
                                  long longPressTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            return true;
        }
        return false;
    }

    /**
     * 设置是否可以长按拖拽
     * @param canDrag
     * @return
     */
    public FreeCusDialog setCanDrag(boolean canDrag) {
        configs.canDrag = canDrag;
        return this;
    }

    /**
     * 设置拖拽的长按时间
     * @param timeMillis
     * @return
     */
    public FreeCusDialog setTimeMillis(int timeMillis) {
        configs.timeMillis = timeMillis;
        return this;
    }

    /**
     * 获取阴影值
     * @return
     */
    public int getElevation() {
        return configs.elevation;
    }

    /**
     * 设置阴影值
     * @param elevation
     * @return
     */
    public FreeCusDialog setElevation(int elevation) {
        configs.elevation = elevation;
        return this;
    }

    /**
     * 获取遮罩层透明度
     * @return 0-1
     */
    public float getDimAmount() {
        return configs.dimAmount;
    }

    /**
     * 设置遮罩层透明度
     * @param dimAmount 0-1
     * @return
     */
    public FreeCusDialog setDimAmount(float dimAmount) {
        configs.dimAmount = dimAmount;
        return this;
    }

    /**
     * 获取是否可以取消
     * @return
     */
    public boolean isCancel() {
        return configs.cancel;
    }

    /**
     * 设置是否可以取消
     * @param cancel
     * @return
     */
    public FreeCusDialog setCancel(boolean cancel) {
        configs.cancel = cancel;
        return this;
    }

    /**
     * 设置dialog位置
     * @param xOffset  x轴偏移
     * @param yOffset  y轴偏移
     * @return
     */
    public FreeCusDialog setAnchor(View anchorView, int xOffset, int yOffset){
        this.anchorView=anchorView;
        configs.anchorViewId=anchorView.getId();
        configs.xOffset=xOffset;
        configs.yOffset=yOffset;
        return this;
    }

    /**
     * 获取dialog的gravity
     * @return
     */
    public int getGravity() {
        return configs.gravity;
    }

    /**
     * 设置dialog的gravity
     * @param gravity
     * @return
     */
    public FreeCusDialog setGravity(int gravity) {
        configs.gravity = gravity;
        return this;
    }

    /**
     * 设置按键监听
     * @param listener
     * @return
     */
    public FreeCusDialog setListener(ViewClick listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 添加点击监听
     * @param ids
     */
    protected void addViewListener(int ...ids){
        for(int id:ids){
            View view=getView(id);
            if(view!=null){
                view.setOnClickListener(this);
            }
        }
    }

    /**
     * 添加点击监听
     *
     */
    protected void addViewListener(View ...views){
        for(View view:views){
            if(view!=null){
                view.setOnClickListener(this);
            }
        }
    }


    /**
     * 设置动画style
     * @param style
     */
    public FreeCusDialog setStyle(int style) {
        configs.style = style;
        return this;
    }

    /**
     * 设置softInputMode
     * @param softMode
     */
    public void setSoftMode(int softMode) {
        configs.softMode = softMode;
    }

    public void setExitAnimation(Animation exitAnimation) {
        this.exitAnimation=exitAnimation;
        if(dialog!=null){
            dialog.setExitAnimation(exitAnimation);
        }
    }

    @Override
    public void onExitAnimation() {

    }

    @Override
    public void dismiss() {
        if(rootView!=null&&rootView.findFocus()!=null){
            SoftKeyboardUtils.hideSoftKeyboard(rootView.findFocus());
        }
        if(dialog!=null){
            dialog.cancel();
        }
    }

    /**
     * dismiss监听
     * @param dialog
     */
    @Override
    public void onDismiss(@NonNull  DialogInterface dialog) {
        super.onDismiss(dialog);
        if(dismiss!=null){
            dismiss.onDisFree();
        }
    }

    public boolean isTrend() {
        return configs.isTrend;
    }

    /**
     * 设置是否动态
     * @param trend
     * @return
     */
    public FreeCusDialog setTrend(boolean trend) {
        configs.isTrend = trend;
        return this;
    }

    /**
     * dismiss监听器
     * @param dismiss
     * @return
     */
    public FreeCusDialog setDismiss(OnDisMissFreeDialog dismiss) {
        this.dismiss = dismiss;
        return this;
    }

    /**
     * 获取dialog的rootView
     * @return
     */
    public View getRootView() {
        return rootView;
    }


    public void showJustPan(boolean isShow){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onDestroy() {
        ViewGroup viewGroup= (ViewGroup) dialog.getWindow().getDecorView();
        if(viewGroup!=null){
            viewGroup.removeAllViews();
        }
        super.onDestroy();
        dialog=null;
        rootView=null;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
