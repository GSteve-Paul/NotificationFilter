package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;

public class GlobalProfileReader
{
    private static volatile GlobalProfileReader mInstance;

    private GlobalProfileReader() {}

    public static GlobalProfileReader getInstance()
    {
        if(mInstance == null)
        {
            synchronized (GlobalProfileReader.class)
            {
                if(mInstance == null)
                {
                    mInstance = new GlobalProfileReader();
                }
            }
        }
        return mInstance;
    }

    private void initializeFile() throws IOException
    {
        Context context = ResourceHolder.getContext();
        File file = new File(context.getFilesDir(), ResourceHolder.GlobalProfileFileName);
        if(!file.exists())
        {
            FilterData data = new FilterData();
            if(file.createNewFile())
            {
                GlobalProfileWriter.getInstance().write(data);
            }
            else
            {
                throw new IOException("unable to create profile file");
            }
        }
        else
        {
            throw new IOException("unable to read profile file");
        }
    }

    private Reader getReader() throws IOException
    {
        Context context = ResourceHolder.getContext();
        InputStreamReader inputStreamReader = null;
        try
        {
            FileInputStream fis =
                    context.openFileInput(ResourceHolder.GlobalProfileFileName);
            inputStreamReader = new InputStreamReader(fis);
            return inputStreamReader;
        }
        catch (IOException e)
        {
            this.initializeFile();
            FileInputStream fis =
                    context.openFileInput(ResourceHolder.GlobalProfileFileName);
            inputStreamReader = new InputStreamReader(fis);
            return inputStreamReader;
        }
    }

    public FilterData read() throws IOException
    {
        Gson gson = new Gson();
        Reader reader = getReader();
        FilterData data = gson.fromJson(reader, FilterData.class);
        return data;
    }
}
