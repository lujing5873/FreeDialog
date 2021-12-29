package com.nhcz500.freedialog.config;

public class DialogGravity {
    private static final int AXIS_SPECIFIED = 0x0001; //0000 0001
    private static final int AXIS_PULL_BEFORE = 0x0002;  //0000 0010
    private static final int AXIS_PULL_AFTER = 0x0004; // 0000 0100
    private static final int AXIS_PULL_ALIGN = 0x0008; // 0000 1000
    private static final int AXIS_X_SHIFT = 0; //0000 0000
    private static final int AXIS_Y_SHIFT = 4; // 0000 0100
    public static final int TOP = (AXIS_PULL_BEFORE|AXIS_SPECIFIED)<<AXIS_Y_SHIFT;// 0011 0000  (48)
    public static final int BOTTOM = (AXIS_PULL_AFTER|AXIS_SPECIFIED)<<AXIS_Y_SHIFT; // 0101 0000  (80)
    public static final int LEFT = (AXIS_PULL_BEFORE|AXIS_SPECIFIED)<<AXIS_X_SHIFT; // 0000 0011  (3)
    public static final int ALIGN_LEFT = (AXIS_PULL_ALIGN|AXIS_SPECIFIED)<<AXIS_X_SHIFT; // 0000 1001  (9)
    public static final int ALIGN_RIGHT = (AXIS_PULL_ALIGN|AXIS_PULL_BEFORE)<<AXIS_X_SHIFT; // 0000 1010  (10)



    public static final int RIGHT = (AXIS_PULL_AFTER|AXIS_SPECIFIED)<<AXIS_X_SHIFT;// 0000 0101  (5)
    public static final int CENTER_VERTICAL = AXIS_SPECIFIED<<AXIS_Y_SHIFT; // 0001 0000  (16)
    public static final int CENTER_HORIZONTAL = AXIS_SPECIFIED<<AXIS_X_SHIFT;// 0000 0001  (1)
    public static final int CENTER = CENTER_VERTICAL|CENTER_HORIZONTAL; // 0001 0001  (17)
}
