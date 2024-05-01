package com.lijn.notificationfilter.back.manager.filterservice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LogProxyFactory
{
    private static InvocationHandler handler = null;

    private static InvocationHandler getHandler(InServiceManager target)
    {
        if (handler == null)
        {
            handler = new LogHandler<>(target);
        }
        return handler;
    }

    public static InServiceManager getLogProxy(InServiceManager target)
    {
        return (InServiceManager) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                getHandler(target)
        );
    }
}
