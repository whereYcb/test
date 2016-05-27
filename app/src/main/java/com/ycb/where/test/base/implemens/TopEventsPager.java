package com.ycb.where.test.base.implemens;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.ycb.where.test.R;
import com.ycb.where.test.base.BaseDetailPager;
import com.ycb.where.test.base.BasePager;
import com.ycb.where.test.base.detail.topeventsdetailpager.EducationalHotDetailPager;
import com.ycb.where.test.base.detail.topeventsdetailpager.EntertainmentHotDetailPager;
import com.ycb.where.test.base.detail.topeventsdetailpager.FinanceHotspotDetailPager;
import com.ycb.where.test.base.detail.topeventsdetailpager.LivelihoodHotDetailPager;
import com.ycb.where.test.base.detail.topeventsdetailpager.SocialHotspotDetailPager;
import com.ycb.where.test.base.detail.topeventsdetailpager.SportsHotDetailPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/19.
 * 热点事件页
 */
public class TopEventsPager extends BasePager {
    private ViewPager mViewPager;
    private List<BaseDetailPager> mPagerList;
    private TabLayout mTabLayout;
    private String[] mTitle;
    public TopEventsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.item_pager, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp);
        PagerTabStrip mPagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pager_tab_strip);
        mPagerTabStrip.setTabIndicatorColor(Color.RED);
        mTitle = new String[]{"社会热点","民生热点","娱乐热点","财经热点","体育热点","教育热点"};
        mPagerList = new ArrayList<>();
        mPagerList.add(new SocialHotspotDetailPager(mActivity));
        mPagerList.add(new LivelihoodHotDetailPager(mActivity));
        mPagerList.add(new EntertainmentHotDetailPager(mActivity));
        mPagerList.add(new FinanceHotspotDetailPager(mActivity));
        mPagerList.add(new SportsHotDetailPager(mActivity));
        mPagerList.add(new EducationalHotDetailPager(mActivity));
        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();
            }
        });
        mViewPager.setAdapter(adapter);

        mPagerList.get(0).initData();

        baseFlRoot.removeAllViews();
        baseFlRoot.addView(view);
    }
    private class  MyPagerAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseDetailPager basePager = mPagerList.get(position);
            container.addView(basePager.rootView);
            return basePager.rootView;
        }
    }

}
