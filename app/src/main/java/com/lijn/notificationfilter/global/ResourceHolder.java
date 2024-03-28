package com.lijn.notificationfilter.global;

import android.content.Context;

public class ResourceHolder
{
    private static Context applicationContext;

    public static void initialize(Context context)
    {
        applicationContext = context.getApplicationContext();
    }

    public static Context getContext()
    {
        return applicationContext;
    }
}
