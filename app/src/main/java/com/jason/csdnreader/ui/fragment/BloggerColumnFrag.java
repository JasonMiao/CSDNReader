package com.jason.csdnreader.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.SimpleTreeListViewAdapter;
import com.jason.csdnreader.bean.Column;
import com.jason.csdnreader.ui.activity.BloggerShowActivity;
import com.jason.csdnreader.ui.view.treeview.Node;
import com.jason.csdnreader.ui.view.treeview.TreeListViewAdapter;
import com.jason.csdnreader.util.CommonUtil;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.HttpUtil;
import com.jason.csdnreader.util.URLUtil;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 博主专栏
 * Created by zzmiao on 2015/11/8.
 */
public class BloggerColumnFrag extends LazyFragment {
    private String username;
    private List<Column> columns = new ArrayList<>();
    private ListView lvColumn;
    private TreeListViewAdapter mAdapter;
    private TextView tvNodata;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_blogger_column);
        username = getArguments().getString(BloggerShowActivity.USERNAME);
        initData();
    }

    private void initData() {
        CommonUtil.showLoadingDialog(getActivity(), "数据加载中...");
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                columns = DataUtil.getColumn(html);
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                CommonUtil.dismissLoadingDialog();
                initView();
            }
        }.execute(URLUtil.BLOG + username);
    }

    private void initView() {
//        Log.e("initView", String.valueOf(columns.size()));
        lvColumn = (ListView) findViewById(R.id.lv_blogger_column);
        tvNodata = (TextView) findViewById(R.id.tv_blogger_column_nodata);
        if (columns.size() > 0){
            lvColumn.setVisibility(View.VISIBLE);
            try {
                mAdapter = new SimpleTreeListViewAdapter<Column>(lvColumn, getActivity(), columns, 0);
                mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                    @Override
                    public void onClick(Node node, int position) {
                        if (node.isLeaf())
                            CommonUtil.showToast(getActivity(), node.getLink());
                    }
                });
                lvColumn.setAdapter(mAdapter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            tvNodata.setVisibility(View.VISIBLE);
        }
    }
}
