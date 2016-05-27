package com.ycb.where.test.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.ycb.where.test.R;


/**
 * Created by 袁从斌-where on 2016/5/19.
 */
public class BasePager {
    public Activity mActivity;
    public FrameLayout baseFlRoot;
    public View mView;

    public BasePager(Activity activity){
        mActivity = activity;
        initViews();

    }
    private void initViews(){
        mView = View.inflate(mActivity, R.layout.base_pager, null);
        baseFlRoot = (FrameLayout) mView.findViewById(R.id.rl_root);
    }
    public void initData(){}
}
