package com.zhangjie.easytask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ViewHolder holder;
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
        //Log.i(TAG, "getView: "+program.getName());
        holder.icon.setImageDrawable(program.getIcon());

        return view;
    }

    class ViewHolder{
        TextView name;
        ImageView icon;
    }
}
