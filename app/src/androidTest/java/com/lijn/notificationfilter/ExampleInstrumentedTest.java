package com.lijn.notificationfilter;

import android.content.Context;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.programsetting.FilterType;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.io.profileio.GlobalProfileWriter;
import com.lijn.notificationfilter.back.manager.profileservice.GlobalProfileManager;
import com.lijn.notificationfilter.back.manager.profileservice.RuleProfileManager;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;
import com.lijn.notificationfilter.back.util.ResourceHolder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest
{
    @Test
    public void filterTest() throws NoSuchFieldException, IllegalAccessException
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    }

    @Test
    public void ProgramSettingManagerTest() throws IOException
    {
        boolean res;
        ProgramSettingManager manager = ProgramSettingManager.getInstance();

        res = manager.getProgramSetting().getAutoStartWhenBoot();
        Assert.assertFalse(res);
        manager.getProgramSetting().setAutoStartWhenBoot(true);
        res = manager.getProgramSetting().getAutoStartWhenBoot();
        Assert.assertTrue(res);

        res = manager.getProgramSetting().getRunning();
        Assert.assertFalse(res);
        manager.getProgramSetting().setRunning(false);


        //check init
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.PASSED);
        Assert.assertFalse(res);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.INTERCEPTED);
        Assert.assertFalse(res);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.UNCHECKED);
        Assert.assertFalse(res);

        res = manager.getProgramSetting().getFilterVariety(FilterType.GLOBAL);
        Assert.assertFalse(res);
        res = manager.getProgramSetting().getFilterVariety(FilterType.RULE);
        Assert.assertFalse(res);


        //check FilterVariety
        manager.getProgramSetting().setFilterVariety(FilterType.GLOBAL, true);
        res = manager.getProgramSetting().getFilterVariety(FilterType.GLOBAL);
        Assert.assertTrue(res);

        manager.getProgramSetting().setFilterVariety(FilterType.GLOBAL, false);
        res = manager.getProgramSetting().getFilterVariety(FilterType.GLOBAL);
        Assert.assertFalse(res);

        manager.getProgramSetting().setFilterVariety(FilterType.RULE, true);
        res = manager.getProgramSetting().getFilterVariety(FilterType.RULE);
        Assert.assertTrue(res);

        manager.getProgramSetting().setFilterVariety(FilterType.RULE, false);
        res = manager.getProgramSetting().getFilterVariety(FilterType.RULE);
        Assert.assertFalse(res);

        manager.getProgramSetting().setFilterVariety(FilterType.GLOBAL, true);
        manager.getProgramSetting().setFilterVariety(FilterType.RULE, true);
        res = manager.getProgramSetting().getFilterVariety(FilterType.GLOBAL);
        Assert.assertTrue(res);
        res = manager.getProgramSetting().getFilterVariety(FilterType.RULE);
        Assert.assertTrue(res);

        //check LogNotificationVariety
        manager.getProgramSetting().setLogNotificationVariety(NotificationType.PASSED, true);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.PASSED);
        Assert.assertTrue(res);
        manager.getProgramSetting().setLogNotificationVariety(NotificationType.PASSED, false);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.PASSED);
        Assert.assertFalse(res);

        manager.getProgramSetting().setLogNotificationVariety(NotificationType.INTERCEPTED, true);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.INTERCEPTED);
        Assert.assertTrue(res);
        manager.getProgramSetting().setLogNotificationVariety(NotificationType.INTERCEPTED, false);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.INTERCEPTED);
        Assert.assertFalse(res);

        manager.getProgramSetting().setLogNotificationVariety(NotificationType.UNCHECKED, true);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.UNCHECKED);
        Assert.assertTrue(res);
        manager.getProgramSetting().setLogNotificationVariety(NotificationType.UNCHECKED, false);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.UNCHECKED);
        Assert.assertFalse(res);

        manager.getProgramSetting().setLogNotificationVariety(NotificationType.PASSED, true);
        manager.getProgramSetting().setLogNotificationVariety(NotificationType.INTERCEPTED, true);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.PASSED);
        Assert.assertTrue(res);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.INTERCEPTED);
        Assert.assertTrue(res);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.UNCHECKED);
        Assert.assertFalse(res);

        manager.getProgramSetting().setLogNotificationVariety(NotificationType.UNCHECKED, true);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.UNCHECKED);
        Assert.assertTrue(res);
        manager.getProgramSetting().setLogNotificationVariety(NotificationType.PASSED, false);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.PASSED);
        Assert.assertFalse(res);
        res = manager.getProgramSetting().getLogNotificationVariety(NotificationType.INTERCEPTED);
        Assert.assertTrue(res);

        //check write
        manager.flushProgramSetting();
        FileInputStream fis = ResourceHolder.getContext().openFileInput(ResourceHolder.ProgramProfileFileName);
        ProgramSetting setting = new Gson().fromJson(new InputStreamReader(fis), ProgramSetting.class);
        fis.close();
        Assert.assertEquals(setting, manager.getProgramSetting());
    }

    @Test
    public void GlobalProfileManagerTest() throws IOException
    {
        GlobalProfileManager manager = GlobalProfileManager.getInstance();

        //check init
        FilterData data = manager.read();
        System.out.println(data);
        System.out.println(data.getProgram());
        System.out.println(data.getNeedDisplay());
        System.out.println(data.getEnabledType());
        System.out.println(data.getBlackList());
        System.out.println(data.getWhiteList());
        FilterData defaultFilterData = new FilterData();
        System.out.println(defaultFilterData);
        System.out.println(data.getProgram());
        System.out.println(data.getNeedDisplay());
        System.out.println(data.getEnabledType());
        System.out.println(data.getBlackList());
        System.out.println(data.getWhiteList());
        Assert.assertEquals(defaultFilterData, data);

        //check save and read
        FilterData filterData = new FilterData();
        filterData.setEnabledType(InServiceType.USE_BLACKLIST);
        manager.save(filterData);
        FileInputStream fis = ResourceHolder.getContext().openFileInput(ResourceHolder.GlobalProfileFileName);
        InputStreamReader isr = new InputStreamReader(fis);
        FilterData fromFile = new Gson().fromJson(isr, FilterData.class);
        isr.close();
        Assert.assertEquals(filterData, fromFile);
        FilterData managerRead = manager.read();
        Assert.assertEquals(filterData, managerRead);

        List<String> myWhiteList = new ArrayList<>();
        myWhiteList.add("test");
        myWhiteList.add("test2");
        myWhiteList.add("test3");
        myWhiteList.add("test4");
        myWhiteList.add("test5");

        List<String> myBlackList = new ArrayList<>();
        myBlackList.add("q");
        myBlackList.add("q1");
        myBlackList.add("q2");
        myBlackList.add("q3");
        myBlackList.add("q4");
        myBlackList.add("q5");

        FilterData filterData2 = new FilterData();
        filterData2.setEnabledType(InServiceType.USE_BLACKLIST);
        filterData2.setBlackList(myBlackList);
        filterData2.setWhiteList(myWhiteList);
        filterData2.setNeedDisplay(false);
        filterData2.setProgram(new Program("qwerty"));
        manager.save(filterData2);

        fis = ResourceHolder.getContext().openFileInput(ResourceHolder.GlobalProfileFileName);
        isr = new InputStreamReader(fis);
        FilterData fromFile2 = new Gson().fromJson(isr, FilterData.class);
        isr.close();
        Assert.assertEquals(filterData2, fromFile2);
        FilterData managerRead2 = manager.read();
        Assert.assertEquals(filterData2, managerRead2);
    }

    @Test
    public void RuleProfileManagerTest() throws IOException
    {
        RuleProfileManager manager = RuleProfileManager.getInstance();

        //check init
        List<FilterData> filterDataList = new ArrayList<>();
        List<FilterData> managerRead = manager.read();
        Assert.assertEquals(filterDataList, managerRead);

        //check save and read
        List<FilterData> filterDataList1 = List.of(
                new FilterData(new Program("program1"),false,InServiceType.USE_WHITELIST,List.of("white1","white2","white3"),List.of("black1","black2","black3","black4")),
                new FilterData(new Program("program2"),true,InServiceType.NOT_USE,List.of("white4","white5","white6"),List.of("black4","black5","black6","black7")),
                new FilterData(new Program("program3"),false,InServiceType.USE_BLACKLIST,List.of("white7","white8","white9","white10","white11"),List.of("black8","black9","black10","black11"))
        );
        manager.save(filterDataList1);
        List<FilterData> list = manager.read();
        System.out.println(list);
        System.out.println(filterDataList1);
        Assert.assertEquals(list, filterDataList1);

        FilterData filterData1 = filterDataList1.get(0);
        FilterData filterData2 = filterDataList1.get(1);
        FilterData filterData3 = filterDataList1.get(2);
        Assert.assertEquals(filterData1, manager.read(new Program("program1")));
        Assert.assertEquals(filterData2, manager.read(new Program("program2")));
        Assert.assertEquals(filterData3, manager.read(new Program("program3")));
    }

}