package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jason.csdnreader.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 个人中心Fragment
 *
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private View view;
    private BGARefreshLayout mRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Picasso.with(getActivity()).load("http://my.csdn.net/uploads/avatar/8/7/3/1_bagecelia.jpg").into((ImageView) view.findViewById(R.id.civ_profile));
        initRefreshLayout();
        return view;
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.rl_frag_profile);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGAMoocStyleRefreshViewHolder(getActivity(), true));
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
