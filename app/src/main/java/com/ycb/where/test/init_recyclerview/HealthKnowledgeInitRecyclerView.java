package com.ycb.where.test.init_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ycb.where.test.DetailActivity;
import com.ycb.where.test.adapter.HealthKnowledgeAdapter;
import com.ycb.where.test.bean.HealthKnowledgeBean;
import com.ycb.where.test.bean.HealthKnowledgeDetailBean;
import com.ycb.where.test.utils.MyGsonRequestUtils;
import com.ycb.where.test.url.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/22.
 * 设置RecylcerView的item监听和SwipeRefreshLayout的上拉刷新和下拉加载
 */
public class HealthKnowledgeInitRecyclerView {
    HealthKnowledgeBean mHealthKnowledgeBean;
    LinearLayoutManager mLayoutManager;
    RecyclerView mLvDetail;
    Context mActivity;
    List<HealthKnowledgeBean.HealthKnowledgeDetail> mDetails;
    HealthKnowledgeAdapter adapter;
    SwipeRefreshLayout mSrl;
    int lastVisibleItem, i;
    int type;
    FrameLayout mFlPb;
    String fromPager;
    public HealthKnowledgeInitRecyclerView(HealthKnowledgeBean response, Context context, RecyclerView
            mLvDetail, SwipeRefreshLayout mSrl, int SOCIAL_HOTSPOT_TYPE, FrameLayout flPb,String fromPager) {
        this.mHealthKnowledgeBean = response;
        this.mActivity = context;
        this.mLvDetail = mLvDetail;
        this.mSrl = mSrl;
        this.type = SOCIAL_HOTSPOT_TYPE;
        this.mFlPb = flPb;
        this.fromPager=fromPager;
        initRecyleView();
    }

