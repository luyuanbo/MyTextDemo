package com.lus.mytextdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by 卢总 on 2017/11/23.
 */

public class GlideImaGlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片的简单用法
        Glide.with(context).load(path).into(imageView);
    }
}
