package com.example.xunhu.xunchat.View;

import android.app.Application;
import android.content.Context;
/**
 * Created by xunhu on 6/13/2017.
 */

public class XunApplication extends Application {
    private static XunApplication mInstance;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
    }
    public static synchronized XunApplication getInstance() {
        return mInstance;
    }
    public static Context getContext() {
        return context;
    }
}
