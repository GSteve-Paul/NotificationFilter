package com.lijn.notificationfilter.back.util;

import android.content.Context;

import java.time.LocalDate;

public class ResourceHolder
{
    public static final String RuleProfileFileName = "rule_profile.json";
    public static final String GlobalProfileFileName = "global_profile.json";
    public static final String ProgramProfileFileName = "program_profile.json";
    public static final String LogFileName = "notification_log.log";
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
