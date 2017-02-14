package com.zhangjie.easytask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjie on 2017/2/1.
 */
public class ListAdapter extends BaseAdapter{
    private static final String TAG ="Zhangjie";
    private List<Program> list=new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private float xInView,yInView,xDownInScreen,yDownInScreen,xInScreen,yInScreen;
    private OutputStream outputStream = null;
    private TranslateAnimation mHiddenAction;

    public ListAdapter(List<Program> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view==null){
            layoutInflater=LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.list_item, null);
            holder=new ViewHolder();
            holder.icon= (ImageView) view.findViewById(R.id.app_icon);
            holder.name= (TextView) view.findViewById(R.id.app_name);

            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        final Program program=list.get(i);
        holder.name.setText(program.getName());
        holder.icon.setImageDrawable(program.getIcon());
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(200);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        xInView = motionEvent.getX();
                        yInView = motionEvent.getY();
                        xDownInScreen = motionEvent.getRawX();
                        yDownInScreen = motionEvent.getRawY() - 10;
                        xInScreen = motionEvent.getRawX();
                        yInScreen = motionEvent.getRawY() - 10;
                        Log.i(TAG, "onTouch: action down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        xInScreen = motionEvent.getRawX();
                        yInScreen = motionEvent.getRawY() - 10;
                        Log.i(TAG, "onTouch: action move");
                        break;
                    case MotionEvent.ACTION_UP:
                        float length = yDownInScreen - yInScreen;
                        float wlength = Math.abs(xDownInScreen - xInScreen);
                        float hlength = Math.abs(yDownInScreen - yInScreen);
                        Log.i(TAG, "onTouch: action up" + wlength + "/" + length);
                        if (wlength < 200 && length > 0 && hlength > 40) {
                            Log.i("上划,y", "" + wlength + "/" + length);
                            killAppAdvance(program.getPackageName());
                            view.startAnimation(mHiddenAction);
                            view.setVisibility(View.GONE);
                            
                            list.remove(i);
                            notifyDataSetChanged();
                        }
                        break;
                }
                return true;
            }
        });
        return view;
    }

    public void killAppAdvance(String appPackageName) {
        String enable="pm enable "+appPackageName+"\n";
        String disable="pm disable "+appPackageName+"\n";
        try {
            if (outputStream == null) {
                outputStream = Runtime.getRuntime().exec("su").getOutputStream();
            }
            outputStream.write(disable.getBytes());
            outputStream.write(enable.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    class ViewHolder{
        TextView name;
        ImageView icon;
    }
}
