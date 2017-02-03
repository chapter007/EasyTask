package com.zhangjie.easytask;

import android.graphics.drawable.Drawable;

/**
 * Created by zhangjie on 2017/2/1.
 */
public class Program {
    private Drawable icon;
    private String name;
    private String PackageName;

    public Drawable getIcon(){
        return icon;
    }

    public void setIcon(Drawable icon){
        this.icon=icon;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return PackageName;
    }

    @Override
    public boolean equals(Object o) {
        Program app= (Program) o;
        return name.equals(app.name);
    }

    @Override
    public int hashCode() {
        String in=name;
        return in.hashCode();
    }
}
