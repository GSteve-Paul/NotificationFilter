package com.lijn.notificationfilter.back.manager;

import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.io.LogWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LogManager
{
    private final static Integer cacheSize = 1000;
    private final static Integer bufferSize = 500;
    private volatile static LogManager mInstance;
    private List<MyLog> logBuffer;

    private ConcurrentLinkedDeque<MyLog> logCache;

    private LogManager()
    {
        logBuffer = new ArrayList<>();
        logCache = new ConcurrentLinkedDeque<>();
    }

    public static LogManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (LogManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new LogManager();
                }
            }
        }
        return mInstance;
    }

    public void flush() throws IOException
    {
        LogWriter logWriter = LogWriter.getInstance();
        logWriter.writeLogs(logBuffer);
        logBuffer.clear();
    }

    public void writeLog(MyLog log) throws IOException
    {
        if(!ProgramSettingManager.getInstance().getProgramSetting()
                .getLogNotificationVariety(log.getNotificationType()))
        {
            return;
        }
        logCache.add(log);
        if (logCache.size() > cacheSize)
        {
            logCache.removeFirst();
        }

        logBuffer.add(log);
        if (logBuffer.size() > bufferSize)
        {
            this.flush();
        }
    }

    public ConcurrentLinkedDeque<MyLog> getLogCache()
    {
        return logCache;
    }
}
