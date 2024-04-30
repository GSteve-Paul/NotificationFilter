package com.lijn.notificationfilter.back.manager.profileservice;

import com.lijn.notificationfilter.back.entity.FilterData;

public sealed interface IGlobalProfileManager permits GlobalProfileManager
{
    FilterData read();

    void save(FilterData filterData);
}
