package com.ycb.where.test.base.detail.topeventsdetailpager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ycb.where.test.R;
import com.ycb.where.test.base.BaseDetailPager;
import com.ycb.where.test.bean.SocialHotspotBean;
import com.ycb.where.test.init_recyclerview.TopEventsInitRecyclerView;
import com.ycb.where.test.url.Url;
import com.ycb.where.test.utils.MyGsonRequestUtils;

/**
 * Created by 袁从斌-where on 2016/5/19.
 * 民生热点
 */
public class LivelihoodHotDetailPager extends BaseDetailPager {
    private RecyclerView mLvDetail;
    private SwipeRefreshLayout mSrl;
    private FrameLayout mFlPb;
    private ProgressBar mPb;
    private Button mReset;
    public LivelihoodHotDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.base_detail_pager, null);
        mFlPb = (FrameLayout) view.findViewById(R.id.fl_pb);
        mReset = (Button) view.findViewById(R.id.reset);
        mPb = (ProgressBar) view.findViewById(R.id.pb);
        mLvDetail = (RecyclerView) view.findViewById(R.id.detali_list);
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSrl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        return view;
    }

    @Override
    public void initData() {
        String url;
        mFlPb.setVisibility(View.VISIBLE);
        SharedPreferences sp = mActivity.getSharedPreferences("config", Context.MODE_PRIVATE);
         String hot_livelihood = sp.getString("hot_livelihood", "");
        if (hot_livelihood.isEmpty()){
            url = Url.HOT_LIVELIHOOD;
        }else{
            url = hot_livelihood;
        }
        //创建请求对象
        MyGsonRequestUtils<SocialHotspotBean> myGsonRequestUtils = new MyGsonRequestUtils<>(Request.Method.GET, url, new Response
                .ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPb.setVisibility(View.INVISIBLE);
                mReset.setVisibility(View.VISIBLE);
                mReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                        mReset.setVisibility(View.INVISIBLE);
                        mPb.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, SocialHotspotBean.class, new Response.Listener<SocialHotspotBean>() {
            @Override
            public void onResponse(SocialHotspotBean response) {
                new TopEventsInitRecyclerView(response,mActivity,mLvDetail,mSrl,Url.HOT_LIVELIHOOD_TYPE, mFlPb,"hot_livelihood");
            }
        }, mActivity);
        //创建队列
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        //发起请求
        requestQueue.add(myGsonRequestUtils);
    }
}
