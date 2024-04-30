package com.lijn.notificationfilter.back.io.profileio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.DataReader;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;

public class GlobalProfileReader extends DataReader<FilterData>
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

    @Override
    protected Reader getReader()
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
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FilterData read() throws IOException
    {
        Gson gson = new Gson();
        Reader reader = getReader();
        FilterData data = gson.fromJson(reader, FilterData.class);
        reader.close();
        return data;
    }
}
