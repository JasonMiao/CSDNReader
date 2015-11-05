package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.FollowListAdapter;
import com.jason.csdnreader.bean.FollowItem;
import com.jason.csdnreader.bean.Page;
import com.jason.csdnreader.ui.view.DividerItemDecoration;
import com.jason.csdnreader.util.CommonUtil;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.HttpUtil;
import com.jason.csdnreader.util.URLUtil;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

/**
 * 我关注的对象
 *
 * Created by zzmiao on 2015/11/4.
 */
public class FollowActivity extends Activity implements View.OnClickListener, BGAOnRVItemClickListener, BGARefreshLayoutDelegate{

    private TextView tvTitle;
    private ImageView ivBack;
    private ImageView ivComment;
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private FollowListAdapter mAdapter;
    private Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_follow);
        initView();
        initEvent();
        page = new Page();
        page.setPageStart();
        beginRefreshing();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_content_topbar_title);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.follow_topbar_titile));
        ivBack = (ImageView) findViewById(R.id.iv_content_top_back);
        ivComment = (ImageView) findViewById(R.id.iv_content_top_comment);
        ivComment.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity_follow);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FollowListAdapter(mRecyclerView);
        mAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_activity_follow);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGAStickinessRefreshViewHolder(this, true));
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        ivComment.setOnClickListener(this);
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

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        new AsyncTask<String, Void, List<FollowItem>>() {
            @Override
            protected List<FollowItem> doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                List<FollowItem> list = DataUtil.getFollowingList(html);
                if (list.size() == 0)
                    return null;
                return list;
            }

            @Override
            protected void onPostExecute(List<FollowItem> list) {
                if (list == null) {
                    //TODO 提示没有数据，界面显示
                    CommonUtil.showToast(FollowActivity.this, "获取数据失败");
                }
//                mAdapter.addNewDatas(list);
                // 因为刷新是抓取页面第一页的所有数据，所以不能用add而是用set充值列表，而且page要重置
                mRefreshLayout.endRefreshing();
                mAdapter.setDatas(list);
                page.setPageStart();
                mRecyclerView.smoothScrollToPosition(0);
            }
        }.execute(URLUtil.getMyFollowUrl("1"));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new AsyncTask<String, Void, List<FollowItem>>() {
            @Override
            protected List<FollowItem> doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                List<FollowItem> list = DataUtil.getFollowingList(html);
                if (list.size() == 0)
                    return null;
                return list;
            }

            @Override
            protected void onPostExecute(List<FollowItem> list) {
                if (list == null) {
                    //TODO 提示没有数据，界面显示
                    CommonUtil.showToast(FollowActivity.this, "没有数据啦");
                }
                mRefreshLayout.endLoadingMore();
                mAdapter.addMoreDatas(list);
                page.addPage();
            }
        }.execute(URLUtil.getMyFollowUrl(page.getCurrentPage()));

        return true;
    }
}
