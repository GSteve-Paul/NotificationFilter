package com.lijn.notificationfilter.back.manager.profileservice;

import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;

import java.util.List;

public sealed interface IRuleProfileManager permits RuleProfileManager
{
    List<FilterData> read();

    FilterData read(Program program);

    void save(List<FilterData> filterDataList);
}
