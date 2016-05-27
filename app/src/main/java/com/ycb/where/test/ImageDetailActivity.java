package com.ycb.where.test;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ycb.where.test.bean.ImageDetailBean;
import com.ycb.where.test.utils.MyGsonRequestUtils;
import com.ycb.where.test.url.Url;
import com.ycb.where.test.utils.GlideUtils;

import java.util.List;

/**
 * 图片详情页
 */
public class ImageDetailActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<ImageDetailBean.detail> mList;
    private TextView mTvTotal;
    private FrameLayout mFlPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String detail = getIntent().getStringExtra("url");
        MyGsonRequestUtils<ImageDetailBean> imageDetailBeanMyGsonRequestUtils = new MyGsonRequestUtils<>(Request
                .Method.GET, detail, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                initUI();
            }
        }, ImageDetailBean.class, new Response.Listener<ImageDetailBean>() {
            @Override
            public void onResponse(ImageDetailBean response) {
                System.out.println(response.toString());
                mList = response.list;
                initUI();
            }
        }, ImageDetailActivity.this);
        RequestQueue requestQueue = Volley.newRequestQueue(ImageDetailActivity.this);
        requestQueue.add(imageDetailBeanMyGsonRequestUtils);

    }

    private void initUI() {
        setContentView(R.layout.activity_image_detail);
        mViewPager = (ViewPager) findViewById(R.id.image_vp);
        mFlPb = (FrameLayout) findViewById(R.id.fl_pb);
        mTvTotal = (TextView) findViewById(R.id.total);
        mTvTotal.setText("1/" + mList.size());
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mTvTotal.setText(position +1 +  "/" + mList.size());
            }
        });
    }
    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             ImageView imageView = new ImageView(ImageDetailActivity.this);
            ImageDetailBean.detail detail = mList.get(position);

            GlideUtils.GlideUtils(Url.IMG+detail.src,imageView);

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
        }
    }
}
