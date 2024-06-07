package com.lijn.notificationfilter.front.fragment;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.util.ResourceHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>
{
    private List<FilterData> dataList;
    private int selectedPosition;

    public ProfileAdapter()
    {
        dataList = new ArrayList<>();
        selectedPosition = -1;
    }

    public ProfileAdapter(List<FilterData> dataList)
    {
        this.dataList = dataList;
        selectedPosition = -1;
    }

    public int getSelectedPosition()
    {
        return selectedPosition;
    }

    public List<FilterData> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<FilterData> dataList)
    {
        this.dataList = dataList;
        notifyDataSetChanged();
        selectedPosition = -1;
    }

    @NonNull
    @NotNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.profile_list_item, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileViewHolder holder, int position)
    {
        FilterData data = dataList.get(position);
        holder.bind(data);
        if (selectedPosition == holder.getAdapterPosition())
        {
            Drawable background = AppCompatResources.getDrawable(ResourceHolder.getContext(), R.drawable.checked_profile_list_item_background);
            holder.itemView.setBackground(background);
        }
        else
        {
            Drawable background = AppCompatResources.getDrawable(ResourceHolder.getContext(), R.drawable.unchecked_profile_list_item_background);
            holder.itemView.setBackground(background);
        }

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


    public static class ProfileViewHolder extends RecyclerView.ViewHolder
    {
        TextView programPackageNameText;
        TextView isDisplayText;
        TextView enabledTypeText;
        Spinner whiteListSpinner;
        Spinner blackListSpinner;

        public ProfileViewHolder(@NonNull @NotNull View itemView)
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
