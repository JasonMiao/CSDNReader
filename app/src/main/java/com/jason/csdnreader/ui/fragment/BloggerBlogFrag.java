package com.jason.csdnreader.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.shizhefei.fragment.LazyFragment;

/**
 * Created by zzmiao on 2015/11/8.
 */
public class BloggerBlogFrag extends LazyFragment {
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_blogger_blog);
        TextView textView = (TextView) findViewById(R.id.tv_blogger_blog);
        textView.setText("Blog");
    }
}
