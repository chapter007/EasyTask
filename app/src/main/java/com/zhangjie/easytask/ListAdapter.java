package com.zhangjie.easytask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjie on 2017/2/1.
 */
public class ListAdapter extends BaseAdapter{
    private static final String TAG ="test";
    private List<Program> list=new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view==null){
            layoutInflater=LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.list_item, null);
            holder=new ViewHolder();
            holder.icon= (ImageView) view.findViewById(R.id.app_icon);
            holder.name= (TextView) view.findViewById(R.id.app_name);
            view.setTag(holder);
        }else if(((ViewHolder)view.getTag()).needInflate){
            view=layoutInflater.inflate(R.layout.list_item,viewGroup,false);

        }else {
            holder= (ViewHolder) view.getTag();
        }
        final Program program=list.get(i);
        holder.name.setText(program.getName());
        holder.icon.setImageDrawable(program.getIcon());

        return view;
    }


    private void deleteCell(final View v, final int index) {
        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                list.remove(index);

                /*ListAdapter.ViewHolder vh = (ListAdapter.ViewHolder)v.getTag();
                vh.needInflate = true;*/

                //adapter.notifyDataSetChanged();
            }
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationStart(Animation animation) {}
        };

        collapse(v, al);
    }

    private void collapse(final View v, Animation.AnimationListener al) {
        final int initialHeight = v.getMeasuredHeight();

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                }
                else {
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (al!=null) {
            anim.setAnimationListener(al);
        }
        anim.setDuration(300);
        v.startAnimation(anim);
    }

    class ViewHolder{
        TextView name;
        ImageView icon;
        boolean needInflate;
    }
}
