package com.lijn.notificationfilter.front.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.programsetting.FilterType;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;

import org.jetbrains.annotations.NotNull;

public class FilterMain extends Fragment
{

   private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

   private String mParam1;
private String mParam2;

public FilterMain()
{
      }

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
public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    ProgramSettingManager.getInstance().getProgramSetting().setFilterVariety(FilterType.RULE);
}
}