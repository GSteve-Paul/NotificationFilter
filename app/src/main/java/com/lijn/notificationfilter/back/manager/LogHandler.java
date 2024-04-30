package com.lijn.notificationfilter.back.manager;

import android.app.Notification;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler<T> implements InvocationHandler
{
    T target;

    public LogHandler(T target)
    {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        NotificationType type = (NotificationType) method.invoke(target, args);

        Notification notification = null;
        for (Object arg : args)
        {
            if(arg instanceof Notification)
            {
                notification = (Notification) arg;
            }
        }

        if(ProgramSettingManager.getInstance().getProgramSetting()
                .getLogNotificationVariety(type))
        {
            MyLog log = new MyLog(notification, type);
            LogManager.getInstance().writeLog(log);
        }

        return type;
    }
}
