package com.lijn.notificationfilter.front.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.manager.logservice.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterHistory#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FilterHistory extends Fragment
{
    // here are useless trash
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private LogAdapter adapter;

    public FilterHistory()
    {
        // Required empty public constructor
    }

    public static FilterHistory newInstance(String param1, String param2)
    {
        FilterHistory fragment = new FilterHistory();
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
        return inflater.inflate(R.layout.fragment_filter_history, container, false);
    }


    // 对FilterHistory进行美化，不过需要运行起来才能看到效果
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.log_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<MyLog> cache = LogManager.getInstance().getLogCache();
        adapter = new LogAdapter(cache);
        recyclerView.setAdapter(adapter);
    }
}