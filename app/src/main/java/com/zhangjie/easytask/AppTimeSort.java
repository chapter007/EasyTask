package com.zhangjie.easytask;

import android.graphics.drawable.Drawable;

/**
 * Created by zhangjie on 2017/2/3.
 */
public class AppTimeSort implements Comparable{
    private long time;
    private String name;
    private String packageName;
    private Drawable icon;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public int compareTo(Object o) {
        AppTimeSort appTimeSort= (AppTimeSort) o;
        long otherTime=appTimeSort.getTime();
        return this.compareTo(otherTime);
    }
}
