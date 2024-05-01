package com.lijn.notificationfilter.back.entity;

import android.app.Notification;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;

import java.time.LocalDateTime;

public class MyLog
{
    private NotificationType notificationType;
    private String logText;
    private LocalDateTime logTime;

    public MyLog(Notification notification,
                 NotificationType notificationType)
    {
        this.notificationType = notificationType;
        String txt = notification.extras.getString(Notification.EXTRA_TEXT);
        txt.replaceAll("\r\n", "");
        txt.replaceAll("\n", "");
        String bigText = notification.extras.getString(Notification.EXTRA_BIG_TEXT);
        bigText.replaceAll("\r\n", "");
        bigText.replaceAll("\n", "");
        String title = notification.extras.getString(Notification.EXTRA_TITLE);
        title.replaceAll("\r\n", "");
        title.replaceAll("\n", "");

        logTime = LocalDateTime.now();
        logText = title + " " + bigText + " " + txt + "\n";
    }

    public LocalDateTime getLogTime() {return logTime;}

    public String getLogText() {return logText;}

    public NotificationType getNotificationType() {return this.notificationType;}

    @Override
    public String toString()
    {
        return logTime + " " + notificationType + " " + logText;
    }
}
