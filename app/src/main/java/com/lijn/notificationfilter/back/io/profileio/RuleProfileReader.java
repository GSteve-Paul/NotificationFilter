package com.lijn.notificationfilter.back.io.profileio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.DataReader;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RuleProfileReader extends DataReader<List<FilterData>>
{
    private volatile static RuleProfileReader mInstance = null;

    private RuleProfileReader() {}

    public static RuleProfileReader getInstance()
    {
        if (mInstance == null)
        {
            synchronized (RuleProfileReader.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RuleProfileReader();
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
            FileInputStream fileInputStream =
                    context.openFileInput(ResourceHolder.RuleProfileFileName);
            inputStreamReader = new InputStreamReader(fileInputStream
                    , StandardCharsets.UTF_8);
            return inputStreamReader;
        }
        catch (IOException ioException)
        {
            return null;
        }
    }

    @Override
    public List<FilterData> read() throws IOException
    {
        Gson gson = new Gson();
        Reader reader = getReader();
        List<FilterData> filterDataList = gson.fromJson(reader, List.class);
        reader.close();
        return filterDataList;
    }
}
