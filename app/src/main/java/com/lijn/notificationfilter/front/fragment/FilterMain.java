package com.lijn.notificationfilter.front.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.lijn.notificationfilter.R;
import org.jetbrains.annotations.NotNull;

import android.widget.CheckBox;

import android.widget.RadioButton;

import android.widget.RadioGroup;

import com.lijn.notificationfilter.back.manager.programsettingservice.FilterType;

import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSetting;

import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;

/**
 * A simple {@link Fragment} subclass.

 * Use the {@link FilterMain#newInstance} factory method to

 * create an instance of this fragment.
 */
     public class FilterMain extends Fragment
     {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterMain()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterMain.
     */
      // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {

    super.onViewCreated(view, savedInstanceState);

 

 ​        // 获取程序配置管理器实例

 ​        ProgramSettingManager programSettingManager = ProgramSettingManager.getInstance();

 

 ​        // 获取当前的程序设置

 ​        ProgramSetting programSetting = programSettingManager.getProgramSetting();

 

 ​        // 显示日志配置状态

 ​        CheckBox logCheckBox = view.findViewById(R.id.log_checkbox);

 ​        logCheckBox.setChecked(programSetting.isLogEnabled());

 

 ​        // 显示过滤配置状态

 ​        RadioGroup filterRadioGroup = view.findViewById(R.id.filter_radio_group);

 ​        switch (programSetting.getFilterVariety()) {

 ​            case RULE:

 ​                filterRadioGroup.check(R.id.filter_rule_radio);

 ​                break;

 ​            case CATEGORY:

 ​                filterRadioGroup.check(R.id.filter_category_radio);

 ​                break;

 ​            // 添加其他过滤类型的处理逻辑

 ​        }

 ​     // 显示开机自启动配置状态

 ​        CheckBox autoStartCheckBox = view.findViewById(R.id.auto_start_checkbox);

 ​        autoStartCheckBox.setChecked(programSetting.isAutoStartEnabled());

 

 ​        // 显示服务开关配置状态

 ​        CheckBox serviceCheckBox = view.findViewById(R.id.service_checkbox);

 ​        serviceCheckBox.setChecked(programSetting.isServiceEnabled());

 

 ​        // 设置过滤栏目中要进行规则过滤

 ​        filterRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

 ​            if (checkedId == R.id.filter_rule_radio) {

 ​                programSetting.setFilterVariety(FilterType.RULE);

 ​            } else if (checkedId == R.id.filter_category_radio) {

 ​                programSetting.setFilterVariety(FilterType.CATEGORY);

 ​            }

 ​            // 添加其他过滤类型的处理逻辑

 ​        });

 }
 }