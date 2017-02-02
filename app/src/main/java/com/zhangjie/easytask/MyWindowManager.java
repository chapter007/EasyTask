package com.zhangjie.easytask;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by zhangjie on 2016/1/30.
 */
public class MyWindowManager {
    /**
     * 小悬浮窗View的实例
     */
    private static TaskBarView smallWindow;

    /**
     * 小悬浮窗View的参数
     */
    private static WindowManager.LayoutParams smallWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    private static SharedPreferences sharedPreferences;
    public static int screenHeight;
    private static Context mContext;
    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createEasyPoint(Context context,AccessibilityService service) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
        mContext=context;
        if (smallWindow == null) {
            smallWindow = new TaskBarView(context,service);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT|Gravity.BOTTOM;
                smallWindowParams.width = screenWidth;
                smallWindowParams.height = screenHeight;
                //smallWindowParams.x = screenWidth;
                //smallWindowParams.y = 20;

            }
            sharedPreferences=context.getSharedPreferences("setting", Context.MODE_PRIVATE);
            sharedPreferences.edit().putInt("origin",smallWindowParams.width).commit();
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    public static void updateEasyPoint(Context context,int vib,int alpha,int size) {
        if (smallWindow != null) {
            View point=smallWindow.findViewById(R.id.point_view);
            if (vib!=0){
                //smallWindow.setVibrator_val(vib-1);
            }
            if (alpha!=0){
                point.getBackground().setAlpha(alpha-1);
            }
            if (size!=0){
                float width=size/50.0f;
                int origin_width=sharedPreferences.getInt("origin",0);

                smallWindowParams.width = (int) (width*origin_width);
                smallWindowParams.height = (int) (width*origin_width);
                point.getLayoutParams().width= (int) (width*origin_width);
                point.getLayoutParams().height= (int) (width*origin_width);
                smallWindow.setParams(smallWindowParams);
                WindowManager manager= (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
                manager.updateViewLayout(smallWindow, smallWindowParams);

            }
        }
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     *            必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null;
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeEasyPoint(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

}
