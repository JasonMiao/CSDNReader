package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.BlogListAdapter;
import com.jason.csdnreader.bean.BlogItem;
import com.jason.csdnreader.bean.NewsItem;
import com.jason.csdnreader.bean.Page;
import com.jason.csdnreader.ui.view.DividerItemDecoration;
import com.jason.csdnreader.util.Constant;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.URLUtil;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 博客Fragment
 *
 * Created by zzmiao on 2015/9/23.
 */
public class BlogFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener {
    private static final String TAG = NewsFragment.class.getSimpleName();
    private String mTopbarTitle = "博客广场";
    private int blog_type = Constant.BLOG_TYPE.MOBILE; // 博客类型默认为移动
    private Page page;
    private View view;
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private BlogListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blog, container, false);
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_frag_blog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BlogListAdapter(mRecyclerView);
        mAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.rl_frag_blog);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGAMoocStyleRefreshViewHolder(getActivity(), true));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = new Page();
        page.setPageStart();
    }

    @Override
    public void onStart() {
        super.onStart();
        beginRefreshing();
    }

    public String getmTopbarTitle() {
        return mTopbarTitle;
    }

    public void setmTopbarTitle(String mTopbarTitle) {
        this.mTopbarTitle = mTopbarTitle;
        if (getString(R.string.b_web).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.WEB;
        else if (getString(R.string.b_other).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.OTHER;
        else if (getString(R.string.b_software).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.SOFTWARE;
        else if (getString(R.string.b_cloud).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.CLOUD;
        else if (getString(R.string.b_code).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.CODE;
        else if (getString(R.string.b_database).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.DATABASE;
        else if (getString(R.string.b_enterprise).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.ENTERPRISE;
        else if (getString(R.string.b_mobile).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.MOBILE;
        else if (getString(R.string.b_www).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.WWW;
        else if (getString(R.string.b_system).equals(mTopbarTitle))
            blog_type = Constant.BLOG_TYPE.SYSTEM;
        setContent();
    }

    private void setContent() {
        // 切换类型后适配器要清空
        mAdapter.clear();
        // 上来加载更多的分页要重置
        page.setPageStart();
        // 切换后自动执行下拉刷新加载数据
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Toast.makeText(getActivity(), mAdapter.getItem(position).getLink(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        new AsyncTask<String, Void, List<BlogItem>>() {

            @Override
            protected List<BlogItem> doInBackground(String... params) {
                List<BlogItem> list = DataUtil.getBlogList(params[0]);
                if (list.size() == 0)
                    return null;
                return list;
            }

            @Override
            protected void onPostExecute(List<BlogItem> list) {
                mAdapter.addNewDatas(list);
                mRefreshLayout.endRefreshing();
                mRecyclerView.smoothScrollToPosition(0);
            }
        }.execute(URLUtil.BlogUrl.getBlogUrl(blog_type, 0, "1"));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new AsyncTask<String, Void, List<BlogItem>>() {

            @Override
            protected List<BlogItem> doInBackground(String... params) {
                List<BlogItem> list = DataUtil.getBlogList(params[0]);
                if (list.size() == 0)
                    return null;
                return list;
            }

            @Override
            protected void onPostExecute(List<BlogItem> list) {
                mAdapter.addMoreDatas(list);
                mRefreshLayout.endLoadingMore();
                page.addPage();
            }
        }.execute(URLUtil.BlogUrl.getBlogUrl(blog_type, 0, page.getCurrentPage()));
        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }
}
