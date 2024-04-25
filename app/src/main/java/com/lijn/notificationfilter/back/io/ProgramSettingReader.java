package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ProgramSettingReader
{
    private volatile static ProgramSettingReader mInstance = null;

    private ProgramSettingReader() {}

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

    private void initializeProgramInfoFile() throws IOException
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFileStreamPath("program_info.json");
        if (!file.exists())
        {
            ProgramSetting programSetting = new ProgramSetting();
            if (file.createNewFile())
            {
                ProgramSettingWriter.getInstance().write(programSetting);
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
                    context.openFileInput("program_info.json");
            inputStreamReader = new InputStreamReader(fileInputStream);
            return inputStreamReader;
        }
        catch (FileNotFoundException e)
        {
            this.initializeProgramInfoFile();
            FileInputStream fileInputStream =
                    context.openFileInput("profile.json");
            inputStreamReader = new InputStreamReader(fileInputStream
                    , StandardCharsets.UTF_8);
            return inputStreamReader;
        }
    }

    public ProgramSetting read() throws IOException
    {
        Reader reader = this.getReader();
        Gson gson = new Gson();
        return gson.fromJson(reader, ProgramSetting.class);
    }
}
