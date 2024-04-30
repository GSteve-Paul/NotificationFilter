package com.lijn.notificationfilter.back.manager.profileservice;

import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.io.profileio.RuleProfileReader;
import com.lijn.notificationfilter.back.io.profileio.RuleProfileWriter;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;

import java.io.IOException;
import java.util.List;

public final class RuleProfileManager implements IRuleProfileManager
{
    private volatile static RuleProfileManager mInstance;

    private RuleProfileManager(){}

    public static RuleProfileManager getInstance()
    {
        if(mInstance == null)
        {
            synchronized (RuleProfileManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RuleProfileManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public List<FilterData> read()
    {
        List<FilterData> filterDataList = null;
        try
        {
            filterDataList = RuleProfileReader.getInstance().read();
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to load profile!");
        }
        return filterDataList;
    }

    @Override
    public FilterData read(Program program)
    {
        List<FilterData> filterDataList = read();
        for (FilterData filterData : filterDataList)
        {
            if (filterData.getProgram().equals(program))
            {
                return filterData;
            }
        }
        FilterData filterData = new FilterData();
        filterData.setEnabledType(InServiceType.NOT_USE);
        return filterData;
    }

    @Override
    public void save(List<FilterData> filterDataList)
    {
        try
        {
            RuleProfileWriter.getInstance().write(filterDataList);
            InServiceManager.getInstance().clearRuleCache();
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to save profile!");
        }
    }
}
