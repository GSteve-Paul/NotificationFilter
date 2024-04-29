package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RuleProfileWriter
{
    private volatile static RuleProfileWriter mInstance = null;

    private RuleProfileWriter() {}

    public static RuleProfileWriter getInstance()
    {
        if (mInstance == null)
        {
            synchronized (RuleProfileWriter.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RuleProfileWriter();
                }
            }
        }
        return mInstance;
    }

    private OutputStreamWriter getWriter() throws FileNotFoundException
    {
        Context context = ResourceHolder.getContext();
        FileOutputStream fileOutputStream = context.openFileOutput
                (ResourceHolder.RuleProfileFileName, Context.MODE_PRIVATE);
        return new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
    }

    public void writeFilterData(List<FilterData> filterDataList) throws IOException
    {
        Gson gson = new Gson();
        Writer writer = getWriter();
        String jsonString = gson.toJson(filterDataList);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
