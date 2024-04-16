package com.lijn.notificationfilter.back.manager;

import android.app.Notification;
import com.lijn.notificationfilter.back.entity.cache.LRUCache;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.io.ProfileReader;
import com.lijn.notificationfilter.back.io.ProfileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InServiceManager
{
    private static volatile InServiceManager mInstance = null;
    private Map<Program, InServiceType> packageInService;
    private LRUCache cache;

    private InServiceManager()
    {
        packageInService = new HashMap<>();
        cache = new LRUCache();
    }

    public static InServiceManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (InServiceManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new InServiceManager();
                }
            }
        }
        return mInstance;
    }

    private InServiceType isInService(Program program)
    {
        InServiceType inServiceType = packageInService.get(program);
        if (inServiceType == null)
        {
            FilterData data = ProfileManager.getInstance().readProfile(program);
            if (data != null)
            {
                packageInService.put(program, data.getEnabledType());
                return data.getEnabledType();
            }
            packageInService.put(program, InServiceType.NOT_USE);
            return InServiceType.NOT_USE;
        }
        return inServiceType;
    }

    public boolean doFilter(Program program, Notification notification)
    {

        InServiceType type = isInService(program);
        if (type == null || type == InServiceType.NOT_USE)
        {
            return true;
        }
        FilterData data = cache.getFilterDataFromCache(program);
        if (data == null)
        {
            try
            {
                data = ProfileReader.getInstance()
                        .ReadFilterData(program);
                cache.setFilterDataIntoCache(data);
            }
            catch (IOException ioException)
            {
                throw new RuntimeException("fail to read profile data");
            }
        }

        List<String> text = new ArrayList<>();
        String txt = notification.extras.getString(Notification.EXTRA_TEXT);
        String bigText = notification.extras.getString(Notification.EXTRA_BIG_TEXT);
        String title = notification.extras.getString(Notification.EXTRA_TITLE);
        text.add(txt);
        text.add(bigText);
        text.add(title);

        if (type == InServiceType.USE_BLACKLIST)
        {
            boolean ban = false;
            for (String temp : text)
            {
                if (isInList(data.getBlackList(), temp))
                {
                    ban = true;
                    break;
                }
            }
            return !ban;
        }
        else
        {
            boolean pass = false;
            for (String temp : text)
            {
                if (isInList(data.getWhiteList(), temp))
                {
                    pass = true;
                    break;
                }
            }
            return pass;
        }
    }

    private boolean isInList(List<String> list, String text)
    {
        for (String str : list)
        {
            if (str.matches(text))
            {
                return true;
            }
        }
        return false;
    }
}
