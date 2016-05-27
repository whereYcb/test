package com.ycb.where.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.Timer;

/**
 * Created by 袁从斌-where on 2016/5/21.
 * 新闻的详情页
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WebView webView = (WebView) findViewById(R.id.web);
        final FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        String url = getIntent().getStringExtra("url");
        WebSettings settings = webView.getSettings();
        //支持JS
//        settings.setJavaScriptEnabled(true);
        //显示放大缩小按钮
        settings.setBuiltInZoomControls(true);
        //双击缩放
        settings.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            //网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                fl.setVisibility(View.VISIBLE);
            }
            //网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                fl.setVisibility(View.GONE);
            }

            /**
             * 所有的网页链接都在此方法中回调
             * @param view
             * @param url
             * @return true 所有的网页都自身跳转,不会跳转到浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
