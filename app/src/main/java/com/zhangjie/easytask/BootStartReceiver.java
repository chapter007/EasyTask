package com.zhangjie.easytask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhangjie on 2017/2/10.
 */

public class BootStartReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service=new Intent(context,EasyTask.class);
        context.startService(service);
    }
}
