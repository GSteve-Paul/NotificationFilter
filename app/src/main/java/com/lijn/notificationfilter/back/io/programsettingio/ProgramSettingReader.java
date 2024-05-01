package com.lijn.notificationfilter.back.io.programsettingio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.io.DataReader;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProgramSettingReader extends DataReader<ProgramSetting>
{
    private volatile static ProgramSettingReader mInstance = null;

    private ProgramSettingReader()
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFilesDir();

        Path path = Paths.get(file.getAbsolutePath());
        path.resolve(ResourceHolder.ProgramProfileFileName);

        file = path.toFile();
        try
        {
            ProgramSetting setting = new ProgramSetting();
            if (!file.exists())
            {
                file.createNewFile();
                ProgramSettingWriter.getInstance().write(setting);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ProgramSettingReader getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ProgramSettingReader.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ProgramSettingReader();
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
                    context.openFileInput(ResourceHolder.ProgramProfileFileName);
            inputStreamReader = new InputStreamReader(fileInputStream);
            return inputStreamReader;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProgramSetting read() throws IOException
    {
        Reader reader = this.getReader();
        Gson gson = new Gson();
        ProgramSetting programSetting = gson.fromJson(reader, ProgramSetting.class);
        reader.close();
        return programSetting;
    }
}
