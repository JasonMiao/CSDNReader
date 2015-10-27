package com.jason.csdnreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.app.MyApplication;
import com.jason.csdnreader.bean.BlogItem;
import com.squareup.picasso.Picasso;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by zzmiao on 2015/10/27.
 */
public class BlogListAdapter extends BGARecyclerViewAdapter<BlogItem> {
    public BlogListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_blog);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, BlogItem model) {
        Picasso.with(MyApplication.getMyApplication()).load(model.getPic()).placeholder(R.drawable.default_avatar).into((ImageView) viewHolderHelper.getView(R.id.iv_blog_pic));
        viewHolderHelper.setText(R.id.tv_blog_title, model.getTitle()).setText(R.id.tv_blog_intro, model.getIntro()).setText(R.id.tv_blog_date, model.getDate()).setText(R.id.tv_blog_readtimes, model.getReadtimes()).setText(R.id.tv_blog_comments, model.getComments());
    }
}
