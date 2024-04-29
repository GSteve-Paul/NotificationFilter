package com.lijn.notificationfilter.global;

import android.content.Context;

import java.time.LocalDate;

public class ResourceHolder
{
    private static Context applicationContext;

    public static final String RuleProfileFileName = "rule_profile.json";

    public static final String GlobalProfileFileName = "global_profile.json";

    public static final String ProgramProfileFileName = "program_profile.json";

    public static String getLogFileName()
    {
        LocalDate date = LocalDate.now();
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
