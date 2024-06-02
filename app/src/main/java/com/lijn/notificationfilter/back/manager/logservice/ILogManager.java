package com.lijn.notificationfilter.back.manager.logservice;

import com.lijn.notificationfilter.back.entity.MyLog;

import java.io.IOException;
import java.util.List;

public sealed interface ILogManager permits LogManager
{
    void flush() throws IOException;

    void writeLog(MyLog log) throws IOException;

    List<MyLog> getLogCache();
}
