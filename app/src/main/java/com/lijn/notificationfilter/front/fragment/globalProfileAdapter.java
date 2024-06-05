package com.lijn.notificationfilter.front.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.manager.profileservice.GlobalProfileManager;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;

import java.util.List;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class globalProfileAdapter extends BaseAdapter{
    private Context mContext;
    private List<FilterData> globalfilterdata;
    private int flag=0;
    private Program program;
    private Boolean needDisplay;
    private InServiceType enabledType;
    private List<String> whiteList;
    private List<String> blackList;
    public globalProfileAdapter() {}

    public globalProfileAdapter(List<FilterData> globalfilterdata, Context mContext) {
        this.globalfilterdata = globalfilterdata;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return globalfilterdata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        globalProfileAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.profile_item,parent,false);
            holder = new globalProfileAdapter.ViewHolder();
            holder.needDisplayButton = convertView.findViewById(R.id.display);
            holder.programEditText =  convertView.findViewById(R.id.program);
            holder.blackListEditText =  convertView.findViewById(R.id.blackList);
            holder.whiteListEditText =  convertView.findViewById(R.id.whiteList);
            holder.globalModifyButton = convertView.findViewById(R.id.globalModify);
            holder.globalDeleteButton = convertView.findViewById(R.id.globalDelete);
            holder.globalAddButton = convertView.findViewById(R.id.globalAdd);
            holder.globalViewButton = convertView.findViewById(R.id.globalView);
            convertView.setTag(holder);
        }
        else {
            holder = (globalProfileAdapter.ViewHolder) convertView.getTag();
        }

        FilterData filterdata = globalfilterdata.get(position);

        holder.programEditText.setText(filterdata.getProgram() != null ? (CharSequence) filterdata.getProgram() : "");
        holder.needDisplayButton.setChecked(filterdata.getNeedDisplay());
        holder.whiteListEditText.setText(filterdata.getWhiteList() != null ? (CharSequence) filterdata.getWhiteList() : "");
        holder.blackListEditText.setText(filterdata.getBlackList() != null ? (CharSequence) filterdata.getBlackList() : "");

        holder.globalModifyButton.setOnClickListener(v -> {

            stopFilterService();

            globalModify(position, filterdata);

            startFilterService();
        });
        holder.globalDeleteButton.setOnClickListener(v -> globalDelete(position));
        holder.globalAddButton.setOnClickListener(v -> globalAdd(new FilterData()));
        holder.globalViewButton.setOnClickListener(v -> globalView());
        return convertView;
    }

    private static class ViewHolder {
        EditText programEditText;
        RadioButton needDisplayButton;
        EditText blackListEditText;
        EditText whiteListEditText;
        Button globalModifyButton;
        Button globalDeleteButton;
        Button globalAddButton;
        Button globalViewButton;
    }
    private void stopFilterService() {
        Intent intent = new Intent(mContext, InServiceManager.class);
        mContext.stopService(intent);
    }

    private void startFilterService() {
        Intent intent = new Intent(mContext, InServiceManager.class);
        mContext.startService(intent);
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("已经添加了一个项目，不可再添加。");
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }
    public void globalAdd(FilterData rule) {
        if (rule != null && flag == 0) {
            flag = 1;
            globalfilterdata.add(rule);
            notifyDataSetChanged();
        }
        else{
            showDialog();
        }
    }
    public void globalModify(int position,FilterData newRule) {

        if (position >= 0 && position < globalfilterdata.size()) {

            FilterData globalToModify = globalfilterdata.get(position);

            globalToModify.setProgram(newRule.getProgram());
            globalToModify.setNeedDisplay(newRule.getNeedDisplay());
            globalToModify.setBlackList(newRule.getBlackList());
            globalToModify.setWhiteList(newRule.getWhiteList());
            GlobalProfileManager.getInstance().save(globalToModify);
            notifyDataSetChanged();
        }
    }
    public void globalDelete(int position) {
        if (position >= 0 && position < globalfilterdata.size()) {
            flag = 0;
            globalfilterdata.remove(position);
            notifyDataSetChanged();
        }
    }
    public void globalView() {
        FilterData filterDataList = (FilterData) GlobalProfileManager.getInstance().read();
        String globalInfo = "Program: " + filterDataList.getProgram() + "\n" +
                "Need Display: " + filterDataList.getNeedDisplay() + "\n" +
                "White List: " + filterDataList.getWhiteList() + "\n" +
                "Black List: " + filterDataList.getBlackList() + "\n" +
                "\n";
        Log.d("GlobalView", globalInfo);
    }
}
