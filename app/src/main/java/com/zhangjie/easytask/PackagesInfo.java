package com.zhangjie.easytask;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by zhangjie on 2017/2/1.
 */
public class PackagesInfo {
    private List<ApplicationInfo> appList;

    public PackagesInfo(Context context) {
        //通包管理器，检索所有的应用程序（甚至卸载的）与数据目录
        PackageManager pm = context.getApplicationContext().getPackageManager();
        appList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
    }

    public ApplicationInfo getInfo(String name) {
        if (name == null) {
            return null;
        }

        for (ApplicationInfo appinfo : appList) {
            if (name.equals(appinfo.processName)) {
                return appinfo;
            }
        }
        return null;
    }
}
