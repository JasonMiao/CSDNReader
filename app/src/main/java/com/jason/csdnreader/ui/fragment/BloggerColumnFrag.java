package com.jason.csdnreader.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
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

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_blogger_column);
        username = getArguments().getString(BloggerShowActivity.USERNAME);
        initData();
    }

    private void initData() {
//        CommonUtil.showLoadingDialog();
        new AsyncTask<String, Void, List<Column>>() {
            @Override
            protected List<Column> doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                columns = DataUtil.getColumn(html);
                if (columns.size() == 0)
                    return null;
                return columns;
            }

            @Override
            protected void onPostExecute(List<Column> result) {
//                CommonUtil.dismissLoadingDialog();
                if (result == null)
                    CommonUtil.showToast(getActivity(), "没有数据");
                else {
                    initView();
                }
            }
        }.execute(URLUtil.BLOG + username);
    }

    private void initView() {
        lvColumn = (ListView) findViewById(R.id.lv_blogger_column);
        try {
            mAdapter = new SimpleTreeListViewAdapter<Column>(lvColumn, getActivity(), columns, 0);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf())
                        CommonUtil.showToast(getActivity(), node.getName());
                    if (node.isRoot())
                        CommonUtil.showToast(getActivity(), node.getDesc());
                }
            });
            lvColumn.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
