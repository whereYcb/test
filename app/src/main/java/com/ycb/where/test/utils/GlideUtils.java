package com.ycb.where.test.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ycb.where.test.BaseApplication;
import com.ycb.where.test.R;

/**
 * Created by 袁从斌-where on 2016/5/25.
 * 获取网络图片
 */
public class GlideUtils {
    public String url;
    public static void  GlideUtils(String url,ImageView iv){
        Glide.with(BaseApplication.getApplication())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(iv);

    }
}
