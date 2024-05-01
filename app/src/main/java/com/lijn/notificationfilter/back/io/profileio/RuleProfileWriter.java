package com.lijn.notificationfilter.back.io.profileio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.DataWriter;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RuleProfileWriter extends DataWriter<List<FilterData>>
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

    @Override
    protected OutputStreamWriter getWriter()
    {
        try
        {
            Context context = ResourceHolder.getContext();
            FileOutputStream fileOutputStream = context.openFileOutput
                    (ResourceHolder.RuleProfileFileName, Context.MODE_PRIVATE);
            return new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(List<FilterData> filterDataList) throws IOException
    {
        Gson gson = new Gson();
        Writer writer = getWriter();
        String jsonString = gson.toJson(filterDataList);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
