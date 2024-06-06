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
import org.jetbrains.annotations.NotNull;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button ruleButton;
    Button globalButton;
    Button deleteButton;
    Button editButton;
    Button addButton;
    RecyclerView ruleProfileView;

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

        ruleProfileView = view.findViewById(R.id.ruleProfileView);
        ruleProfileView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ruleButton.setOnClickListener((v) -> turnRuleProfile());
        globalButton.setOnClickListener((v) -> turnGlobalProfile());


    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume: ");
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
}