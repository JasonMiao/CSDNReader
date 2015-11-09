package com.jason.csdnreader.ui.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.BloggerShowAdapter;
import com.jason.csdnreader.bean.BloggerIntro;
import com.jason.csdnreader.util.DataUtil;
import com.jason.csdnreader.util.HttpUtil;
import com.jason.csdnreader.util.URLUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 博主个人主页
 *
 * Created by zzmiao on 2015/11/6.
 */
public class BloggerShowActivity extends FragmentActivity {
    public static final String USERNAME = "username";
    private String username;
    private IndicatorViewPager indicatorViewPager;
    private FixedIndicatorView indicator;
    private TextView tvUsername;
    private CircleImageView civPic;
    private TextView tvRank;
    private ImageView ivLevel;
    private TextView tvPV;
    private TextView tvIntegral;
    private BloggerShowAdapter mAdapter;
    private BloggerIntro bloggerIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloggershow);
        username = getIntent().getStringExtra(USERNAME);
        initView();
        initData();
    }

    private void initData() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String html = HttpUtil.sendGet(params[0]);
                return html;
            }

            @Override
            protected void onPostExecute(String result) {
                bloggerIntro = DataUtil.getBloggerIntro(result);
                Picasso.with(BloggerShowActivity.this).load(bloggerIntro.getPic()).into(civPic);
                tvRank.setText(bloggerIntro.getRank());
                Picasso.with(BloggerShowActivity.this).load(bloggerIntro.getLevel()).resize(90, 30).into(ivLevel);
                tvPV.setText(bloggerIntro.getPv());
                tvIntegral.setText(bloggerIntro.getIntegral());
            }
        }.execute(URLUtil.BLOG + username);
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_bloggershow);
        indicator = (FixedIndicatorView) findViewById(R.id.fiv_bloggershow);
        indicator.setScrollBar(new ColorBar(this, Color.BLUE, 5));
        // 设置滚动监听
        int selectColorId = R.color.tab_top_text_2;
        int unSelectColorId = R.color.tab_top_text_1;
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));
        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        mAdapter = new BloggerShowAdapter(getSupportFragmentManager(), username);
        indicatorViewPager.setAdapter(mAdapter);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_blogger_show_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvUsername = (TextView) findViewById(R.id.tv_blogger_username);
        tvUsername.setText(username);
        civPic = (CircleImageView) findViewById(R.id.civ_blogger_pic);
        tvRank = (TextView) findViewById(R.id.tv_blogger_rank);
        ivLevel = (ImageView) findViewById(R.id.iv_blogger_level);
        tvPV = (TextView) findViewById(R.id.tv_blogger_pv);
        tvIntegral = (TextView) findViewById(R.id.tv_blogger_integral);
    }

}
