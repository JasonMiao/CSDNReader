package com.jason.csdnreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.app.MyApplication;
import com.jason.csdnreader.bean.NewsItem;
import com.squareup.picasso.Picasso;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by zzmiao on 2015/10/23.
 */
public class NewsListAdapter extends BGARecyclerViewAdapter<NewsItem> {

    public NewsListAdapter(RecyclerView recyclerView){
        super(recyclerView, R.layout.item_news);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, NewsItem model) {
        Picasso.with(MyApplication.getMyApplication()).load(model.getPic()).into((ImageView) viewHolderHelper.getView(R.id.iv_news_pic));
        viewHolderHelper.setText(R.id.tv_news_title, model.getTitle()).setText(R.id.tv_news_intro, model.getIntro()).setText(R.id.tv_news_date, model.getDate());
    }
}
