package com.lijn.notificationfilter.front.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
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

import java.util.List;

public class ProfileEditDialog extends DialogFragment
{
    private static final String TAG = "ProfileEditDialog";

    private Profile caller;
    private FilterData preData;
    private Spinner programPackageNameSpinner = null;
    private Spinner stateSpinner = null;
    private SwitchCompat displaySwitch = null;
    private EditText whiteListText = null;
    private EditText blackListText = null;
    private Button okButton = null;
    private Button cancelButton = null;

    public ProfileEditDialog() {super();}

    public ProfileEditDialog(FilterData preData, Profile caller)
    {
        super();
        this.caller = caller;
        this.preData = preData;
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

        ArrayAdapter<Program> programAdapter = new ArrayAdapter<>
                (ResourceHolder.getContext(), android.R.layout.simple_spinner_item, List.of(preData.getProgram()));
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programPackageNameSpinner.setAdapter(programAdapter);

        displaySwitch.setChecked(preData.getNeedDisplay());

        List<InServiceType> states = List.of(InServiceType.values());
        ArrayAdapter<InServiceType> stateAdapter = new ArrayAdapter<>
                (ResourceHolder.getContext(), android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);
        int preSelectPosition = 0;
        for (int i = 0; i < states.size(); i++)
        {
            if (states.get(i) == preData.getEnabledType())
            {
                preSelectPosition = i;
                break;
            }
        }
        stateSpinner.setSelection(preSelectPosition);

        for (String whiteListLine : preData.getWhiteList())
        {
            whiteListText.getText().append(whiteListLine);
            whiteListText.getText().append(System.lineSeparator());
        }
        for (String blackListLine : preData.getBlackList())
        {
            blackListText.getText().append(blackListLine);
            blackListText.getText().append(System.lineSeparator());
        }

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

            caller.editProfile(new FilterData(program, needDisplay, enabledType, whiteList, blackList));

            dismiss();
        });

        cancelButton.setOnClickListener((v) -> dismiss());

        return builder.create();
    }
}
