package com.lijn.notificationfilter.back.manager;

import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.io.ProgramSettingReader;
import com.lijn.notificationfilter.back.io.ProgramSettingWriter;

import java.io.IOException;

public class ProgramSettingManager
{
    private static volatile ProgramSettingManager mInstance;
    private ProgramSetting programSetting;

    private ProgramSettingManager()
    {
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
        if(mInstance == null)
        {
            synchronized (ProgramSettingManager.class)
            {
                if(mInstance == null)
                {
                    mInstance = new ProgramSettingManager();
                }
            }
        }
        return mInstance;
    }

    public ProgramSetting getProgramSetting()
    {
        return programSetting;
    }

    @Override
    protected void finalize() throws Throwable
    {
        ProgramSettingWriter.getInstance().write(programSetting);
    }
}
