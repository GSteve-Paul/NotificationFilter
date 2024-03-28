package com.lijn.notificationfilter.back.fileoperator;

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
    private volatile static ProfileReader mInstance;
    private ProfileReader(){}

    public static ProfileReader getInstance()
    {
        if(mInstance == null)
        {
            synchronized (ProfileReader.class)
            {
                if(mInstance == null)
                {
                    mInstance = new ProfileReader();
                }
            }
        }
        return mInstance;
    }

    private Reader readProfile() throws IOException
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
            if(!file.exists())
            {
                List<FilterData> filterDataList = new ArrayList<>();
                if(file.createNewFile())
                {
                    ProfileWriter.getInstance().writeAllFilterData(filterDataList);
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

    public List<FilterData> ReadFilterDataForAllProgram() throws IOException
    {
        Gson gson = new Gson();
        Reader reader = readProfile();
        List<FilterData> filterDataList = gson.fromJson(reader, List.class);
        reader.close();
        return filterDataList;
    }

    public FilterData ReadFilterDataForOneProgram(Program program) throws IOException
    {
        List<FilterData> filterDataList = ReadFilterDataForAllProgram();
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
