package com.jason.csdnreader.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.SimpleTreeListViewAdapter;
import com.jason.csdnreader.bean.Category;
import com.jason.csdnreader.bean.Column;
import com.jason.csdnreader.ui.activity.BloggerShowActivity;
import com.jason.csdnreader.ui.activity.CategoryMoreActivity;
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
 * 博主博文分类
 *
 * Created by zzmiao on 2015/11/8.
 */
public class BloggerCategoryFrag extends LazyFragment {
    private String username;
    private List<Category> categories = new ArrayList<>();
    private ListView lvCategory;
    private TreeListViewAdapter mAdapter;
    private TextView tvNodata;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_blogger_category);
        username = getArguments().getString(BloggerShowActivity.USERNAME);
        initData();
    }

    private void initData() {
        CommonUtil.showLoadingDialog(getActivity(), "数据加载中...");
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                categories = DataUtil.getCategory(html);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                CommonUtil.dismissLoadingDialog();
                initView();
            }
        }.execute(URLUtil.BLOG + username);
    }

    private void initView() {
        lvCategory = (ListView) findViewById(R.id.lv_blogger_category);
        tvNodata = (TextView) findViewById(R.id.tv_blogger_category_nodata);
        if (categories.size() > 0) {
            lvCategory.setVisibility(View.VISIBLE);
            try {
                mAdapter = new SimpleTreeListViewAdapter<Category>(lvCategory, getActivity(), categories, 0);
                mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                    @Override
                    public void onClick(Node node, int position) {
                        if ("".equals(node.getDesc())) // 直接获取地址去展示文章
                            CommonUtil.showToast(getActivity(), node.getLink());
                        else if (Integer.parseInt(node.getDesc()) > 15) { // 类别里文章太多 需要跳转到另一个页面去显示列表
//                            CommonUtil.showToast(getActivity(), "需要跳转页面去展开");
                            Intent intent = new Intent(getActivity(), CategoryMoreActivity.class);
                            intent.putExtra(CategoryMoreActivity.BLOG_URL, node.getLink());
                            intent.putExtra(CategoryMoreActivity.CATEGORY, node.getName());
                            startActivity(intent);
                        } else if (Integer.parseInt(node.getDesc()) == 0)
                            CommonUtil.showToast(getActivity(), "该分类下没有文章");
                    }
                });
                lvCategory.setAdapter(mAdapter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            tvNodata.setVisibility(View.VISIBLE);
        }
    }
}
