package com.lijn.notificationfilter.back.service;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.manager.filterservice.IDoFilter;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;
import com.lijn.notificationfilter.back.manager.filterservice.LogProxyFactory;

public class NotificationListener extends NotificationListenerService
{
    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        Log.d("NotificationListener", "onNotificationPosted: ");

        String packageName = sbn.getPackageName();
        String notificationKey = sbn.getKey();

        Notification notification = sbn.getNotification();
        Program program = new Program(packageName);
        IDoFilter doFilterManager = LogProxyFactory
                .getLogProxy(InServiceManager.getInstance());
        NotificationType type = doFilterManager.doFilter(program, notification);
        if (type == NotificationType.INTERCEPTED)
        {
            this.cancelNotification(notificationKey);
        }
    }
}
