package com.jason.csdnreader.ui.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.ui.activity.BloggerShowActivity;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.HttpUtil;
import com.jason.csdnreader.util.URLUtil;
import com.shizhefei.fragment.LazyFragment;

/**
 * 博主专栏
 * Created by zzmiao on 2015/11/8.
 */
public class BloggerColumnFrag extends LazyFragment {
    private String username;
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_blogger_column);
        TextView textView = (TextView) findViewById(R.id.tv_blogger_column);
        username = getArguments().getString(BloggerShowActivity.USERNAME);
        textView.setText(username);
        initData();
    }

    private void initData() {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                DataUtil.getColumn(html);
                return null;
            }
        }.execute(URLUtil.BLOG + username);
    }

}
