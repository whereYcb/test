package com.ycb.where.test.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by 袁从斌-where on 2016/5/19.
 * 详情页的基类
 */
public abstract class BaseDetailPager {
    public Activity mActivity;
    public View rootView;
    public BaseDetailPager(Activity activity){
        mActivity = activity;
        rootView=initViews();
    }

    public abstract View initViews() ;
    public void initData(){}
}
