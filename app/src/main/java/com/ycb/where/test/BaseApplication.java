package com.ycb.where.test;

import android.app.Application;
import android.os.Handler;
import android.os.Process;

/**
 * Created by 袁从斌-where on 2016/5/20.
 */
public class BaseApplication extends Application{
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }
    public static BaseApplication getApplication() {
        return application;
    }
}
