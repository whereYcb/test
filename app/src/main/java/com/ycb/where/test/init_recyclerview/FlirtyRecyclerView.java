package com.ycb.where.test.init_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ycb.where.test.DetailActivity;
import com.ycb.where.test.adapter.FlirtyAdapter;
import com.ycb.where.test.bean.FlirtyBean;
import com.ycb.where.test.utils.MyGsonRequestUtils;
import com.ycb.where.test.url.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/22.
 * 设置RecylcerView的item监听和SwipeRefreshLayout的上拉刷新和下拉加载
 */
public class FlirtyRecyclerView {
    FlirtyBean mFlirtyBean;
    RecyclerView mLvDetail;
    Context mActivity;
    List<FlirtyBean.tngouDetail> mDetails;
    FlirtyAdapter adapter;
    SwipeRefreshLayout mSrl;
    FrameLayout flPb;
    int lastVisibleItem, i;
    int type;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    String fromPager;
    public FlirtyRecyclerView(FlirtyBean response, Context context, RecyclerView
            mLvDetail, SwipeRefreshLayout mSrl, int SOCIAL_HOTSPOT_TYPE, FrameLayout flPb,String fromPager) {
        this.mFlirtyBean = response;
        this.mActivity = context;
        this.mLvDetail = mLvDetail;
        this.mSrl = mSrl;
        this.type = SOCIAL_HOTSPOT_TYPE;
        this.flPb = flPb;
        this.fromPager=fromPager;
        initRecyleView();
    }

