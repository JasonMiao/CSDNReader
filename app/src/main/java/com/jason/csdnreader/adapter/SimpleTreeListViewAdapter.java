package com.jason.csdnreader.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.ui.view.treeview.Node;
import com.jason.csdnreader.ui.view.treeview.TreeHelper;
import com.jason.csdnreader.ui.view.treeview.TreeListViewAdapter;

import java.util.List;

public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {
    public SimpleTreeListViewAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView,
                               ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.treeview_list_item, parent, false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.id_item_icon);
            holder.mText = (TextView) convertView.findViewById(R.id.id_item_text);
            holder.mDesc = (TextView) convertView.findViewById(R.id.id_item_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }

        holder.mText.setText(node.getName());
        holder.mDesc.setText(node.getDesc());

        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        TextView mText;
        TextView mDesc;
    }

}
