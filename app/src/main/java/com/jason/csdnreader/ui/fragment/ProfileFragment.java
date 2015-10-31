package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.util.HttpUtil;
import com.jason.csdnreader.util.URLUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.io.UnsupportedEncodingException;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心Fragment
 *
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private View view;
    private BGARefreshLayout mRefreshLayout;
    private CircleImageView civProfile;
    private TextView tvName;
    private TextView tvIntro;
    private TextView tvFollowing;
    private TextView tvFans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initRefreshLayout();
        initView();
        return view;
    }

    private void initView() {
        civProfile = (CircleImageView) view.findViewById(R.id.civ_profile);
        tvName = (TextView) view.findViewById(R.id.tv_profile_name);
        tvIntro = (TextView) view.findViewById(R.id.tv_profile_intro);
        tvFollowing = (TextView) view.findViewById(R.id.tv_profile_following);
        tvFans = (TextView) view.findViewById(R.id.tv_profile_fans);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 获取并解析出个人信息
         */
        HttpUtil.get(URLUtil.MYCSDN, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = "";
                try {
                    result = new String(responseBody, "utf-8");
//                    System.out.print(result);
                    // 解析个人信息

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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
