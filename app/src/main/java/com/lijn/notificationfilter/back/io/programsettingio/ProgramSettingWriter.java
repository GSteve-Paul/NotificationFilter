package com.lijn.notificationfilter.back.io.programsettingio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.io.DataWriter;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ProgramSettingWriter extends DataWriter<ProgramSetting>
{
    private static volatile ProgramSettingWriter mInstance = null;

    private ProgramSettingWriter() {}

    public static ProgramSettingWriter getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ProgramSettingWriter.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ProgramSettingWriter();
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
                    (ResourceHolder.ProgramProfileFileName, Context.MODE_PRIVATE);
            return new OutputStreamWriter(fileOutputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(ProgramSetting programSetting) throws IOException
    {
        OutputStreamWriter osw = getWriter();
        Gson gson = new Gson();
        String json = gson.toJson(programSetting);
        osw.write(json);
        osw.flush();
        osw.close();
    }
}
