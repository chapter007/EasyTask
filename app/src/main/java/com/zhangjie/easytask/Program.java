package com.zhangjie.easytask;

import android.graphics.drawable.Drawable;

/**
 * Created by zhangjie on 2017/2/1.
 */
public class Program {
    private Drawable icon;
    private String name;

    public Drawable getIcon(){
        return icon;
    }

    public void setIcon(Drawable icon){
        this.icon=icon;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
