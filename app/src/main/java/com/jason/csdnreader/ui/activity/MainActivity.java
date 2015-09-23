package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jason.csdnreader.R;

public class MainActivity extends Activity implements OnClickListener {
    private LinearLayout mTabNews;
    private LinearLayout mTabBlog;
    private LinearLayout mTabFocus;
    private LinearLayout mTabProfile;

    private ImageButton mImgNews;
    private ImageButton mImgBlog;
    private ImageButton mImgFocus;
    private ImageButton mImgProfile;

    private TextView mTextViewNews;
    private TextView mTextViewBlog;
    private TextView mTextViewFocus;
    private TextView mTextViewProfile;

    private TextView mTextViewTopTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0); // 默认第一个
    }

    private void setSelect(int i) {
        switch (i) {
            case 0:
                mImgNews.setImageResource(R.drawable.tabbar_home_selected);
                mTextViewNews.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                mTextViewTopTitle.setText("资讯");
                break;
            case 1:
                mImgBlog.setImageResource(R.drawable.tabbar_discover_selected);
                mTextViewBlog.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                mTextViewTopTitle.setText("博客广场");
                break;
            case 2:
                mImgFocus.setImageResource(R.drawable.tabbar_message_center_selected);
                mTextViewFocus.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                mTextViewTopTitle.setText("博客圈");
                break;
            case 3:
                mImgProfile.setImageResource(R.drawable.tabbar_profile_selected);
                mTextViewProfile.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                mTextViewTopTitle.setText("个人中心");
                break;
            default:
                break;
        }
    }

    private void initEvent() {
        mTabNews.setOnClickListener(this);
        mTabBlog.setOnClickListener(this);
        mTabFocus.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);
    }

    private void initView() {
        mTabNews = (LinearLayout) findViewById(R.id.id_tab_news);
        mTabBlog = (LinearLayout) findViewById(R.id.id_tab_blog);
        mTabFocus = (LinearLayout) findViewById(R.id.id_tab_focus);
        mTabProfile = (LinearLayout) findViewById(R.id.id_tab_profile);

        mImgNews = (ImageButton) findViewById(R.id.id_tab_news_img);
        mImgBlog = (ImageButton) findViewById(R.id.id_tab_blog_img);
        mImgFocus = (ImageButton) findViewById(R.id.id_tab_focus_img);
        mImgProfile = (ImageButton) findViewById(R.id.id_tab_profile_img);

        mTextViewNews = (TextView) findViewById(R.id.id_tab_news_tv);
        mTextViewBlog = (TextView) findViewById(R.id.id_tab_blog_tv);
        mTextViewFocus = (TextView) findViewById(R.id.id_tab_focus_tv);
        mTextViewProfile = (TextView) findViewById(R.id.id_tab_profile_tv);

        mTextViewTopTitle = (TextView) findViewById(R.id.id_topbar_title);
    }

    @Override
    public void onClick(View v) {
        resetImgsandText();
        switch (v.getId()) {
            case R.id.id_tab_news:
                setSelect(0);
                break;
            case R.id.id_tab_blog:
                setSelect(1);
                break;
            case R.id.id_tab_focus:
                setSelect(2);
                break;
            case R.id.id_tab_profile:
                setSelect(3);
                break;
            default:
                break;
        }
    }

    /**
     * 切换图片和文字为默认状态
     */
    private void resetImgsandText() {
        mImgNews.setImageResource(R.drawable.tabbar_home);
        mImgBlog.setImageResource(R.drawable.tabbar_discover);
        mImgFocus.setImageResource(R.drawable.tabbar_message_center);
        mImgProfile.setImageResource(R.drawable.tabbar_profile);

        mTextViewNews.setTextColor(getResources().getColor(R.color.tabbar_text_default));
        mTextViewBlog.setTextColor(getResources().getColor(R.color.tabbar_text_default));
        mTextViewFocus.setTextColor(getResources().getColor(R.color.tabbar_text_default));
        mTextViewProfile.setTextColor(getResources().getColor(R.color.tabbar_text_default));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            new AlertView("退出CSDNReader？", null, "取消", new String[]{"退出"}, null, MainActivity.this, AlertView.Style.Alert,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) { // 退出
                                MainActivity.this.finish();
                            }
                        }
                    }
            ).show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
