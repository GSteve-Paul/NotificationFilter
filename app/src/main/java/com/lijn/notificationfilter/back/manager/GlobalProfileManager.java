package com.lijn.notificationfilter.back.manager;

import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.GlobalProfileReader;
import com.lijn.notificationfilter.back.io.GlobalProfileWriter;

import java.io.IOException;

public class GlobalProfileManager
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
