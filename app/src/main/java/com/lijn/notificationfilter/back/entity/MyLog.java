package com.lijn.notificationfilter.back.entity;

import android.app.Notification;

import java.time.LocalTime;

public class MyLog
{
    InServiceType inServiceType;
    private String logText;

    public MyLog(Notification notification, InServiceType inServiceType)
    {
        this.inServiceType = inServiceType;

        String txt = notification.extras.getString(Notification.EXTRA_TEXT);
        txt.replaceAll("\r\n", "");
        txt.replaceAll("\n", "");
        String bigText = notification.extras.getString(Notification.EXTRA_BIG_TEXT);
        bigText.replaceAll("\r\n", "");
        bigText.replaceAll("\n", "");
        String title = notification.extras.getString(Notification.EXTRA_TITLE);
        title.replaceAll("\r\n", "");
        title.replaceAll("\n", "");

        LocalTime localTime = LocalTime.now();
        String time = localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond();
        logText = time + " " + title + " " + bigText + " " + txt + "\n";
    }

    public String getLogText() {return this.logText;}

    @Override
    public String toString()
    {
        return logText;
    }
}
