package com.lijn.notificationfilter.global;

import android.content.Context;

import java.time.LocalDate;

public class ResourceHolder
{
    public static final String RuleProfileFileName = "rule_profile.json";
    public static final String GlobalProfileFileName = "global_profile.json";
    public static final String ProgramProfileFileName = "program_profile.json";
    private static Context applicationContext;

    public static String getLogFileName(LocalDate date)
    {
        String year = Integer.toString(date.getYear());
        String month = Integer.toString(date.getMonthValue());
        String day = Integer.toString(date.getDayOfMonth());
        return year + "_" + month + "_" + day + ".log";
    }

    public static void initialize(Context context)
    {
        applicationContext = context.getApplicationContext();
    }

    public static Context getContext()
    {
        return applicationContext;
    }
}
