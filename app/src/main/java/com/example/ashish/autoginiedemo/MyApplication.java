package com.example.ashish.autoginiedemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ashish on 2/1/2016.
 */
public class MyApplication extends Application {
    private static MyApplication sInstence;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstence=this;
    }
    public MyApplication getsInstence(){
        return sInstence;
    }
    public static Context getContext(){
        return sInstence.getApplicationContext();
    }
}
