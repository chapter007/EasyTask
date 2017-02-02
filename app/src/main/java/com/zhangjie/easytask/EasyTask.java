package com.zhangjie.easytask;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by zhangjie on 2016/1/30.
 */
public class EasyTask extends AccessibilityService {

    private static final String TAG ="EasyTask";
    private AccessibilityService service;

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public EasyTask() {
        service = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //创建taskBar
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.createEasyPoint(getApplicationContext(), service);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //计时器也销毁
        Log.i("destroy", "");

        //关闭圆点
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.removeEasyPoint(getApplicationContext());
            }
        });
    }

    public List getRunningProcess(Context context) throws IOException {
        PackagesInfo pi = new PackagesInfo(context);
        //ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        //获取正在运行的应用,run需要处理一下，排序还要过滤
        List<AndroidAppProcess> run = AndroidProcesses.getRunningAppProcesses();

        //获取包管理器，在这里主要通过包名获取程序的图标和程序名
        PackageManager pm = context.getPackageManager();

        List list = new ArrayList();
        for (AndroidAppProcess ra : run) {
            Program pr = new Program();
            ApplicationInfo info=pi.getInfo(ra.getPackageName());

            if (info!=null){
                Log.i(TAG, "getRunningProcess: ");
                System.out.println(info.loadLabel(pm).toString());
                pr.setIcon(info.loadIcon(pm));
                pr.setName(info.loadLabel(pm).toString());
                pr.setPackageName(info.packageName);
                list.add(pr);
            }
        }
        return list;
    }

    public List<AndroidAppProcess> handleList(List<AndroidAppProcess> runApp,PackagesInfo pi,PackageManager pm) throws IOException {
        //排序还有去重
        List list = new ArrayList();
        for (AndroidAppProcess ra : runApp) {
            Program pr = new Program();
            ApplicationInfo info=pi.getInfo(ra.getPackageName());
            Stat stat=ra.stat();
            long startTime=stat.starttime();
            if (info!=null){
                Log.i(TAG, "getRunningProcess: "+startTime);
                System.out.println(info.loadLabel(pm).toString());
                pr.setIcon(info.loadIcon(pm));
                pr.setName(info.loadLabel(pm).toString());
                pr.setPackageName(info.packageName);
                list.add(pr);
            }
        }
        return list;
    }

    class RefreshTask extends TimerTask {
        //检测横屏隐藏圆点
        @Override
        public void run() {

            handler.post(new Runnable() {
                public static final String TAG ="test";

                @Override
                public void run() {

                }
            });


        }
    }
}
