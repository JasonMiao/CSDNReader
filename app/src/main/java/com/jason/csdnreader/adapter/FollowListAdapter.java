package com.jason.csdnreader.adapter;

import android.support.v7.widget.RecyclerView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.app.MyApplication;
import com.jason.csdnreader.bean.FollowItem;
import com.squareup.picasso.Picasso;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zzmiao on 2015/11/4.
 */
public class FollowListAdapter extends BGARecyclerViewAdapter<FollowItem> {
    public FollowListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_follow);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, FollowItem model) {
        Picasso.with(MyApplication.getMyApplication()).load(model.getPic()).placeholder(R.drawable.default_avatar).into((CircleImageView) viewHolderHelper.getView(R.id.civ_follow_pic));
        viewHolderHelper.setText(R.id.tv_follow_nick_name, model.getNick_name()).setText(R.id.tv_follow_intro, model.getIntro());
    }
}
