package com.lijn.notificationfilter.front.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.manager.profileservice.GlobalProfileManager;
import com.lijn.notificationfilter.back.manager.profileservice.RuleProfileManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment
{
    private static final String TAG = "Profile";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RULE = 1;
    private static final int GLOBAL = 2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int type;
    private Button ruleButton;
    private Button globalButton;
    private Button deleteButton;
    private Button editButton;
    private Button addButton;
    private RecyclerView profileView;
    private ProfileAdapter adapter;


    public Profile()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2)
    {
        Profile fragment = new Profile();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ruleButton = view.findViewById(R.id.ruleButton);
        globalButton = view.findViewById(R.id.globalButton);

        deleteButton = view.findViewById(R.id.deleteProfileButton);
        editButton = view.findViewById(R.id.editProfileButton);
        addButton = view.findViewById(R.id.addProfileButton);

        profileView = view.findViewById(R.id.profileView);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        profileView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProfileAdapter(null);
        profileView.setAdapter(adapter);

        addButton.setOnClickListener((v) -> {
            List<Program> existProgram = new ArrayList<>();
            for (FilterData data : adapter.getDataList())
                existProgram.add(data.getProgram());
            ProfileAddDialog dialog = new ProfileAddDialog(existProgram, this);
            dialog.show(getFragmentManager(), "ProfileAddDialog");
        });

        deleteButton.setOnClickListener((v) -> deleteProfile());

        editButton.setOnClickListener((v) -> {
            int position = adapter.getSelectedPosition();
            FilterData editFilterData = adapter.getDataList().get(position);
            ProfileEditDialog dialog = new ProfileEditDialog(editFilterData, this);
            dialog.show(getFragmentManager(), "ProfileEditDialog");
        });

        ruleButton.setOnClickListener((v) -> {
            type = RULE;
            //change the add/delete access
            addButton.setEnabled(true);
            deleteButton.setEnabled(true);
            //change the style of rule/global button
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            globalButton.setTypeface(boldTypeface);

            Typeface simpleTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
            ruleButton.setTypeface(simpleTypeface);

            GradientDrawable colorDrawable = new GradientDrawable();
            colorDrawable.setColor(0xFFDBF6ED);
            globalButton.setBackground(colorDrawable);

            GradientDrawable transparentDrawable = new GradientDrawable();
            transparentDrawable.setColor(0x00000000);
            ruleButton.setBackground(transparentDrawable);

            //save the content before
            List<FilterData> filterDataList = adapter.getDataList();
            if (filterDataList != null)
                GlobalProfileManager.getInstance().save(filterDataList.get(0));
            //change the content of the recyclerview
            adapter.setDataList(RuleProfileManager.getInstance().read());
        });

        globalButton.setOnClickListener((v) -> {
            type = GLOBAL;
            //change the add/delete access
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            //change the style of rule/global button
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            ruleButton.setTypeface(boldTypeface);

            Typeface simpleTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
            globalButton.setTypeface(simpleTypeface);

            GradientDrawable colorDrawable = new GradientDrawable();
            colorDrawable.setColor(0xFFDBF6ED);
            ruleButton.setBackground(colorDrawable);

            GradientDrawable transparentDrawable = new GradientDrawable();
            transparentDrawable.setColor(0x00000000);
            globalButton.setBackground(transparentDrawable);

            //save
            List<FilterData> filterDataList = adapter.getDataList();
            if (filterDataList != null)
                RuleProfileManager.getInstance().save(filterDataList);
            //change the content of the recyclerview
            adapter.setDataList(List.of(GlobalProfileManager.getInstance().read()));
        });

        ruleButton.performClick();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onDestroyView()
    {
        if (type == RULE)
            RuleProfileManager.getInstance().save(adapter.getDataList());
        else
            GlobalProfileManager.getInstance().save(adapter.getDataList().get(0));
        super.onDestroyView();
    }

    public void addProfile(FilterData data)
    {
        adapter.getDataList().add(data);
        adapter.notifyItemInserted(adapter.getDataList().size() - 1);
    }

    public void deleteProfile()
    {
        int position = adapter.getSelectedPosition();
        if(position == -1) return;
        adapter.getDataList().remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void editProfile(FilterData data)
    {
        int position = adapter.getSelectedPosition();
        if(position == -1) return;
        adapter.getDataList().set(position, data);
        adapter.notifyItemChanged(position);
    }
}