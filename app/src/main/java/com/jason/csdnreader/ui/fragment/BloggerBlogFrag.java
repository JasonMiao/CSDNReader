package com.jason.csdnreader.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.BloggerBlogAdapter;
import com.jason.csdnreader.bean.BlogItem;
import com.jason.csdnreader.bean.Page;
import com.jason.csdnreader.ui.activity.BloggerShowActivity;
import com.jason.csdnreader.ui.view.DividerItemDecoration;
import com.jason.csdnreader.util.CommonUtil;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.HttpUtil;
import com.jason.csdnreader.util.URLUtil;
import com.shizhefei.fragment.LazyFragment;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

/**
 * 博主最新博客
 *
 * Created by zzmiao on 2015/11/8.
 */
public class BloggerBlogFrag extends LazyFragment implements BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate{
    private String username;
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private BloggerBlogAdapter mAdapter;
    private Page page;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_blogger_blog);
        username = getArguments().getString(BloggerShowActivity.USERNAME);
        initView();
        page = new Page();
        page.setPageStart();
        beginRefreshing();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_blogger_blog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BloggerBlogAdapter(mRecyclerView);
        mAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_blogger_blog);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGAStickinessRefreshViewHolder(getActivity(), true));
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        CommonUtil.showToast(getActivity(), mAdapter.getItem(position).getLink());
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
        }.execute(URLUtil.BLOG + username);
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
                    CommonUtil.showToast(getActivity(), "没有数据啦");
                }
                // 因为刷新是抓取页面第一页的所有数据，所以不能用add而是用set充值列表，而且page要重置
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreDatas(list);
                page.addPage();
            }
        }.execute(URLUtil.BLOG + username + "/article/list/" + page.getCurrentPage());

        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }
}
