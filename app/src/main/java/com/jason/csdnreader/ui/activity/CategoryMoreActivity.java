package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.CategoryMoreAdapter;
import com.jason.csdnreader.bean.BlogItem;
import com.jason.csdnreader.bean.Page;
import com.jason.csdnreader.ui.view.DividerItemDecoration;
import com.jason.csdnreader.util.CommonUtil;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.HttpUtil;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

/**
 * 分类更多详情
 *
 * Created by zzmiao on 2015/11/10.
 */
public class CategoryMoreActivity extends Activity implements View.OnClickListener, BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String BLOG_URL = "blog_url";
    public static final String CATEGORY = "category";
    private String url;
    private String category;
    private TextView tvTitle;
    private ImageView ivBack;
    private ImageView ivComment;
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private CategoryMoreAdapter mAdapter;
    private Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorymore);
        url = getIntent().getStringExtra(BLOG_URL);
        category = getIntent().getStringExtra(CATEGORY);
        initView();
        page = new Page();
        page.setPageStart();
        beginRefreshing();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_content_topbar_title);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(category);
        ivBack = (ImageView) findViewById(R.id.iv_content_top_back);
        ivBack.setOnClickListener(this);
        ivComment = (ImageView) findViewById(R.id.iv_content_top_comment);
        ivComment.setVisibility(View.INVISIBLE);
        ivComment.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity_category_more);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CategoryMoreAdapter(mRecyclerView);
        mAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_activity_category_more);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGAStickinessRefreshViewHolder(this, true));
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        CommonUtil.showToast(this, mAdapter.getItem(position).getLink());
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        new AsyncTask<String, Void, List<BlogItem>>() {
            @Override
            protected List<BlogItem> doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                List<BlogItem> list = DataUtil.getBlog(html);
                if (list.size() == 0)
                    return null;
                return list;
            }

            @Override
            protected void onPostExecute(List<BlogItem> list) {
                if (list == null) {

                }
                // 因为刷新是抓取页面第一页的所有数据，所以不能用add而是用set充值列表，而且page要重置
                mRefreshLayout.endRefreshing();
                mAdapter.setDatas(list);
                page.setPageStart();
                mRecyclerView.smoothScrollToPosition(0);
            }
        }.execute(url);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new AsyncTask<String, Void, List<BlogItem>>() {
            @Override
            protected List<BlogItem> doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                List<BlogItem> list = DataUtil.getBlog(html);
                if (list.size() == 0)
                    return null;
                return list;
            }

            @Override
            protected void onPostExecute(List<BlogItem> list) {
                if (list == null) {
                    CommonUtil.showToast(CategoryMoreActivity.this, "没有数据啦");
                }
                // 因为刷新是抓取页面第一页的所有数据，所以不能用add而是用set充值列表，而且page要重置
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreDatas(list);
                page.addPage();
            }
        }.execute(url + "/" + page.getCurrentPage());

        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_content_top_back:
                finish();
                break;
            case R.id.iv_content_top_comment:

                break;
        }
    }
}
