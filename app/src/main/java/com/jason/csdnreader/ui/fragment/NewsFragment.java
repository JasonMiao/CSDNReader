package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.NewsListAdapter;
import com.jason.csdnreader.app.MyApplication;
import com.jason.csdnreader.bean.NewsItem;
import com.jason.csdnreader.util.Constant;
import com.jason.csdnreader.util.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 资讯Fragment
 * <p/>
 * Created by zzmiao on 2015/9/23.
 */
public class NewsFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate ,BGAOnRVItemClickListener{
    private static final String TAG = NewsFragment.class.getSimpleName();
    private String mTopbarTitle = "资讯";
    private int news_type = 1; // 资讯类型默认为业界
    private View view;
    private View headerView;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private ConvenientBanner convenientBanner;
    private List<String> networkImages = Arrays.asList("http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png");
    private ArrayList<NewsItem> newsItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initData();
        initBanner();
        initRefreshLayout();
        initRecyclerView();
        return view;
    }

    private void initData() {
        newsItems = new ArrayList<NewsItem>();
        for (int i = 0; i < 8; i++) {
            newsItems.add(new NewsItem("http://img.ptcms.csdn.net/article/201510/21/562709dbdcca0_thumb.jpg", "标题" + i, "内容简介" + i, "2015-10-23 12:2" + i));
        }
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_frag_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsListAdapter(mRecyclerView);
        mAdapter.setDatas(newsItems);
        mAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.rl_frag_news);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGAMoocStyleRefreshViewHolder(getActivity(), true));
        mRefreshLayout.setCustomHeaderView(headerView, true);
    }

    /**
     * 设置Banner
     */
    private void initBanner() {
        headerView = View.inflate(getActivity(), R.layout.view_refresh_header, null);
        convenientBanner = (ConvenientBanner) headerView.findViewById(R.id.cb_frag_news);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    public String getmTopbarTitle() {
        return mTopbarTitle;
    }

    /**
     * 判断Fragment是否可见 ？？为啥没调用？？
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(TAG, "isVisibleToUser: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

        }
    }

    /**
     * 设置标题栏资讯类型，需要根据类型更新不同内容
     *
     * @param mTopbarTitle
     */
    public void setmTopbarTitle(String mTopbarTitle) {
        this.mTopbarTitle = mTopbarTitle;
        if (getString(R.string.n_news).equals(mTopbarTitle))
            news_type = Constant.NEWS_TYPE.NEWS;
        else if (getString(R.string.n_mobile).equals(mTopbarTitle))
            news_type = Constant.NEWS_TYPE.MOBILE;
        else if (getString(R.string.n_cloud).equals(mTopbarTitle))
            news_type = Constant.NEWS_TYPE.CLOUD;
        else if (getString(R.string.n_develop).equals(mTopbarTitle))
            news_type = Constant.NEWS_TYPE.DEVELOP;
        else if (getString(R.string.n_programmer).equals(mTopbarTitle))
            news_type = Constant.NEWS_TYPE.PROGRAMMER;
        setContent(news_type);
    }

    /**
     * 根据资讯类型，设置设置不同资讯内容
     *
     * @param news_type
     */
    private void setContent(int news_type) {

    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mRefreshLayout.endRefreshing();
            }
        }.execute();
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mRefreshLayout.endLoadingMore();
            }
        }.execute();
        return true;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Toast.makeText(getActivity(), mAdapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}

