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

public class ProfileReader
{
    private volatile static ProfileReader mInstance = null;

    private ProfileReader() {}

    public static ProfileReader getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ProfileReader.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ProfileReader();
                }
            }
        }
        return mInstance;
    }

    private Reader getReader() throws IOException
    {
        Context context = ResourceHolder.getContext();
        InputStreamReader inputStreamReader = null;
        try
        {
            FileInputStream fileInputStream =
                    context.openFileInput("profile.json");
            inputStreamReader = new InputStreamReader(fileInputStream
                    , StandardCharsets.UTF_8);
            return inputStreamReader;
        }
        catch (IOException ioException)
        {
            File file = context.getFileStreamPath("profile.json");
            if (!file.exists())
            {
                List<FilterData> filterDataList = new ArrayList<>();
                if (file.createNewFile())
                {
                    ProfileWriter.getInstance().writeFilterData(filterDataList);
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
