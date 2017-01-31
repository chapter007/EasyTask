package com.zhangjie.easytask;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangjie on 2016/1/30.
 */
public class EasyTask extends AccessibilityService {

    private AccessibilityService service;
    private Vibrator vibrator;

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public EasyTask(){
        service=this;
    }

    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前悬浮窗透明度。
     */
    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启定时器，每隔5秒检测一下
        /*if (timer==null){
            timer=new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(),1000,5000);
        }*/

        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //创建小圆点
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.createEasyPoint(getApplicationContext(), service,vibrator);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //计时器也销毁
        Log.i("destroy","");
        timer.cancel();
        timer=null;
        //关闭圆点
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.removeEasyPoint(getApplicationContext());
            }
        });
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    class RefreshTask extends TimerTask{
        //检测横屏隐藏圆点
        @Override
        public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });


        }
    }
}
