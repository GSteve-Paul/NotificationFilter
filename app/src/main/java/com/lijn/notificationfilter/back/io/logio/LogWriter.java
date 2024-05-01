package com.lijn.notificationfilter.back.io.logio;

import android.content.Context;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.io.DataWriter;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LogWriter extends DataWriter<List<MyLog>>
{
    private volatile static LogWriter mInstance = null;

    private String logFileName;

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

    @Override
    protected FileWriter getWriter()
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFilesDir();

        Path path = Paths.get(file.getAbsolutePath());
        path.resolve("log");

        path = path.resolve(logFileName);

        file = path.toFile();
        try
        {
            FileWriter fw = new FileWriter(file);
            return fw;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(List<MyLog> logs) throws IOException
    {
        this.logFileName = ResourceHolder.getLogFileName
                (logs.get(logs.size() - 1).getLogTime().toLocalDate());
        FileWriter fw = this.getWriter();
        BufferedWriter bw = new BufferedWriter(fw);
        for (MyLog log : logs)
        {
            bw.write(log.toString());
        }
        bw.flush();
        bw.close();
    }
}
