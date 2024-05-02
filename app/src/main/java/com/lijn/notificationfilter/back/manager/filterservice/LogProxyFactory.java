package com.lijn.notificationfilter.back.manager.filterservice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class LogProxyFactory
{
    private static InvocationHandler handler = null;

    private static InvocationHandler getHandler(IDoFilter target)
    {
        if (handler == null)
        {
            handler = new LogHandler<>(target);
        }
        return handler;
    }

    public static IDoFilter getLogProxy(IDoFilter target)
    {
        return (IDoFilter) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                getHandler(target)
        );
    }
}
