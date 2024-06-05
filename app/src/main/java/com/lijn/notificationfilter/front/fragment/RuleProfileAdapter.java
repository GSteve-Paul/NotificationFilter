package com.lijn.notificationfilter.front.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.util.ResourceHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RuleProfileAdapter extends RecyclerView.Adapter<RuleProfileAdapter.RuleProfileViewHolder>
{
    private List<FilterData> dataList;
    private int selectedPosition = -1;

    public RuleProfileAdapter() {}

    public RuleProfileAdapter(List<FilterData> dataList)
    {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public RuleProfileViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.profile_list_item, parent, false);
        return new RuleProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RuleProfileViewHolder holder, int position)
    {
        FilterData data = dataList.get(position);
        holder.bind(data);
        if (selectedPosition == holder.getAdapterPosition())
            holder.itemView.setBackgroundColor(Color.toArgb(0x33ff66));
        else
            holder.itemView.setBackgroundColor(Color.toArgb(0x33ffff));

        holder.itemView.setOnClickListener(v -> {
            int previousSelectedPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousSelectedPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public static class RuleProfileViewHolder extends RecyclerView.ViewHolder
    {
        TextView programPackageNameText;
        TextView isDisplayText;
        TextView enabledTypeText;
        Spinner whiteListSpinner;
        Spinner blackListSpinner;

        public RuleProfileViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            programPackageNameText = itemView.findViewById(R.id.programPackageNameText);
            isDisplayText = itemView.findViewById(R.id.isDisplayText);
            enabledTypeText = itemView.findViewById(R.id.enabledTypeText);
            whiteListSpinner = itemView.findViewById(R.id.whiteListSpinner);
            blackListSpinner = itemView.findViewById(R.id.blackListSpinner);
        }

        public void bind(FilterData filterData)
        {
            programPackageNameText.setText(filterData.getProgram().getPackageName());
            isDisplayText.setText(filterData.getNeedDisplay().toString());
            enabledTypeText.setText(filterData.getEnabledType().toString());

            ArrayAdapter<String> whiteListAdapter = new ArrayAdapter<>
                    (ResourceHolder.getContext(), android.R.layout.simple_spinner_item, filterData.getWhiteList());
            whiteListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            whiteListSpinner.setAdapter(whiteListAdapter);

            ArrayAdapter<String> blackListAdapter = new ArrayAdapter<>
                    (ResourceHolder.getContext(), android.R.layout.simple_spinner_item, filterData.getBlackList());
            blackListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            blackListSpinner.setAdapter(blackListAdapter);
        }
    }
}
