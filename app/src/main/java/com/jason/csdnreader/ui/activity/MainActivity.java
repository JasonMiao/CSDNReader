package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jason.csdnreader.R;
import com.jason.csdnreader.adapter.GroupAdapter;
import com.jason.csdnreader.ui.fragment.BlogFragment;
import com.jason.csdnreader.ui.fragment.NewsFragment;
import com.jason.csdnreader.ui.fragment.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {
    private LinearLayout mTabNews;
    private LinearLayout mTabBlog;
//    private LinearLayout mTabFocus;
    private LinearLayout mTabProfile;

    private ImageButton mImgNews;
    private ImageButton mImgBlog;
//    private ImageButton mImgFocus;
    private ImageButton mImgProfile;

    private TextView mTextViewNews;
    private TextView mTextViewBlog;
//    private TextView mTextViewFocus;
    private TextView mTextViewProfile;

    private TextView mTextViewTopTitle;

    private NewsFragment mFragmentNews;
    private BlogFragment mFragmentBlog;
//    private FocusFragment mFragmentFocus;
    private ProfileFragment mFragmentProfile;
    private int currentFragment = 0; // 表示当前Fragment, 0-News/1-Blog/2-Focus/3-Profile

    private GroupAdapter groupAdapter;
    private ArrayList<String> groups = new ArrayList<String>();
    private PopupWindow mPopupWindow;
    private View contentView;
    private ListView listView;

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
        resetImgsandText();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                // 显示对应的fragment
                if (mFragmentNews == null) {
                    mFragmentNews = new NewsFragment();
                    transaction.add(R.id.id_content, mFragmentNews);
                } else {
                    transaction.show(mFragmentNews);
                }
                currentFragment = 0;
                // 设置底部Tab图标和文字状态
                mImgNews.setImageResource(R.drawable.tabbar_home_selected);
                mTextViewNews.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                // 设置顶部分组信息
                setTopTitleText(mFragmentNews.getmTopbarTitle());
                mTextViewTopTitle.setClickable(true);
                groups.clear();
                groups.add(getString(R.string.n_news));
                groups.add(getString(R.string.n_mobile));
                groups.add(getString(R.string.n_cloud));
                groups.add(getString(R.string.n_develop));
                groups.add(getString(R.string.n_programmer));
                break;
            case 1:
                // 显示对应的fragment
                if (mFragmentBlog == null) {
                    mFragmentBlog = new BlogFragment();
                    transaction.add(R.id.id_content, mFragmentBlog);
                } else {
                    transaction.show(mFragmentBlog);
                }
                currentFragment = 1;
                // 设置底部Tab图标和文字状态
                mImgBlog.setImageResource(R.drawable.tabbar_discover_selected);
                mTextViewBlog.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                // 设置顶部分组信息
                setTopTitleText(mFragmentBlog.getmTopbarTitle());
                mTextViewTopTitle.setClickable(true);
                groups.clear();
                groups.add(getString(R.string.b_mobile));
                groups.add(getString(R.string.b_web));
                groups.add(getString(R.string.b_enterprise));
                groups.add(getString(R.string.b_code));
                groups.add(getString(R.string.b_www));
                groups.add(getString(R.string.b_database));
                groups.add(getString(R.string.b_system));
                groups.add(getString(R.string.b_cloud));
                groups.add(getString(R.string.b_software));
                groups.add(getString(R.string.b_other));
                break;
//            case 2:
//                // 显示对应的fragment
//                if (mFragmentFocus == null) {
//                    mFragmentFocus = new FocusFragment();
//                    transaction.add(R.id.id_content, mFragmentFocus);
//                } else {
//                    transaction.show(mFragmentFocus);
//                }
//                currentFragment = 2;
//                // 设置底部Tab图标和文字状态
//                mImgFocus.setImageResource(R.drawable.tabbar_message_center_selected);
//                mTextViewFocus.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
//                // 设置顶部标题
//                mTextViewTopTitle.setText("博客圈");
//                mTextViewTopTitle.setClickable(false);
//                break;
            case 2:
                // 显示对应的fragment
                if (mFragmentProfile == null) {
                    mFragmentProfile = new ProfileFragment();
                    transaction.add(R.id.id_content, mFragmentProfile);
                } else {
                    transaction.show(mFragmentProfile);
                }
                currentFragment = 3;
                // 设置底部Tab图标和文字状态
                mImgProfile.setImageResource(R.drawable.tabbar_profile_selected);
                mTextViewProfile.setTextColor(getResources().getColor(R.color.tabbar_text_selected));
                // 设置顶部标题
                mTextViewTopTitle.setText("个人中心");
                mTextViewTopTitle.setClickable(false);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mFragmentNews != null) {
            transaction.hide(mFragmentNews);
        }
        if (mFragmentBlog != null) {
            transaction.hide(mFragmentBlog);
        }
