package com.lijn.notificationfilter.back.manager;

import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.io.ProfileReader;
import com.lijn.notificationfilter.back.io.ProfileWriter;

import java.io.IOException;
import java.util.List;

public class ProfileManager
{
    private volatile static ProfileManager mInstance;

    private ProfileManager(){}

    public static ProfileManager getInstance()
    {
        if(mInstance == null)
        {
            synchronized (ProfileManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ProfileManager();
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
            filterDataList = ProfileReader.getInstance()
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
            ProfileWriter.getInstance().writeFilterData(filterDataList);
            InServiceManager.getInstance();
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to save profile!");
        }
    }
}
