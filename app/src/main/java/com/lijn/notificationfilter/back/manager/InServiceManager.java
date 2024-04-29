package com.lijn.notificationfilter.back.manager;

import android.app.Notification;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.cache.LRUCache;
import com.lijn.notificationfilter.back.entity.programsetting.FilterType;

import java.util.ArrayList;
import java.util.List;

public class InServiceManager
{
    private static volatile InServiceManager mInstance = null;
    private LRUCache cache;
    private FilterData globalFilterData;

    private InServiceManager()
    {
        globalFilterData = null;
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

    private static boolean isInList(List<String> list, String text)
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

    private boolean check(Notification notification, FilterData data)
    {
        InServiceType type = data.getEnabledType();
        if (type == InServiceType.NOT_USE)
        {
            return true;
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
            for (String s : text)
            {
                if (isInList(data.getBlackList(), s))
                {
                    return false;
                }
            }
            return true;
        }

        if (type == InServiceType.USE_WHITELIST)
        {
            for (String s : text)
            {
                if (isInList(data.getWhiteList(), s))
                {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean doGlobalFilter(Notification notification)
    {
        FilterData data = globalFilterData;
        if (data == null)
        {
            globalFilterData = GlobalProfileManager.getInstance().read();
            data = globalFilterData;
        }
        return check(notification, data);
    }

    private boolean doRuleFilter(Program program, Notification notification)
    {
        FilterData data = cache.getFilterDataFromCache(program);
        if (data == null)
        {
            data = RuleProfileManager.getInstance().readProfile(program);
            cache.setFilterDataIntoCache(data);
        }
        return check(notification, data);
    }

    public boolean doFilter(Program program, Notification notification)
    {
        boolean globalResult = true;
        if(ProgramSettingManager.getInstance().getProgramSetting()
                .getFilterVariety(FilterType.GLOBAL))
        {
            globalResult = doGlobalFilter(notification);
        }

        boolean ruleResult = true;
        if(ProgramSettingManager.getInstance().getProgramSetting()
                .getFilterVariety(FilterType.RULE))
        {
            ruleResult = doRuleFilter(program, notification);
        }

        return globalResult && ruleResult;
    }

    public void clearRuleCache()
    {
        cache = new LRUCache();
    }

    public void clearGlobalCache()
    {
        globalFilterData = null;
    }
}
