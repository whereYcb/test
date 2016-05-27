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
import com.ycb.where.test.base.detail.healthknowledgedetailpager.BabyDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.ChildDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.ChildrenDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.DietDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.HealthyDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.LifeDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.LoseWeightDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.MaintainDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.OldManDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.PsychologyDetailPager;
import com.ycb.where.test.base.detail.healthknowledgedetailpager.SeasonsDetailPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁从斌-where on 2016/5/19.
 * 健康知识详情页
 */
public class HealthKnowledgePager extends BasePager {
    private ViewPager mViewPager;
    private List<BaseDetailPager> mPagerList;
    private String[] mTitle;

    public HealthKnowledgePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.item_pager, null);
        mActivity.setTheme(R.style.CyanTheme);
        mViewPager = (ViewPager) view.findViewById(R.id.vp);
        PagerTabStrip mPagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pager_tab_strip);
        mPagerTabStrip.setTabIndicatorColor(Color.RED);
        mTitle = new String[]{"减肥瘦身", "私密生活", "女性保养", "男性健康", "孕婴手册",  "育儿宝典", "健康饮食", "老人健康", "孩子健康", "四季养生", "心理健康"};
        mPagerList = new ArrayList<>();


        mPagerList.add(new LoseWeightDetailPager(mActivity));
        mPagerList.add(new LifeDetailPager(mActivity));
        mPagerList.add(new MaintainDetailPager(mActivity));
        mPagerList.add(new HealthyDetailPager(mActivity));
        mPagerList.add(new BabyDetailPager(mActivity));
        mPagerList.add(new ChildDetailPager(mActivity));
        mPagerList.add(new DietDetailPager(mActivity));
        mPagerList.add(new OldManDetailPager(mActivity));
        mPagerList.add(new ChildrenDetailPager(mActivity));
        mPagerList.add(new SeasonsDetailPager(mActivity));
        mPagerList.add(new PsychologyDetailPager(mActivity));
        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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

    private class MyPagerAdapter extends PagerAdapter {
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
