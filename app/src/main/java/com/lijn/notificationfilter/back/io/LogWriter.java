package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class LogWriter
{
    private volatile static LogWriter mInstance = null;

    private LogWriter() {}

    public static LogWriter getInstance()
    {
        if (mInstance == null)
        {
            synchronized (LogWriter.class)
            {
                if (mInstance == null)
                {
                    mInstance = new LogWriter();
                }
            }
        }
        return mInstance;
    }

    private FileWriter getWriter() throws IOException
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFilesDir();

        Path path = Paths.get(file.getAbsolutePath());
        path.resolve("log");

        String logFileName = ResourceHolder.getLogFileName();
        path = path.resolve(logFileName);

        file = path.toFile();
        FileWriter fw = new FileWriter(file);
        return fw;
    }

    public void writeLogs(List<MyLog> myLogList) throws IOException
    {
        FileWriter fw = this.getWriter();
        BufferedWriter bw = new BufferedWriter(fw);
        for (MyLog log : myLogList)
        {
            bw.write(log.toString());
        }
        bw.flush();
        bw.close();
    }
}