//        if (mFragmentFocus != null) {
//            transaction.hide(mFragmentFocus);
//        }
        if (mFragmentProfile != null) {
            transaction.hide(mFragmentProfile);
        }
    }

    private void initEvent() {
        mTabNews.setOnClickListener(this);
        mTabBlog.setOnClickListener(this);
//        mTabFocus.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);
        mTextViewTopTitle.setOnClickListener(this);
    }

    private void initView() {
        mTabNews = (LinearLayout) findViewById(R.id.id_tab_news);
        mTabBlog = (LinearLayout) findViewById(R.id.id_tab_blog);
//        mTabFocus = (LinearLayout) findViewById(R.id.id_tab_focus);
        mTabProfile = (LinearLayout) findViewById(R.id.id_tab_profile);

        mImgNews = (ImageButton) findViewById(R.id.id_tab_news_img);
        mImgBlog = (ImageButton) findViewById(R.id.id_tab_blog_img);
//        mImgFocus = (ImageButton) findViewById(R.id.id_tab_focus_img);
        mImgProfile = (ImageButton) findViewById(R.id.id_tab_profile_img);

        mTextViewNews = (TextView) findViewById(R.id.id_tab_news_tv);
        mTextViewBlog = (TextView) findViewById(R.id.id_tab_blog_tv);
//        mTextViewFocus = (TextView) findViewById(R.id.id_tab_focus_tv);
        mTextViewProfile = (TextView) findViewById(R.id.id_tab_profile_tv);

        mTextViewTopTitle = (TextView) findViewById(R.id.id_topbar_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_news:
                setSelect(0);
                break;
            case R.id.id_tab_blog:
                setSelect(1);
                break;
//            case R.id.id_tab_focus:
//                setSelect(2);
//                break;
            case R.id.id_tab_profile:
                setSelect(2);
                break;
            case R.id.id_topbar_title:
                showPopwindow(v);
            default:
                break;
        }
    }

    private void showPopwindow(View parent) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        Log.e("TAG", String.valueOf(screenWidth) + "*" + String.valueOf(screenHeight));
        if (mPopupWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.group_list, null);
            listView = (ListView) contentView.findViewById(R.id.lv_group);
            groupAdapter = new GroupAdapter(this, groups);
            listView.setAdapter(groupAdapter);
            mPopupWindow = new PopupWindow(contentView, screenWidth / 2, screenHeight / 3);
        }
        // 使其聚集
        mPopupWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap)null));
        // showAsDropDown的偏移量参考的是anchor(依靠)控件底部左边(bottom-left corner of the anchor view)
        mPopupWindow.showAsDropDown(parent, mTextViewTopTitle.getWidth() / 2 - mPopupWindow.getWidth() / 2, 0);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String title = groups.get(position);
        setTopTitleText(title);
        switch (currentFragment){
            case 0:
                mFragmentNews.setmTopbarTitle(title);
                break;
            case 1:
                mFragmentBlog.setmTopbarTitle(title);
                break;
            case 2:
            case 3:
                break;
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    public void setTopTitleText(String str){
        mTextViewTopTitle.setText(str);
    }

    /**
     * 切换图片和文字为默认状态
     */
    private void resetImgsandText() {
        mImgNews.setImageResource(R.drawable.tabbar_home);
        mImgBlog.setImageResource(R.drawable.tabbar_discover);
//        mImgFocus.setImageResource(R.drawable.tabbar_message_center);
        mImgProfile.setImageResource(R.drawable.tabbar_profile);

        mTextViewNews.setTextColor(getResources().getColor(R.color.tabbar_text_default));
        mTextViewBlog.setTextColor(getResources().getColor(R.color.tabbar_text_default));
//        mTextViewFocus.setTextColor(getResources().getColor(R.color.tabbar_text_default));
        mTextViewProfile.setTextColor(getResources().getColor(R.color.tabbar_text_default));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            new AlertView("退出CSDN阅读器？", null, "取消", new String[]{"退出"}, null, MainActivity.this, AlertView.Style.Alert,
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
