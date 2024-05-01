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

public class GlobalProfileWriter extends DataWriter<FilterData>
{
    private static volatile GlobalProfileWriter mInstance = null;

    private GlobalProfileWriter() {}

    public static GlobalProfileWriter getInstance()
    {
        if (mInstance == null)
        {
            synchronized (GlobalProfileWriter.class)
            {
                if (mInstance == null)
                {
                    mInstance = new GlobalProfileWriter();
                }
            }
        }
        return mInstance;
    }

    @Override
    public Writer getWriter()
    {
        try
        {
            Context context = ResourceHolder.getContext();
            FileOutputStream fileOutputStream = context.openFileOutput
                    (ResourceHolder.GlobalProfileFileName, Context.MODE_PRIVATE);
            return new OutputStreamWriter(fileOutputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(FilterData data) throws IOException
    {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        Writer writer = this.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
