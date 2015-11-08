package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.BloggerShowAdapter;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloggershow);
        username = getIntent().getStringExtra(USERNAME);
        initView();
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
        indicatorViewPager.setAdapter(new BloggerShowAdapter(getSupportFragmentManager()));
        ImageView ivBack = (ImageView) findViewById(R.id.iv_blogger_show_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvUsername = (TextView) findViewById(R.id.tv_blogger_username);
        tvUsername.setText(username);
    }
}
