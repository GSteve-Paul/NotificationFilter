package com.lijn.notificationfilter.back.manager.logservice;

import android.util.Log;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.io.logio.LogWriter;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class LogManager implements ILogManager
{
    private final String TAG = "LogManager";
    private final static Integer cacheSize = 1000;
    private final static Integer bufferSize = 500;
    private volatile static LogManager mInstance;
    private List<MyLog> logBuffer;
    private List<MyLog> logCache;

    private LogManager()
    {
        logBuffer = new ArrayList<>();
        logCache = new ArrayList<>();
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
    public void flush()
    {
        try
        {
            LogWriter logWriter = LogWriter.getInstance();
            logWriter.write(logBuffer);
            logBuffer.clear();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void writeLog(MyLog log) throws IOException
    {
        Log.i(TAG, "writeLog: " + log.toString());

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

        Log.i(TAG, "start addLogCache: " + logCache.size());
        logCache.add(log);
        if (logCache.size() > cacheSize)
        {
            logCache.remove(0);
        }
        Log.i(TAG, "finish addLogCache: " + logCache.size());

        Log.i(TAG, "start addLogBuffer: " + logBuffer.size());
        logBuffer.add(log);
        if (logBuffer.size() > bufferSize)
        {
            this.flush();
        }
        Log.i(TAG, "finish addLogBuffer: " + logBuffer.size());

    }

    @Override
    public List<MyLog> getLogCache()
    {
        return logCache;
    }
}
