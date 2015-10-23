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

/**
 * 个人中心Fragment
 *
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends Fragment {
    private View view;
    private ImageView ivShow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ivShow = (ImageView) view.findViewById(R.id.iv_show);
        // 测试开源图片加载缓存库
        Picasso.with(getActivity().getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").resize(500, 500).centerCrop().into(ivShow);
        return view;
    }

}
