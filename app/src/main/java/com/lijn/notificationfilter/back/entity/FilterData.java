package com.lijn.notificationfilter.back.entity;

import java.util.ArrayList;
import java.util.List;


public class FilterData
{
    Program program;
    InServiceType enabledType;
    List<String> whiteList;
    List<String> blackList;

    public FilterData()
    {
        whiteList = new ArrayList<>();
        blackList = new ArrayList<>();
        enabledType = InServiceType.NOT_USE;
        program = new Program();
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

    @Override
    public String toString()
    {
        return "FilterData{" +
                "program=" + program +
                ", enabledType=" + enabledType +
                ", whiteList=" + whiteList +
                ", blackList=" + blackList +
                '}';
    }
}
