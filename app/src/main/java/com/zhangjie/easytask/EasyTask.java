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
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

/**
 * Created by zhangjie on 2016/1/30.
 */
public class EasyTask extends AccessibilityService {

    private static final String TAG = "EasyTask";
    private AccessibilityService service;
    private String[] block_list = {"systemui","ime","partnersetup","pico","push",
            "launcher3", "defcontainer", "com.android.phone", "service"};

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
        Log.i("destroy", "");

        //关闭
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.removeEasyPoint(getApplicationContext());
            }
        });
    }

    public List getRunningProcess(Context context) throws IOException {
        PackagesInfo pi = new PackagesInfo(context);
        //获取正在运行的应用,run需要处理一下，排序还要过滤
        List<AndroidAppProcess> run = AndroidProcesses.getRunningAppProcesses();

        //获取包管理器，在这里主要通过包名获取程序的图标和程序名
        PackageManager pm = context.getPackageManager();
        List<AppTimeSort> appList = handleList(run, pi, pm);
        List list = new ArrayList();
        for (AppTimeSort ra : appList) {
            Program pr = new Program();
            //System.out.println(ra.getName());
            pr.setIcon(ra.getIcon());
            pr.setName(ra.getName());
            pr.setPackageName(ra.getPackageName());
            list.add(pr);
        }
        list = removeDuplicate(list);
        return list;
    }

    public List<AppTimeSort> handleList(List<AndroidAppProcess> runApp, PackagesInfo pi, PackageManager pm) throws IOException {
        //排序还有去重
        List<AppTimeSort> appList = new ArrayList<>();
        for (AndroidAppProcess ra : runApp) {
            AppTimeSort app = new AppTimeSort();
            ApplicationInfo info = pi.getInfo(ra.getPackageName());
            Stat stat = ra.stat();
            long startTime = stat.starttime();


            if ((info != null) && !isHide(info)) {
                Log.i(TAG, "handleList: " + info.packageName);
                app.setIcon(info.loadIcon(pm));
                app.setName(info.loadLabel(pm).toString());
                app.setPackageName(info.packageName);
                app.setTime(startTime);
                appList.add(app);
            }

        }
        Collections.reverse(appList);
        return appList;
    }

    private List<Program> removeDuplicate(List<Program> list) {
        Set<Program> set = new HashSet<Program>();
        List<Program> newList = new ArrayList<Program>();
        for (Iterator<Program> iter = list.iterator(); iter.hasNext(); ) {
            Program element = (Program) iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }


    class RefreshTask extends TimerTask {
        //检测横屏隐藏圆点
        @Override
        public void run() {

            handler.post(new Runnable() {
                public static final String TAG = "test";

                @Override
                public void run() {

                }
            });


        }
    }


    private boolean isHide(ApplicationInfo info) {
        boolean isHide = false;
        for (int i = 0; i < block_list.length; i++) {
            if (info.packageName.contains(block_list[i])) {
                isHide = true;
                break;
            } else isHide = false;
        }
        return isHide;
    }
}
