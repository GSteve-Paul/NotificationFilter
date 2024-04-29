package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RuleProfileReader
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

    private void initializeFile() throws IOException
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFileStreamPath(ResourceHolder.RuleProfileFileName);
        if (!file.exists())
        {
            List<FilterData> filterDataList = new ArrayList<>();
            if (file.createNewFile())
            {
                RuleProfileWriter.getInstance().writeFilterData(filterDataList);
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
            FileInputStream fileInputStream =
                    context.openFileInput(ResourceHolder.RuleProfileFileName);
            inputStreamReader = new InputStreamReader(fileInputStream
                    , StandardCharsets.UTF_8);
            return inputStreamReader;
        }
        catch (IOException ioException)
        {
            this.initializeFile();
            FileInputStream fileInputStream =
                    context.openFileInput(ResourceHolder.RuleProfileFileName);
            inputStreamReader = new InputStreamReader(fileInputStream
                    , StandardCharsets.UTF_8);
            return inputStreamReader;
        }
    }

    public List<FilterData> ReadFilterData() throws IOException
    {
        Gson gson = new Gson();
        Reader reader = getReader();
        List<FilterData> filterDataList = gson.fromJson(reader, List.class);
        reader.close();
        return filterDataList;
    }

    public FilterData ReadFilterData(Program program) throws IOException
    {
        List<FilterData> filterDataList = ReadFilterData();
        for (FilterData data : filterDataList)
        {
            Program thisProgram = data.getProgram();
            if (thisProgram.equals(program))
            {
                return data;
            }
        }
        return null;
    }
}
