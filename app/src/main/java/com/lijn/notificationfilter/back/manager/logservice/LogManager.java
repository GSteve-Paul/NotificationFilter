package com.lijn.notificationfilter.back.manager.logservice;

import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.io.logio.LogWriter;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class LogManager implements ILogManager
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

    @Override
    public void flush() throws IOException
    {
        LogWriter logWriter = LogWriter.getInstance();
        logWriter.write(logBuffer);
        logBuffer.clear();
    }

    @Override
    public void writeLog(MyLog log) throws IOException
    {
        if (!ProgramSettingManager.getInstance().getProgramSetting()
                .getLogNotificationVariety(log.getNotificationType()))
        {
            return;
        }

        if (!logBuffer.isEmpty())
        {
            MyLog lastLog = logBuffer.get(logBuffer.size() - 1);
            LocalDate lastLocalDate = lastLog.getLogTime().toLocalDate();
            LocalDate nowLocalDate = LocalDate.now();
            if (nowLocalDate.isAfter(lastLocalDate))
            {
                this.flush();
            }
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

    @Override
    public ConcurrentLinkedDeque<MyLog> getLogCache()
    {
        return logCache;
    }
}
