package com.lijn.notificationfilter.back.manager.programsettingservice;

import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;

public sealed interface IProgramSettingManager permits ProgramSettingManager
{
    ProgramSetting getProgramSetting();
}
