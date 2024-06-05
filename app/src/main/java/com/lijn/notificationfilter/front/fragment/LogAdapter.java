package com.lijn.notificationfilter.front.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.MyLog;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder>
{
    private List<MyLog> dataList;

    public LogAdapter() {}

    public LogAdapter(List<MyLog> dataList)
    {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LogViewHolder holder, int position)
    {
        MyLog log = dataList.get(position);
        holder.bind(log);
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder
    {
        TextView textTimeView;
        TextView textTypeView;
        TextView textMsgView;

        public LogViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            textTimeView = itemView.findViewById(R.id.notificationTime);
            textTypeView = itemView.findViewById(R.id.notificationType);
            textMsgView = itemView.findViewById(R.id.notificationMsg);
        }

        public void bind(MyLog log)
        {
            textTimeView.setText(log.getLogTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            textTypeView.setText(log.getNotificationType().name());
            textMsgView.setText(log.getLogText());
        }
    }
}
