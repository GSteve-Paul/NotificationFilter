package com.lijn.notificationfilter.back.util;

import android.app.Application;

public class MyApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        ResourceHolder.initialize(this);
    }
}
