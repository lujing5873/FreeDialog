package com.nhcz500.freedialog;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhcz500.freedialog.config.DialogGravity;

public class DialogConfigs implements Parcelable {
    protected DialogConfigs() {
    }

    protected DialogConfigs(Parcel in) {
        elevation = in.readInt();
        dimAmount = in.readFloat();
        location = in.createIntArray();
        barHeight = in.readInt();
        statusHeight = in.readInt();
        xOffset = in.readInt();
        yOffset = in.readInt();
        anchorViewId = in.readInt();
        gravity = in.readInt();
        cancel = in.readByte() != 0;
        softMode = in.readInt();
        canDrag = in.readByte() != 0;
        timeMillis = in.readInt();
        isLongClickModule = in.readByte() != 0;
        lastX = in.readFloat();
        lastY = in.readFloat();
        xDown = in.readFloat();
        yDown = in.readFloat();
        style = in.readInt();
        isTrend = in.readByte() != 0;
        isLiuHai = in.readByte() != 0;
    }

    public static final Creator<DialogConfigs> CREATOR = new Creator<DialogConfigs>() {
        @Override
        public DialogConfigs createFromParcel(Parcel in) {
            return new DialogConfigs(in);
        }

        @Override
        public DialogConfigs[] newArray(int size) {
            return new DialogConfigs[size];
        }
    };


    protected int elevation=2;//0不带阴影 其他则带阴影 默认值为2
    protected float dimAmount=-1;//遮罩层透明度
    protected int[] location= new int[2];
    protected int barHeight,statusHeight;//缓存状态栏
    protected int xOffset,yOffset;//相对于view的x轴y轴偏移位置
    protected int anchorViewId;//依附的view
    protected int gravity= DialogGravity.CENTER;
    protected boolean cancel=true;
    protected int softMode;
    protected boolean canDrag;//是否可以拖拽
    protected int timeMillis=300; //长按触发时间
    protected boolean isLongClickModule = false;
    protected float lastX,lastY,xDown,yDown;
    protected int style;
    protected boolean isTrend;//是否动态加载数据
    protected boolean isLiuHai;//是否有刘海
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(elevation);
        dest.writeFloat(dimAmount);
        dest.writeIntArray(location);
        dest.writeInt(barHeight);
        dest.writeInt(statusHeight);
        dest.writeInt(xOffset);
        dest.writeInt(yOffset);
        dest.writeInt(anchorViewId);
        dest.writeInt(gravity);
        dest.writeByte((byte) (cancel ? 1 : 0));
        dest.writeInt(softMode);
        dest.writeByte((byte) (canDrag ? 1 : 0));
        dest.writeInt(timeMillis);
        dest.writeByte((byte) (isLongClickModule ? 1 : 0));
        dest.writeFloat(lastX);
        dest.writeFloat(lastY);
        dest.writeFloat(xDown);
        dest.writeFloat(yDown);
        dest.writeInt(style);
        dest.writeByte((byte) (isTrend ? 1 : 0));
        dest.writeByte((byte) (isLiuHai ? 1 : 0));
    }
}
