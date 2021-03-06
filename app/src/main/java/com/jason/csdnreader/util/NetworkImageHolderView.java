package com.jason.csdnreader.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.squareup.picasso.Picasso;

/**
 * 网络图片加载
 *
 * Created by zzmiao on 2015/10/22.
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        Picasso.with(context).load(data).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
