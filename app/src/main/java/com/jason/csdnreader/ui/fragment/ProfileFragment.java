package com.jason.csdnreader.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jason.csdnreader.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

/**
 * 个人中心Fragment
 *
 * Created by zzmiao on 2015/9/23.
 */
public class ProfileFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

}
