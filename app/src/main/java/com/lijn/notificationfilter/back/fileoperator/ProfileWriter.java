package com.lijn.notificationfilter.back.fileoperator;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProfileWriter
{
    private volatile static ProfileWriter mInstance;

    private ProfileWriter() {}

    public static ProfileWriter getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ProfileWriter.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ProfileWriter();
                }
            }
        }
        return mInstance;
    }

    private OutputStreamWriter writeProfile() throws FileNotFoundException
    {
        Context context = ResourceHolder.getContext();
        FileOutputStream fileOutputStream =
                context.openFileOutput(
                        "profile.json", Context.MODE_PRIVATE);
        return new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
    }

    public void writeAllFilterData(List<FilterData> filterDataList) throws IOException
    {
        Gson gson = new Gson();
        Writer writer = writeProfile();
        String jsonString = gson.toJson(filterDataList);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