    private void initRecyleView() {
        //设置RecyclerView
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mLvDetail.setLayoutManager(mStaggeredGridLayoutManager);
        //固定item的大小
//        mLvDetail.setHasFixedSize(true);
        //解析的数据是从旧的到新的，所有需要把list的数据进行倒序
        List<FlirtyBean.tngouDetail> list = mFlirtyBean.tngou;
        mDetails = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            mDetails.add(list.get(i));
        }
        adapter = new FlirtyAdapter(mActivity,
                mDetails);
        mLvDetail.setAdapter(adapter);
        mLvDetail.setBackgroundColor(Color.WHITE);
        flPb.setVisibility(View.GONE);
        int size = mFlirtyBean.tngou.size();
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
        adapter.setOnItemClickListener(new FlirtyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, FlirtyBean.tngouDetail data) {
                Toast.makeText(mActivity, data.title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mActivity, DetailActivity.class);
//                intent.putExtra("url", data.fromurl);
                mActivity.startActivity(intent);
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
                System.out.println("adapter:" + adapter.getItemCount());
                System.out.println("StaggeredGridLayoutManager:"+mStaggeredGridLayoutManager.getItemCount());
                boolean isBottom =false;
                // 由于瀑布流有多个列 所以此处用数组存储
                int positions[] = new
                        int[mStaggeredGridLayoutManager.getColumnCountForAccessibility(null,null)];
                // 获取lastItem的positions
                mStaggeredGridLayoutManager.findLastVisibleItemPositions(positions);
                for (int i = 0; i < positions.length; i++) {
                    /**
                     * 判断lastItem的底边到recyclerView顶部的距离
                     * 是否小于recyclerView的高度
                     * 如果小于或等于 说明滚动到了底部
                     */
                    if (mStaggeredGridLayoutManager.findViewByPosition(positions[i]).getBottom()<=mStaggeredGridLayoutManager.getHeight()){
                        //表示到底了
                            isBottom=true;
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom) {
                    addMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem= mStaggeredGridLayoutManager.getItemCount()-1;
            }
        });
    }

    /**
     * 加载更多
     */
    private void addMore() {
        mSrl.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long id = -1;
                if (i == 0) {
                    i++;
                    id = mDetails.get(mDetails.size() - 1).id - 300;
                } else {
                    id = -1;
                }
                if (id == -1) {
                    Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                            .LENGTH_SHORT).show();
                    mSrl.setRefreshing(false);

                } else {
                    MyGsonRequestUtils<FlirtyBean> flirtyBeanMyGsonRequestUtils = new
                            MyGsonRequestUtils<>(Request.Method.GET, Url.getFlirtyLoadingMoreUrl
                            (id, type), new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                    error.printStackTrace();
                            Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT)
                                    .show();
                            mSrl.setRefreshing(false);
                        }
                    }, FlirtyBean.class, new Response
                            .Listener<FlirtyBean>() {
                        @Override
                        public void onResponse(FlirtyBean response) {
                            List<FlirtyBean.tngouDetail> moreData = response
                                    .tngou;
                            if (moreData.isEmpty()) {
                                Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                                        .LENGTH_SHORT).show();
                            } else {
                                List<FlirtyBean.tngouDetail> moreList = new
                                        ArrayList<FlirtyBean.tngouDetail>();
                                for (int i = moreData.size() - 1; i >= 0; i--) {
                                    moreList.add(moreData.get(i));
                                }
                                adapter.addMore(moreList);
                            }
                            mSrl.setRefreshing(false);
                        }
                    }, mActivity);
                    RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                    requestQueue.add(flirtyBeanMyGsonRequestUtils);
                }
            }
        }, 3000);
    }
    /**
     * 加载更多
     * 如果当前新闻少于15条 就先加载更多一次
     */
    private void addMore(boolean isAddMore) {
//        mSrl.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long id = -1;
                if (i == 0) {
                    i++;
                    id = mDetails.get(mDetails.size() - 1).id - 300;
                } else {
                    id = -1;
                }
                if (id == -1) {
                    Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                            .LENGTH_SHORT).show();
                    mSrl.setRefreshing(false);

                } else {
                    MyGsonRequestUtils<FlirtyBean> flirtyBeanMyGsonRequestUtils = new
                            MyGsonRequestUtils<>(Request.Method.GET, Url.getFlirtyLoadingMoreUrl
                            (id, type), new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                    error.printStackTrace();
                            Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT)
                                    .show();
                            mSrl.setRefreshing(false);
                        }
                    }, FlirtyBean.class, new Response
                            .Listener<FlirtyBean>() {
                        @Override
                        public void onResponse(FlirtyBean response) {
                            List<FlirtyBean.tngouDetail> moreData = response
                                    .tngou;
                            if (moreData.isEmpty()) {
                                Toast.makeText(mActivity, "已经没有更多数据啦!", Toast
                                        .LENGTH_SHORT).show();
                            } else {
                                List<FlirtyBean.tngouDetail> moreList = new
                                        ArrayList<FlirtyBean.tngouDetail>();
                                for (int i = moreData.size() - 1; i >= 0; i--) {
                                    moreList.add(moreData.get(i));
                                }
                                adapter.addMore(moreList);
                            }
                            mSrl.setRefreshing(false);
                        }
                    }, mActivity);
                    RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                    requestQueue.add(flirtyBeanMyGsonRequestUtils);
                }
            }
        }, 3000);
    }
    /**
     * 下拉刷新
     */
    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyGsonRequestUtils<FlirtyBean> flirtyBeanMyGsonRequestUtils = new
                        MyGsonRequestUtils<>(Request.Method.GET, Url.getRefreshUrl(mDetails.get
                        (0).id, type), new Response
                        .ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT).show();
                        mSrl.setRefreshing(false);
                    }
                }, FlirtyBean.class, new Response.Listener<FlirtyBean>() {
                    @Override
                    public void onResponse(FlirtyBean response) {
                        List<FlirtyBean.tngouDetail> newdatas = response.tngou;
                        if (newdatas.isEmpty()) {
                            Toast.makeText(mActivity, "已经是最新的数据啦!", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            ArrayList<FlirtyBean.tngouDetail> newLists = new
                                    ArrayList<>();
                            for (int i = newdatas.size() - 1; i >= 0; i--) {
                                newLists.add(newdatas.get(i));
                            }
                            SharedPreferences config = mActivity.getSharedPreferences
                                    ("config", Context.MODE_PRIVATE);
                            //保存最新数据的地址
                            config.edit().putString(fromPager, Url.getFlirtyRefreshUrl
                                    (newLists.get(newLists.size() - 1).id, type)).commit();
                            adapter.addItem(newLists);
                        }
                        mSrl.setRefreshing(false);
                    }
                }, mActivity);
                RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
                requestQueue.add(flirtyBeanMyGsonRequestUtils);
            }
        }, 5000);
    }


}
