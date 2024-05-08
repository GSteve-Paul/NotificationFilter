package com.lijn.notificationfilter.back.manager.programsettingservice;

import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;

import java.io.IOException;

public sealed interface IProgramSettingManager permits ProgramSettingManager
{
    ProgramSetting getProgramSetting();

    void flushProgramSetting() throws IOException;
}
