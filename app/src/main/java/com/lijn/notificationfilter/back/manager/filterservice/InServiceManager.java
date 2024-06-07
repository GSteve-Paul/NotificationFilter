package com.lijn.notificationfilter.back.manager.filterservice;

import android.app.Notification;
import android.util.Log;
import android.util.Pair;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.cache.LRUCache;
import com.lijn.notificationfilter.back.entity.programsetting.FilterType;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.manager.displayservice.DisplayManager;
import com.lijn.notificationfilter.back.manager.logservice.LogManager;
import com.lijn.notificationfilter.back.manager.profileservice.GlobalProfileManager;
import com.lijn.notificationfilter.back.manager.profileservice.RuleProfileManager;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InServiceManager
{
    private static final String TAG = "InServiceManager";
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
        if (text == null) return false;
        for (String str : list)
        {
            if (Pattern.matches(str, text))
            {
                return true;
            }
        }
        return false;
    }

    private NotificationType check(Notification notification, FilterData data)
    {
        InServiceType type = data.getEnabledType();
        if (type == InServiceType.NOT_USE)
        {
            return NotificationType.UNCHECKED;
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
                    return NotificationType.INTERCEPTED;
                }
            }
            return NotificationType.PASSED;
        }

        if (type == InServiceType.USE_WHITELIST)
        {
            for (String s : text)
            {
                if (isInList(data.getWhiteList(), s))
                {
                    return NotificationType.PASSED;
                }
            }
            return NotificationType.INTERCEPTED;
        }
        return NotificationType.PASSED;
    }

    private Pair<NotificationType, FilterData> doGlobalFilter(Notification notification)
    {
        FilterData data = globalFilterData;
        if (data == null)
        {
            globalFilterData = GlobalProfileManager.getInstance().read();
            data = globalFilterData;
        }
        return new Pair<>(check(notification, data), data);
    }

    private Pair<NotificationType, FilterData> doRuleFilter(Program program, Notification notification)
    {
        FilterData data = cache.getFilterDataFromCache(program);
        if (data == null)
        {
            data = RuleProfileManager.getInstance().read(program);
            cache.setFilterDataIntoCache(data);
        }
        return new Pair<>(check(notification, data), data);
    }

    private Pair<NotificationType, FilterData> doFilter(Program program, Notification notification)
    {
        //if filter module is not enabled
        Log.i(TAG, "doFilter_: ");
        if (!ProgramSettingManager.getInstance().getProgramSetting()
                .getRunning())
        {
            return new Pair<>(NotificationType.UNCHECKED, null);
        }
        //if both global and rule filter is enabled
        if (ProgramSettingManager.getInstance().getProgramSetting()
                .getFilterVariety(FilterType.GLOBAL) &&
                ProgramSettingManager.getInstance().getProgramSetting()
                        .getFilterVariety(FilterType.RULE))
        {
            Pair<NotificationType, FilterData> globalType = doGlobalFilter(notification);
            Pair<NotificationType, FilterData> ruleType = doRuleFilter(program, notification);

            if (globalType.first == NotificationType.INTERCEPTED ||
                    ruleType.first == NotificationType.INTERCEPTED)
            {
                return new Pair<>(NotificationType.INTERCEPTED, null);
            }

            if (globalType.first == NotificationType.PASSED)
            {
                return globalType;
            }

            if (ruleType.first == NotificationType.PASSED)
            {
                return ruleType;
            }

            return new Pair<>(NotificationType.UNCHECKED, null);
        }

        //if only global filter is enabled
        NotificationType globalResult = NotificationType.PASSED;
        if (ProgramSettingManager.getInstance().getProgramSetting()
                .getFilterVariety(FilterType.GLOBAL))
        {
            return doGlobalFilter(notification);
        }

        //if only rule filter is enabled
        NotificationType ruleResult = NotificationType.PASSED;
        if (ProgramSettingManager.getInstance().getProgramSetting()
                .getFilterVariety(FilterType.RULE))
        {
            return doRuleFilter(program, notification);
        }

        //if not any filter is enabled
        return new Pair<>(NotificationType.UNCHECKED, null);
    }

    public NotificationType doFilterProxy(Program program, Notification notification) throws IOException
    {
        Pair<NotificationType, FilterData> result = doFilter(program, notification);
        NotificationType resType = result.first;
        FilterData resData = result.second;

        if (ProgramSettingManager.getInstance().getProgramSetting()
                .getLogNotificationVariety(resType))
        {
            MyLog log = new MyLog(notification, resType);
            LogManager.getInstance().writeLog(log);
        }

        if (resData != null && resData.getNeedDisplay() && resType == NotificationType.PASSED)
        {
            MyLog log = new MyLog(notification, resType);
            DisplayManager.getInstance().doDisplay(log.getLogText());
        }

        return resType;
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
