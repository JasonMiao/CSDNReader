package com.jason.csdnreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.bean.BlogItem;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by zzmiao on 2015/11/11.
 */
public class BloggerBlogAdapter extends BGARecyclerViewAdapter<BlogItem> {
    public BloggerBlogAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_blogger_blog);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, BlogItem model) {
        ImageView ivType =viewHolderHelper.getView(R.id.iv_blogger_blog_type);
        if (model.getType() == 0)
            ivType.setImageResource(R.drawable.ico_origin);
        else if (model.getType() == 1)
            ivType.setImageResource(R.drawable.ico_repost);
        else
            ivType.setImageResource(R.drawable.ico_translated);
        viewHolderHelper.setText(R.id.tv_blogger_blog_title, model.getTitle())
                .setText(R.id.tv_blogger_blog_intro, model.getIntro())
                .setText(R.id.tv_blogger_blog_date, model.getDate())
                .setText(R.id.tv_blogger_blog_readtimes, model.getReadtimes())
                .setText(R.id.tv_blogger_blog_comments, model.getComments());
    }
}
