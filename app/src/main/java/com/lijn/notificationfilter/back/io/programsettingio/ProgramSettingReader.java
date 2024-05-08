package com.lijn.notificationfilter.back.io.programsettingio;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.io.DataReader;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProgramSettingReader extends DataReader<ProgramSetting>
{
    private static final String TAG = "ProgramSettingReader";
    private volatile static ProgramSettingReader mInstance = null;

    private ProgramSettingReader()
    {
        Log.d(TAG, "ProgramSettingReader: Create");
        Context context = ResourceHolder.getContext();
        File file = context.getFilesDir();

        Path path = Paths.get(file.getAbsolutePath());
        path = path.resolve(ResourceHolder.ProgramProfileFileName);

        file = path.toFile();
        Log.i(TAG, "ProgramSettingReader: file path" + file.getAbsolutePath());
        try
        {
            ProgramSetting setting = new ProgramSetting();
            Log.i(TAG, "ProgramSettingReader: exist:" + file.exists());
            if (!file.exists())
            {
                Log.d(TAG, "ProgramSettingReader: ProgramSetting does not exist");
                boolean res = file.createNewFile();
                Log.d(TAG, "ProgramSettingReader: create file " + res);
                ProgramSettingWriter.getInstance().write(setting);
                Log.d(TAG, "ProgramSettingReader: write default settings");
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, "ProgramSettingReader: Error creating program setting", e);
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
