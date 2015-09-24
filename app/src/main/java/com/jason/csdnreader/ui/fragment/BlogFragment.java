package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jason.csdnreader.R;

/**
 * 博客Fragment
 * Created by zzmiao on 2015/9/23.
 */
public class BlogFragment extends Fragment {
    private String mTopbarTitle = "博客广场";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    public String getmTopbarTitle() {
        return mTopbarTitle;
    }

    public void setmTopbarTitle(String mTopbarTitle) {
        this.mTopbarTitle = mTopbarTitle;
    }
}
