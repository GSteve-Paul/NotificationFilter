package com.lijn.notificationfilter.front.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.InServiceType;
import com.lijn.notificationfilter.back.entity.Program;
import com.lijn.notificationfilter.back.util.ResourceHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProfileAddDialog extends DialogFragment
{
    private static final String TAG = "ProfileAddDialog";

    private Profile caller;
    private List<Program> listProgram = null;
    private Spinner programPackageNameSpinner = null;
    private Spinner stateSpinner = null;
    private SwitchCompat displaySwitch = null;
    private EditText whiteListText = null;
    private EditText blackListText = null;
    private Button okButton = null;
    private Button cancelButton = null;

    public ProfileAddDialog() { super();}

    public ProfileAddDialog(List<Program> listProgram, Profile caller)
    {
        super();
        this.listProgram = listProgram;
        this.caller = caller;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_dialog, null);
        builder.setView(view);

        programPackageNameSpinner = view.findViewById(R.id.programPackageNameSpinner);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        displaySwitch = view.findViewById(R.id.displaySwitch);
        whiteListText = view.findViewById(R.id.whiteListText);
        blackListText = view.findViewById(R.id.blackListText);
        okButton = view.findViewById(R.id.okButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        List<Program> allValidProgram = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages)
        {
            Program program = new Program(packageInfo.packageName);
            if (listProgram.contains(program))
                continue;
            allValidProgram.add(program);
        }
        ArrayAdapter<Program> programAdapter = new ArrayAdapter<>
                (ResourceHolder.getContext(), android.R.layout.simple_spinner_item, allValidProgram);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programPackageNameSpinner.setAdapter(programAdapter);

        List<InServiceType> states = List.of(InServiceType.values());
        ArrayAdapter<InServiceType> stateAdapter = new ArrayAdapter<>
                (ResourceHolder.getContext(), android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        okButton.setOnClickListener((v) -> {
            Program program = (Program) programPackageNameSpinner.getSelectedItem();
            Boolean needDisplay = displaySwitch.isChecked();
            InServiceType enabledType = (InServiceType) stateSpinner.getSelectedItem();

            String whiteListAllText = whiteListText.getText().toString();
            String[] whiteListLines = whiteListAllText.split(System.lineSeparator());
            List<String> whiteList = List.of(whiteListLines);

            String blackListAllText = blackListText.getText().toString();
            String[] blackListLines = blackListAllText.split(System.lineSeparator());
            List<String> blackList = List.of(blackListLines);

            caller.addProfile(new FilterData(program, needDisplay, enabledType, whiteList, blackList));

            dismiss();
        });

        cancelButton.setOnClickListener((v) -> dismiss());

        return builder.create();
    }
}
