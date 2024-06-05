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
import com.lijn.notificationfilter.back.manager.profileservice.RuleProfileManager;
import com.lijn.notificationfilter.back.manager.filterservice.InServiceManager;

import java.util.List;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Button;

public class ruleProfileAdapter extends BaseAdapter {
    private Context mContext;
    private List<FilterData> rulefilterdata;
    private Program program;
    private Boolean needDisplay;
    private InServiceType enabledType;
    private List<String> whiteList;
    private List<String> blackList;
    public ruleProfileAdapter() {}

    public ruleProfileAdapter(List<FilterData>rulefilterdata, Context mContext) {
        this.rulefilterdata = rulefilterdata;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return rulefilterdata.size();
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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.profile_item,parent,false);
            holder = new ViewHolder();
            holder.needDisplayButton = convertView.findViewById(R.id.display);
            holder.programEditText =  convertView.findViewById(R.id.program);
            holder.blackListEditText =  convertView.findViewById(R.id.blackList);
            holder.whiteListEditText =  convertView.findViewById(R.id.whiteList);
            holder.ruleModifyButton = convertView.findViewById(R.id.ruleModify);
            holder.ruleDeleteButton = convertView.findViewById(R.id.ruleDelete);
            holder.ruleAddButton = convertView.findViewById(R.id.ruleAdd);
            holder.ruleViewButton = convertView.findViewById(R.id.ruleView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        FilterData filterdata = rulefilterdata.get(position);

        holder.programEditText.setText(filterdata.getProgram() != null ? (CharSequence) filterdata.getProgram() : "");
        holder.needDisplayButton.setChecked(filterdata.getNeedDisplay());
        holder.whiteListEditText.setText(filterdata.getWhiteList() != null ? (CharSequence) filterdata.getWhiteList() : "");
        holder.blackListEditText.setText(filterdata.getBlackList() != null ? (CharSequence) filterdata.getBlackList() : "");

        holder.ruleModifyButton.setOnClickListener(v -> {
            stopFilterService();

            ruleModify(position, filterdata);

            startFilterService();
        });
        holder.ruleDeleteButton.setOnClickListener(v -> ruleDelete(position));
        holder.ruleAddButton.setOnClickListener(v -> ruleAdd(new FilterData()));
        holder.ruleViewButton.setOnClickListener(v -> ruleView());

        return convertView;
    }

    private static class ViewHolder {
        EditText programEditText;
        RadioButton needDisplayButton;
        EditText blackListEditText;
        EditText whiteListEditText;
        Button ruleModifyButton;
        Button ruleDeleteButton;
        Button ruleAddButton;
        Button ruleViewButton;
    }
    private void stopFilterService() {
        Intent intent = new Intent(mContext, InServiceManager.class);
        mContext.stopService(intent);
    }

    private void startFilterService() {
        Intent intent = new Intent(mContext, InServiceManager.class);
        mContext.startService(intent);
    }
    public void ruleAdd(FilterData rule) {
        if (rule != null) {
            rulefilterdata.add(rule);
            notifyDataSetChanged();
        }
    }
    public void ruleModify(int position,FilterData newRule) {

        if (position >= 0 && position < rulefilterdata.size()) {

            FilterData ruleToModify = rulefilterdata.get(position);

            ruleToModify.setProgram(newRule.getProgram());
            ruleToModify.setNeedDisplay(newRule.getNeedDisplay());
            ruleToModify.setBlackList(newRule.getBlackList());
            ruleToModify.setWhiteList(newRule.getWhiteList());
            RuleProfileManager.getInstance().save(rulefilterdata);

            notifyDataSetChanged();
        }
    }

    public void ruleDelete(int position) {
        if (position >= 0 && position < rulefilterdata.size()) {
            rulefilterdata.remove(position);
            notifyDataSetChanged();
        }
    }
    public void ruleView() {
        List<FilterData> filterDataList = RuleProfileManager.getInstance().read();
        StringBuilder ruleInfo = new StringBuilder();
        for (FilterData filterData : filterDataList) {
            ruleInfo.append("Program: ").append(filterData.getProgram()).append("\n");
            ruleInfo.append("Need Display: ").append(filterData.getNeedDisplay()).append("\n");
            ruleInfo.append("White List: ").append(filterData.getWhiteList()).append("\n");
            ruleInfo.append("Black List: ").append(filterData.getBlackList()).append("\n");
            ruleInfo.append("\n");
        }
        Log.d("RuleView", ruleInfo.toString());
    }
//        public void onAddRuleClick(View view) {
//        // 获取父 ConstraintLayout
//        ConstraintLayout ruleConstraintLayout = getView().findViewById(R.id.ruleConstraintLayout);
//
//        // 创建新的 ConstraintLayout
//        ConstraintLayout profile = (ConstraintLayout) LayoutInflater.from(getContext())
//                .inflate(R.layout.profile_item, ruleConstraintLayout, false);
//
//        // 设置新的 ConstraintLayout 的属性和约束
//        RadioButton displayRadioButton = profile.findViewById(R.id.display);
//        EditText programEditText = profile.findViewById(R.id.program);
//
//        // 设置默认值
//        programEditText.setText("请输入你要过滤的程序名");
//        program= (Program) programEditText.getText();
//        // 添加新的 ConstraintLayout 到父布局中
//        ruleConstraintLayout.addView(profile);
//        needDisplay = (Boolean) ruleConstraintLayout.isSelected();
//    }
}
