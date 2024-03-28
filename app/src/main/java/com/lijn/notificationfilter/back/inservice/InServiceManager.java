package com.lijn.notificationfilter.back.inservice;

import com.lijn.notificationfilter.back.cache.LRUCache;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.fileoperator.ProfileReader;
import com.lijn.notificationfilter.back.fileoperator.ProfileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InServiceManager
{
    private static volatile InServiceManager mInstance;
    Map<Program, InServiceType> packageInService;
    LRUCache cache;
    private InServiceManager()
    {
        try
        {
            packageInService = new HashMap<>();
            cache = new LRUCache();
            //read from the json file
            List<FilterData> filterDataList = ProfileReader.getInstance().
                    ReadFilterDataForAllProgram();
            for (FilterData temp : filterDataList)
            {
                packageInService.put(temp.getProgram(), temp.getEnabledType());
            }
        }
        catch (IOException ioException)
        {
            throw new Error("critical error! CANNOT load InServiceManager!");
        }
    }

    public static InServiceManager getInstance()
    {
        if(mInstance == null )
        {
            synchronized (InServiceManager.class)
            {
                if(mInstance == null)
                {
                    mInstance = new InServiceManager();
                }
            }
        }
        return mInstance;
    }
    public static InServiceManager getInstance(boolean remake)
    {
        if(mInstance == null || remake)
        {
            synchronized (InServiceManager.class)
            {
                if(mInstance == null || remake)
                {
                    mInstance = new InServiceManager();
                }
            }
        }
        return mInstance;
    }

    public InServiceType isInService(Program program)
    {
        return packageInService.get(program);
    }

    public boolean doFilter(Program program, String ...text)
    {
        InServiceType type = isInService(program);
        if(type == null || type == InServiceType.NOT_USE)
        {
            return true;
        }
        FilterData data = cache.getFilterDataFromCache(program);
        if (data == null)
        {
            try
            {
                data = ProfileReader.getInstance()
                        .ReadFilterDataForOneProgram(program);
                cache.setFilterDataIntoCache(data);
            }
            catch (IOException ioException)
            {
                throw new RuntimeException("fail to read profile data");
            }
        }
        if(type == InServiceType.USE_BLACKLIST)
        {
            boolean ban = false;
            for(String temp:text)
            {
                if(isInList(data.getBlackList(),temp))
                {
                    ban = true;
                }
            }
            return !ban;
        }
        else
        {
            boolean pass = false;
            for(String temp:text)
            {
                if(isInList(data.getWhiteList(),temp))
                {
                    pass = true;
                }
            }
            return pass;
        }
    }

    private boolean isInList(List<String> list, String text)
    {
        for(String str:list)
        {
            if(str.matches(text))
            {
                return true;
            }
        }
        return false;
    }

    public List<FilterData> readProfile()
    {
        List<FilterData> filterDataList = null;
        try
        {
            filterDataList = ProfileReader.getInstance()
                    .ReadFilterDataForAllProgram();
            return filterDataList;
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to load profile!");
        }
    }

    public FilterData readOneProfile(Program program)
    {
        List<FilterData> filterDataList = readProfile();
        for(FilterData filterData : filterDataList)
        {
            if(filterData.getProgram().equals(program))
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
            ProfileWriter.getInstance().writeAllFilterData(filterDataList);
        }
        catch (IOException ioException)
        {
            throw new RuntimeException("fail to save profile!");
        }
    }
}
