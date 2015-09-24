package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jason.csdnreader.R;

/**
 * 资讯Fragment
 * Created by zzmiao on 2015/9/23.
 */
public class NewsFragment extends Fragment {
    private String mTopbarTitle = "资讯";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public String getmTopbarTitle() {
        return mTopbarTitle;
    }

    public void setmTopbarTitle(String mTopbarTitle) {
        this.mTopbarTitle = mTopbarTitle;
    }
}

