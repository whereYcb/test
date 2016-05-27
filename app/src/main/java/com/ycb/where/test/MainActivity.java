package com.ycb.where.test;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ycb.where.test.base.implemens.HealthKnowledgePager;
import com.ycb.where.test.base.implemens.ReadingPager;
import com.ycb.where.test.base.implemens.TopEventsPager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ListView mLvDetail;
    private FrameLayout mFlRoot;
    private DrawerLayout mDl;
    private ActionBarDrawerToggle mMToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initUI();
        initData();

    }
    private void initUI() {
        setTheme(R.style.CyanTheme);
        setContentView(R.layout.activity_main);

        mDl = (DrawerLayout) findViewById(R.id.dl);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("热点时事");
        mFlRoot = (FrameLayout) findViewById(R.id.flRoot);
        mLvDetail = (ListView) findViewById(R.id.lv_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMToggle = new ActionBarDrawerToggle(this, mDl, mToolbar, R.string.String_open, R.string.String_close);
        mDl.addDrawerListener(mMToggle);
        mMToggle.syncState();

    }

    private void initData() {
        final String[] detail = new String[]{"热点事件","健康知识","天狗美阅"};
        mLvDetail.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,detail));
        mLvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                    if (position==0){
                        TopEventsPager topEventsPager = new TopEventsPager(MainActivity.this);

                        topEventsPager.initData();
                        mFlRoot.removeAllViews();
                        mFlRoot.addView(topEventsPager.mView);
                        mToolbar.setTitle("热点时事");
                        mDl.closeDrawers();
                    }else if(position==1){
                        HealthKnowledgePager healthKnowledgePager = new HealthKnowledgePager
                                (MainActivity.this);
                        healthKnowledgePager.initData();
                        mFlRoot.removeAllViews();
                        mFlRoot.addView(healthKnowledgePager.mView);
                        mToolbar.setTitle("健康知识");
                        mDl.closeDrawers();
                    }else if(position==2){
                        ReadingPager readingPager = new ReadingPager(MainActivity.this);
                        readingPager.initData();
                        mFlRoot.removeAllViews();
                        mFlRoot.addView(readingPager.mView);
                        mToolbar.setTitle("美图");
                        mDl.closeDrawers();
                    }
            }
        });
        TopEventsPager topEventsPager = new TopEventsPager(MainActivity.this);
        topEventsPager.initData();
        mFlRoot.addView(topEventsPager.mView);
    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();        //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }




}
