package com.lijn.notificationfilter.front.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.programsetting.FilterType;
import com.lijn.notificationfilter.back.entity.programsetting.NotificationType;
import com.lijn.notificationfilter.back.entity.programsetting.ProgramSetting;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;
import org.jetbrains.annotations.NotNull;

public class FilterMain extends Fragment
{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Switch logInterceptedNotificationSwitch;
    private Switch logPassedNotificationSwitch;
    private Switch logUnknownPassedNotificationSwitch;
    private Switch filterRulesSwitch;
    private Switch filterGlobalSwitch;
    private Switch switchSwitch;
    private Switch startAutomaticSwitch;

    public FilterMain() {}

    public static FilterMain newInstance(String param1, String param2)
    {
        FilterMain fragment = new FilterMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_filter_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // 初始化视图控件
        logInterceptedNotificationSwitch = view.findViewById(R.id.logInterceptedNotificationSwitch);
        logPassedNotificationSwitch = view.findViewById(R.id.logPassedNotificationSwitch);
        logUnknownPassedNotificationSwitch = view.findViewById(R.id.logUnknownPassedNotificationSwitch);
        filterRulesSwitch = view.findViewById(R.id.filterRulesSwitch);
        filterGlobalSwitch = view.findViewById(R.id.filterGlobalSwitch);
        switchSwitch = view.findViewById(R.id.switchSwitch);
        startAutomaticSwitch = view.findViewById(R.id.StartAutomaticSwitch);

        // 设置监听器
        setSwitchListeners();
        // 从设置管理器中加载现有设置
        loadSettings();

    }

    private void setSwitchListeners()
    {
        // 为每个Switch设置监听器，以便在状态改变时更新设置
        logInterceptedNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setLogNotificationVariety(NotificationType.INTERCEPTED, isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
            // 更新设置
        });

        logPassedNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setLogNotificationVariety(NotificationType.PASSED, isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
            // 更新设置
        });

        logUnknownPassedNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setLogNotificationVariety(NotificationType.UNCHECKED, isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
            // 更新设置
        });

        filterRulesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setFilterVariety(FilterType.RULE, isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
        });

        filterGlobalSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setFilterVariety(FilterType.GLOBAL, isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
            // 更新设置
        });

        switchSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setRunning(isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
            // 更新设置
        });

        startAutomaticSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ProgramSettingManager.getInstance().getProgramSetting().setAutoStartWhenBoot(isChecked);
            ProgramSettingManager.getInstance().flushProgramSetting();
            // 更新设置
        });
    }

    private void loadSettings()
    {
        // 从ProgramSettingManager加载设置并应用到界面上的Switch控件
        ProgramSetting setting = ProgramSettingManager.getInstance().getProgramSetting();
        logInterceptedNotificationSwitch.setChecked(setting.getLogNotificationVariety(NotificationType.INTERCEPTED));
        logPassedNotificationSwitch.setChecked(setting.getLogNotificationVariety(NotificationType.PASSED));
        logUnknownPassedNotificationSwitch.setChecked(setting.getLogNotificationVariety(NotificationType.UNCHECKED));
        filterRulesSwitch.setChecked(setting.getFilterVariety(FilterType.RULE));
        filterGlobalSwitch.setChecked(setting.getFilterVariety(FilterType.GLOBAL));
        switchSwitch.setChecked(setting.getRunning());
        startAutomaticSwitch.setChecked(setting.getAutoStartWhenBoot());
    }
}