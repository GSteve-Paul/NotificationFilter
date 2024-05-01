package com.lijn.notificationfilter.back.manager.filterservice;

import android.app.Notification;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.manager.displayservice.DisplayManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DisplayHandler<T> implements InvocationHandler
{
    T handler;

    public DisplayHandler(T handler) {this.handler = handler;}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        NotificationType type = (NotificationType) method.invoke(handler, args);

        Notification notification = null;
        for (Object arg : args)
        {
            if (arg instanceof Notification)
            {
                notification = (Notification) arg;
            }
        }

        DisplayManager.getInstance().doDisplay();
        return type;
    }
}
