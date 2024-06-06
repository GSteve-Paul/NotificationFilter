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
        return new ProgramSetting(programSetting);
    }

    @Override
    public void setProgramSetting(ProgramSetting programSetting)
    {
        this.programSetting = programSetting;
    }

    @Override
    public void flushProgramSetting()
    {
        try
        {
            ProgramSettingWriter.getInstance().write(programSetting);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
