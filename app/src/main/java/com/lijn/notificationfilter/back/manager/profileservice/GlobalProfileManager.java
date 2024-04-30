package com.lijn.notificationfilter.back.manager.profileservice;

import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.profileio.GlobalProfileReader;
import com.lijn.notificationfilter.back.io.profileio.GlobalProfileWriter;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;

import java.io.IOException;

public final class GlobalProfileManager implements IGlobalProfileManager
{
    private volatile static GlobalProfileManager mInstance;

    private GlobalProfileManager() {}

    public static GlobalProfileManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (GlobalProfileManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new GlobalProfileManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public FilterData read()
    {
        FilterData filterData = null;
        try
        {
            filterData = GlobalProfileReader.getInstance().read();
        }
        catch (IOException e)
        {
            throw new RuntimeException("fail to load profile!");
        }
        return filterData;
    }

    @Override
    public void save(FilterData filterData)
    {
        try
        {
            GlobalProfileWriter.getInstance().write(filterData);
            InServiceManager.getInstance().clearGlobalCache();
        }
        catch (IOException e)
        {
            throw new RuntimeException("fail to save profile!");
        }
    }
}
