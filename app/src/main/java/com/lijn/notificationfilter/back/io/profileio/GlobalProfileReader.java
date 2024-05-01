package com.lijn.notificationfilter.back.io.profileio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.DataReader;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GlobalProfileReader extends DataReader<FilterData>
{
    private static volatile GlobalProfileReader mInstance;

    private GlobalProfileReader()
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFilesDir();

        Path path = Paths.get(file.getAbsolutePath());
        path.resolve(ResourceHolder.GlobalProfileFileName);

        file = path.toFile();
        try
        {
            FilterData data = new FilterData();
            if (!file.exists())
            {
                file.createNewFile();
                GlobalProfileWriter.getInstance().write(data);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static GlobalProfileReader getInstance()
    {
        if (mInstance == null)
        {
            synchronized (GlobalProfileReader.class)
            {
                if (mInstance == null)
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
