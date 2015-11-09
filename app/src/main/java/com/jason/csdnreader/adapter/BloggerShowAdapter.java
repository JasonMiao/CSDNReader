package com.jason.csdnreader.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.app.MyApplication;
import com.jason.csdnreader.ui.activity.BloggerShowActivity;
import com.jason.csdnreader.ui.fragment.BloggerBlogFrag;
import com.jason.csdnreader.ui.fragment.BloggerCategoryFrag;
import com.jason.csdnreader.ui.fragment.BloggerColumnFrag;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * Created by zzmiao on 2015/11/8.
 */
public class BloggerShowAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private String[] titles = {"专栏", "博客", "分类"};
    private LayoutInflater inflate;
    private String username;

    public BloggerShowAdapter(FragmentManager fragmentManager, String username){
        super(fragmentManager);
        this.username = username;
        inflate = LayoutInflater.from(MyApplication.getMyApplication().getApplicationContext());
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        Log.e("getViewForTab", position + "");
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(titles[position % titles.length]);
        textView.setPadding(20, 0, 20, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        Log.e("getFragmentForPage", position + "");
        Fragment fragment = null;
        if (position == 0)
            fragment = new BloggerColumnFrag();
        else if (position == 1)
            fragment = new BloggerBlogFrag();
        else
            fragment = new BloggerCategoryFrag();
        Bundle bundle = new Bundle();
        bundle.putString(BloggerShowActivity.USERNAME, username);
        fragment.setArguments(bundle);
        return fragment;
    }
}
