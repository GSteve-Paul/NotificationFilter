package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;

public class GlobalProfileWriter
{
    private static volatile GlobalProfileWriter mInstance = null;

    private GlobalProfileWriter() {}

    public static GlobalProfileWriter getInstance()
    {
        if (mInstance == null)
        {
            synchronized (GlobalProfileWriter.class)
            {
                if(mInstance == null)
                {
                    mInstance = new GlobalProfileWriter();
                }
            }
        }
        return mInstance;
    }

    private OutputStreamWriter getWriter() throws FileNotFoundException
    {
        Context context = ResourceHolder.getContext();
        FileOutputStream fileOutputStream = context.openFileOutput
                (ResourceHolder.GlobalProfileFileName, Context.MODE_PRIVATE);
        return new OutputStreamWriter(fileOutputStream);
    }

    public void write(FilterData data) throws IOException
    {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        Writer writer = this.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
