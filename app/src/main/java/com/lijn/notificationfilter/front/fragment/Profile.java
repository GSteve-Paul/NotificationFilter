package com.lijn.notificationfilter.front.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.front.fragment.globalProfileAdapter;
import com.lijn.notificationfilter.front.fragment.ruleProfileAdapter;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ruleProfileAdapter ruleAdapter;
    private ListView ruleListView;
    private List<FilterData> rulefilterdatalist;

    private globalProfileAdapter globalAdapter;
    private ListView globalListView;
    private List<FilterData> globalfilterdatalist;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ConstraintLayout ruleLayout;
    ConstraintLayout globalLayout;
    Button ruleButton;
    Button globalButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isGlobalSelected = false;
    private boolean isRuleSelected = false;
    public Profile() {
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
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        rulefilterdatalist = new ArrayList<>();
        globalfilterdatalist = new ArrayList<>();


//        globalButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        isGlobalSelected = true;
//                        isRuleSelected = false;
//                        showHideLayout();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        isGlobalSelected = false;
//                        showHideLayout();
//                        break;
//                }
//                return true;
//            }
//        });
//        ruleButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public void onTouch(View view) {}
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        isRuleSelected = true;
//                        isGlobalSelected = false;
//                        showHideLayout();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        isRuleSelected = false;
//                        showHideLayout();
//                        break;
//                }
//                return true;
//            }
//        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        showHideLayout();

        globalButton = view.findViewById(R.id.globalButton);
        ruleButton = view.findViewById(R.id.ruleButton);

        globalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGlobalSelected = true;
                isRuleSelected = false;
                showHideLayout();
            }
        });
        ruleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRuleSelected = true;
                isGlobalSelected = false;
                showHideLayout();
            }
        });

        super.onViewCreated(view, savedInstanceState);

        ruleListView = view.findViewById(R.id.ruleList);
        ruleAdapter = new ruleProfileAdapter(rulefilterdatalist, getContext());
        ruleListView.setAdapter(ruleAdapter);

        globalListView = view.findViewById(R.id.globalList);
        globalAdapter = new globalProfileAdapter(globalfilterdatalist, getContext());
        globalListView.setAdapter(globalAdapter);

        Button ruleViewButton = view.findViewById(R.id.ruleView);
        ruleViewButton.setOnClickListener(v -> viewRules());

        Button ruleAddButton = view.findViewById(R.id.ruleAdd);
        ruleAddButton.setOnClickListener(v -> addRule());

        Button ruleModifyButton = view.findViewById(R.id.ruleModify);
        ruleModifyButton.setOnClickListener(v -> modifyRule());

        Button ruleDeleteButton = view.findViewById(R.id.ruleDelete);
        ruleDeleteButton.setOnClickListener(v -> deleteRule());

        Button globalViewButton = view.findViewById(R.id.globalView);
        globalViewButton.setOnClickListener(v -> viewGlobal());

        Button globalAddButton = view.findViewById(R.id.globalAdd);
        globalAddButton.setOnClickListener(v -> addGlobal());

        Button globalModifyButton = view.findViewById(R.id.globalModify);
        globalModifyButton.setOnClickListener(v -> modifyGlobal());

        Button globalDeleteButton = view.findViewById(R.id.globalDelete);
        globalDeleteButton.setOnClickListener(v -> deleteGlobal());

        ruleButton = view.findViewById(R.id.ruleButton);
        globalButton = view.findViewById(R.id.globalButton);

        ruleButton.setOnClickListener((v) -> {
            turnRuleProfile();
        });

        globalButton.setOnClickListener((v) -> {
            turnGlobalProfile();
        });
    }

    private void turnGlobalProfile()
    {
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
    }

    private void turnRuleProfile()
    {
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
    }
    private void showHideLayout() {
        ruleLayout = getView().findViewById(R.id.ruleConstraintLayout);
        globalLayout = getView().findViewById(R.id.globalConstraintLayout);
        if (isGlobalSelected) {
            globalLayout.setVisibility(View.VISIBLE);
            ruleLayout.setVisibility(View.GONE);
        } else if (isRuleSelected) {
            globalLayout.setVisibility(View.GONE);
            ruleLayout.setVisibility(View.VISIBLE);
        } else {
            globalLayout.setVisibility(View.GONE);
            ruleLayout.setVisibility(View.GONE);
        }
    }
    private void addRule() {
        FilterData newRule = new FilterData();
        ruleAdapter.ruleAdd(newRule);
    }

    private void modifyRule() {
        int selectedPosition = ruleListView.getSelectedItemPosition();
        if (selectedPosition != ListView.INVALID_POSITION) {
            FilterData ruleToUpdate = rulefilterdatalist.get(selectedPosition);
            ruleAdapter.ruleModify(selectedPosition, ruleToUpdate);
        }
    }

    private void deleteRule() {
        int selectedPosition = ruleListView.getSelectedItemPosition();
        if (selectedPosition != ListView.INVALID_POSITION) {
            ruleAdapter.ruleDelete(selectedPosition);
        }
    }

    private void viewRules() {
        ruleAdapter.ruleView();
    }
    private void addGlobal() {
        FilterData newglobal = new FilterData();
        globalAdapter.globalAdd(newglobal);
    }

    private void modifyGlobal() {
        int selectedPosition = ruleListView.getSelectedItemPosition();
        if (selectedPosition != ListView.INVALID_POSITION) {
            FilterData globalToUpdate = globalfilterdatalist.get(selectedPosition);
            globalAdapter.globalModify(selectedPosition, globalToUpdate);
        }
    }

    private void deleteGlobal() {
        int selectedPosition = globalListView.getSelectedItemPosition();
        if (selectedPosition != ListView.INVALID_POSITION) {
            globalAdapter.globalDelete(selectedPosition);
        }
    }

    private void viewGlobal() {
        globalAdapter.globalView();
    }
}