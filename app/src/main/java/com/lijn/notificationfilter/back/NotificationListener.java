package com.lijn.notificationfilter.back;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.inservice.InServiceManager;

public class NotificationListener extends NotificationListenerService
{
    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        String packageName = sbn.getPackageName();
        String notificationKey = sbn.getKey();

        Notification notification = sbn.getNotification();
        String text = notification.extras.getString(Notification.EXTRA_TEXT);
        String bigText = notification.extras.getString(Notification.EXTRA_BIG_TEXT);
        String title = notification.extras.getString(Notification.EXTRA_TITLE);

        Program program = new Program(packageName);
        if (InServiceManager.getInstance()
                .doFilter(program, text, bigText, title))
        {
            cancelNotification(notificationKey);
        }
    }
}
