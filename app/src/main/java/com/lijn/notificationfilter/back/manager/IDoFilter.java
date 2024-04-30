package com.lijn.notificationfilter.back.manager;

import android.app.Notification;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;

public interface IDoFilter
{
    NotificationType doFilter(Program program, Notification notification);
}
