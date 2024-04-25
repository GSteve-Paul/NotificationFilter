package com.lijn.notificationfilter.back.manager;

import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.io.RuleProfileReader;
import com.lijn.notificationfilter.back.io.RuleProfileWriter;

import java.io.IOException;
import java.util.List;

public class RuleProfileManager
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

    public List<FilterData> readProfile()
    {
        List<FilterData> filterDataList = null;
        try
        {
            filterDataList = RuleProfileReader.getInstance()
                    .ReadFilterData();
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to load profile!");
        }
        return filterDataList;
    }

    public FilterData readProfile(Program program)
    {
        List<FilterData> filterDataList = readProfile();
        for (FilterData filterData : filterDataList)
        {
            if (filterData.getProgram().equals(program))
            {
                return filterData;
            }
        }
        return null;
    }

    public void saveProfile(List<FilterData> filterDataList)
    {
        try
        {
            RuleProfileWriter.getInstance().writeFilterData(filterDataList);
            InServiceManager.getInstance();
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to save profile!");
        }
    }
}
