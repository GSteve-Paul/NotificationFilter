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
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.MyLog;
import com.lijn.notificationfilter.back.manager.logservice.LogManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterHistory#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FilterHistory extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ConcurrentLinkedDeque<MyLog> logCache;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterHistory() {
        // Required empty public constructor
    }

    public static FilterHistory newInstance(String param1, String param2) {
        FilterHistory fragment = new FilterHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_history, container, false);
    }


    // 对FiterHistory进行美化，不过需要运行起来才能看到效果
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.recyclerView);

        // 创建适配器并将数据绑定到ListView
        adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item);
        listView.setAdapter(adapter);

        // 获取日志缓存
        logCache = LogManager.getInstance().getLogCache();

        // 将日志缓存中的数据添加到适配器
        for (MyLog log : logCache) {
            adapter.add(log.toString());
        }

        // 定时任务，每隔一段时间更新适配器的数据
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        for (MyLog log : logCache) {
                            adapter.add(log.toString());
                        }
                    }
                });
            }
        }, 0, 1000); // 每隔1秒更新一次数据
    }
}