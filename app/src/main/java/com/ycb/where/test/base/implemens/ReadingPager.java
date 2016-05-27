package com.ycb.where.test.base.implemens;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.ycb.where.test.R;
import com.ycb.where.test.base.BaseDetailPager;
import com.ycb.where.test.base.BasePager;
import com.ycb.where.test.base.detail.readingpager.FlirtyPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/19.
 * 美阅页面
 */
public class ReadingPager extends BasePager {
    public ReadingPager(Activity activity) {
        super(activity);
    }
    @Override
    public void initData() {
        FlirtyPager flirtyPager = new FlirtyPager(mActivity);
        flirtyPager.initData();
        baseFlRoot.removeAllViews();
        baseFlRoot.addView(flirtyPager.rootView);
    }



}
