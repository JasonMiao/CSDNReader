package com.jason.csdnreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jason.csdnreader.R;

import java.util.ArrayList;

/**
 * 顶部弹出分组的适配器
 * Created by zzmiao on 2015/9/23.
 */
public class GroupAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> groups;
    private LayoutInflater mLayoutInflater;

    public GroupAdapter(Context context, ArrayList<String> groups){
        this.mContext = context;
        this.groups = groups;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.group_item, null);
            convertView.setTag(viewHolder);
            viewHolder.groupItemTextView = (TextView) convertView.findViewById(R.id.tv_group_item);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.groupItemTextView.setText(groups.get(position));
        return convertView;
    }

    static class ViewHolder{
        TextView groupItemTextView;
    }
}
