package com.lijn.notificationfilter.back.entity;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class FilterData
{
    Program program;
    Boolean needDisplay;
    InServiceType enabledType;
    List<String> whiteList;
    List<String> blackList;

    public FilterData()
    {
        needDisplay = false;
        whiteList = new ArrayList<>();
        blackList = new ArrayList<>();
        enabledType = InServiceType.NOT_USE;
        program = new Program();
    }

    public FilterData(Program program, Boolean needDisplay, InServiceType enabledType, List<String> whiteList, List<String> blackList)
    {
        this.program = program;
        this.needDisplay = needDisplay;
        this.enabledType = enabledType;
        this.whiteList = whiteList;
        this.blackList = blackList;
    }

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    public InServiceType getEnabledType()
    {
        return enabledType;
    }

    public void setEnabledType(InServiceType enabledType)
    {
        this.enabledType = enabledType;
    }

    public List<String> getWhiteList()
    {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList)
    {
        this.whiteList = whiteList;
    }

    public List<String> getBlackList()
    {
        return blackList;
    }

    public void setBlackList(List<String> blackList)
    {
        this.blackList = blackList;
    }

    public Boolean getNeedDisplay()
    {
        return needDisplay;
    }

    public void setNeedDisplay(Boolean needDisplay)
    {
        this.needDisplay = needDisplay;
    }

    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof FilterData other)) return false;
        return program.equals(other.program)
                && enabledType.equals(other.enabledType)
                && needDisplay.equals(other.needDisplay)
                && whiteList.equals(other.whiteList)
                && blackList.equals(other.blackList);
    }
}
