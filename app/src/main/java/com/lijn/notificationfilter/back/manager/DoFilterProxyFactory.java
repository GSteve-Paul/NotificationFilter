package com.lijn.notificationfilter.back.manager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DoFilterProxyFactory
{
    private static InvocationHandler handler = null;

    private static InvocationHandler getHandler(InServiceManager target)
    {
        if(handler == null)
        {
            handler = new LogHandler<>(target);
        }
        return handler;
    }

    public static IDoFilter getLogProxy(InServiceManager target)
    {
        return (IDoFilter) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                getHandler(target)
        );
    }
}
