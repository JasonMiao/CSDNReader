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
import com.jason.csdnreader.bean.Profile;
import com.jason.csdnreader.util.CommonUtil;
import com.jason.csdnreader.util.DataUtil;
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
public class ProfileFragment extends Fragment implements View.OnClickListener{
    private View view;
    private CircleImageView civProfile;
    private TextView tvNickName;
    private TextView tvIntro;
    private TextView tvFollowing;
    private TextView tvFans;
    private Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        view.findViewById(R.id.ll_profile_following).setOnClickListener(this);
        view.findViewById(R.id.rl_profile_blog).setOnClickListener(this);
        view.findViewById(R.id.rl_profile_collect).setOnClickListener(this);
        view.findViewById(R.id.rl_profile_set).setOnClickListener(this);
    }

    private void initView() {
        civProfile = (CircleImageView) view.findViewById(R.id.civ_profile);
        tvNickName = (TextView) view.findViewById(R.id.tv_profile_nick_name);
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
                    profile = DataUtil.getProfile(result);
                    Picasso.with(getActivity()).load(profile.getPic()).into(civProfile);
                    tvNickName.setText(profile.getNick_name());
                    tvIntro.setText(profile.getIntro());
                    tvFollowing.setText(profile.getFollowing());
                    tvFans.setText(profile.getFans());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_profile_following:

                break;
            case R.id.rl_profile_blog:
                CommonUtil.showToast(getActivity(), URLUtil.BLOG + profile.getUsername());
                break;
            case R.id.rl_profile_collect:

                break;
            case R.id.rl_profile_set:

                break;
        }
    }
}
