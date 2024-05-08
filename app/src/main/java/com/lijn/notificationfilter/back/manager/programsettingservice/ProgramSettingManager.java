package com.lijn.notificationfilter.back.manager.programsettingservice;

import android.util.Log;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.io.programsettingio.ProgramSettingReader;
import com.lijn.notificationfilter.back.io.programsettingio.ProgramSettingWriter;

import java.io.IOException;

public final class ProgramSettingManager implements IProgramSettingManager
{
    private static final String TAG = "ProgramSettingManager";
    private static volatile ProgramSettingManager mInstance;
    private ProgramSetting programSetting;

    private ProgramSettingManager()
    {
        Log.d(TAG, "ProgramSettingManager: Create");
        try
        {
            programSetting = ProgramSettingReader.getInstance().read();
        }
        catch (IOException e)
        {
            programSetting = new ProgramSetting();
        }
    }

    public static ProgramSettingManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ProgramSettingManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ProgramSettingManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public ProgramSetting getProgramSetting()
    {
        return programSetting;
    }

    @Override
    public void flushProgramSetting() throws IOException
    {
        ProgramSettingWriter.getInstance().write(programSetting);
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        Log.d(TAG, "finalize: flush the ProgramSetting");
        flushProgramSetting();
    }
}
