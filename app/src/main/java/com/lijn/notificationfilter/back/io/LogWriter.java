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

        LocalDate date = LocalDate.now();
        String year = Integer.toString(date.getYear());
        String month = Integer.toString(date.getMonthValue());
        String day = Integer.toString(date.getDayOfMonth());
        path = path.resolve(year + "_" + month + "_" + day + ".log");

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