    private void initRecyleView() {
        //设置RecyclerView
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLvDetail.setLayoutManager(mLayoutManager);
        //固定item的大小
        mLvDetail.setHasFixedSize(true);
        //解析的数据是从旧的到新的，所有需要把list的数据进行倒序
        List<HealthKnowledgeBean.HealthKnowledgeDetail> list = mHealthKnowledgeBean.list;
        mDetails = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            mDetails.add(list.get(i));
        }
        adapter = new HealthKnowledgeAdapter(mActivity,
                mDetails);
        mLvDetail.setAdapter(adapter);
        mLvDetail.setBackgroundColor(Color.WHITE);
        mFlPb.setVisibility(View.GONE);
        int size = mHealthKnowledgeBean.list.size();
        int refresh=-1;
        //如果是第一次进入 那就先自动刷新 获取最新的数据
        if (refresh==-1){
            refreshData();
            refresh++;
        }
        if (size<15){
            addMore(true);
        }
        setRefreshListener();
    }

    private void setRefreshListener() {
        adapter.setOnItemClickListener(new HealthKnowledgeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, HealthKnowledgeBean.HealthKnowledgeDetail data) {
                String url = Url.getHealthKnowledgeDetail(data.id);
                MyGsonRequestUtils<HealthKnowledgeDetailBean> request = new
                        MyGsonRequestUtils<>(Request.Method.GET, url, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, HealthKnowledgeDetailBean.class, new Response.Listener<HealthKnowledgeDetailBean>() {
                    @Override
                    public void onResponse(HealthKnowledgeDetailBean response) {
                        Intent intent = new Intent(mActivity, DetailActivity.class);
                        intent.putExtra("url", response.url);
                        mActivity.startActivity(intent);
                    }
                }, mActivity);
                RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                requestQueue.add(request);

            }
        });
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        mLvDetail.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter
                        .getItemCount()) {
                    addMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 获取加载更多
     */
    private void addMore() {
        mSrl.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long id =-1 ;
                if (i == 0) {
                    id = mDetails.get(mDetails.size() - 1).id-300;
                   Log.e("TAG",""+mDetails.get(mDetails.size()-1).id);
                    i++;
                } else {
                    id = -1;
                }
                if (id == -1) {
                    Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                            .LENGTH_SHORT).show();
                    mSrl.setRefreshing(false);
                } else {
                    MyGsonRequestUtils<HealthKnowledgeBean> socialHotspotBeanMyGsonRequestUtils = new
                            MyGsonRequestUtils<>(Request.Method.GET, Url.getHealthKnowleLoadingMoreUrl
                            (id, type), new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                    error.printStackTrace();
                            Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT)
                                    .show();
                            mSrl.setRefreshing(false);
                        }
                    }, HealthKnowledgeBean.class, new Response
                            .Listener<HealthKnowledgeBean>() {
                        @Override
                        public void onResponse(HealthKnowledgeBean response) {
                            List<HealthKnowledgeBean.HealthKnowledgeDetail> moreData = response
                                    .list;
                            if (moreData.isEmpty()) {
                                Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                                        .LENGTH_SHORT).show();
                            } else {
                                List<HealthKnowledgeBean.HealthKnowledgeDetail> moreList = new
                                        ArrayList<HealthKnowledgeBean.HealthKnowledgeDetail>();
                                for (int i = moreData.size() - 1; i >= 0; i--) {
                                    moreList.add(moreData.get(i));
                                }
                                adapter.addMore(moreList);
                            }
                            mSrl.setRefreshing(false);
                        }
                    }, mActivity);
                    RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                    requestQueue.add(socialHotspotBeanMyGsonRequestUtils);
                }
            }
        }, 3000);
    }
    /**
     * 获取加载更多
     * 如果当前新闻少于15条 就先加载更多一次
     */
    private void addMore(boolean isAddMore) {
//        mSrl.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long id =-1 ;
                if (i == 0) {
                    id = mDetails.get(mDetails.size() - 1).id-200;
                    Log.e("TAG",""+mDetails.get(mDetails.size()-1).id);
                    i++;
                } else {
                    id = -1;
                }
                if (id == -1) {
                    Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                            .LENGTH_SHORT).show();
                    mSrl.setRefreshing(false);
                } else {
                    MyGsonRequestUtils<HealthKnowledgeBean> socialHotspotBeanMyGsonRequestUtils = new
                            MyGsonRequestUtils<>(Request.Method.GET, Url.getHealthKnowleLoadingMoreUrl
                            (id, type), new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                    error.printStackTrace();
                            Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT)
                                    .show();
                            mSrl.setRefreshing(false);
                        }
                    }, HealthKnowledgeBean.class, new Response
                            .Listener<HealthKnowledgeBean>() {
                        @Override
                        public void onResponse(HealthKnowledgeBean response) {
                            List<HealthKnowledgeBean.HealthKnowledgeDetail> moreData = response
                                    .list;
                            if (moreData.isEmpty()) {
                                Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                                        .LENGTH_SHORT).show();
                            } else {
                                List<HealthKnowledgeBean.HealthKnowledgeDetail> moreList = new
                                        ArrayList<HealthKnowledgeBean.HealthKnowledgeDetail>();
                                for (int i = moreData.size() - 1; i >= 0; i--) {
                                    moreList.add(moreData.get(i));
                                }
                                adapter.addMore(moreList);
                            }
                            mSrl.setRefreshing(false);
                        }
                    }, mActivity);
                    RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                    requestQueue.add(socialHotspotBeanMyGsonRequestUtils);
                }
            }
        }, 3000);
    }
    /**
     * 下拉刷新
     */
    private void refreshData() {
        mSrl.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyGsonRequestUtils<HealthKnowledgeBean> socialHotspotBeanMyGsonRequestUtils = new


                        MyGsonRequestUtils<>(Request.Method.GET, Url.getHealthKnowleRefreshUrl(mDetails.get
                        (0).id, type), new Response
                        .ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT).show();
                        mSrl.setRefreshing(false);
                    }
                }, HealthKnowledgeBean.class, new Response.Listener<HealthKnowledgeBean>() {
                    @Override
                    public void onResponse(HealthKnowledgeBean response) {
                        List<HealthKnowledgeBean.HealthKnowledgeDetail> newdatas = response.list;
                        if (newdatas.isEmpty()) {
                            Toast.makeText(mActivity, "已经是最新的数据啦!", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            ArrayList<HealthKnowledgeBean.HealthKnowledgeDetail> newLists = new
                                    ArrayList<>();
                            for (int i = newdatas.size() - 1; i >= 0; i--) {
                                newLists.add(newdatas.get(i));
                            }
                            SharedPreferences config = mActivity.getSharedPreferences
                                    ("config", Context.MODE_PRIVATE);
                            //保存最新数据的地址
                            config.edit().putString(fromPager, Url.getHealthKnowleRefreshUrl
                                    (newLists.get(newLists.size()/2).id, type)).commit();
                            adapter.addItem(newLists);
                        }
                        mSrl.setRefreshing(false);
                    }
                }, mActivity);
                RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                requestQueue.add(socialHotspotBeanMyGsonRequestUtils);
            }
        }, 5000);
    }


}
