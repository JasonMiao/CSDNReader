package com.jason.csdnreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.app.MyApplication;
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

    public BloggerShowAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
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
        if (position == 0)
            return new BloggerColumnFrag();
        else if (position == 1)
            return new BloggerBlogFrag();
        else
            return new BloggerCategoryFrag();
    }
}
