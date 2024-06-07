package com.lijn.notificationfilter.front;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.manager.logservice.LogManager;
import com.lijn.notificationfilter.back.manager.programsettingservice.ProgramSettingManager;
import com.lijn.notificationfilter.back.service.NotificationListener;
import com.lijn.notificationfilter.back.util.ResourceHolder;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    Intent notificationIntent = null;

    private boolean isEnabled()
    {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat))
        {
            final String[] names = flat.split(":");
            for (String name : names)
            {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null)
                {
                    if (TextUtils.equals(pkgName, cn.getPackageName()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Navigation
        NavController navController = Navigation.findNavController
                (this, R.id.nav_host);
        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
        if (!isEnabled())
        {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
        }

        //NotificationListenerService
        notificationIntent = new Intent(this, NotificationListener.class);
        NotificationListener.requestRebind(new ComponentName(ResourceHolder.getContext(), NotificationListener.class));
        startService(notificationIntent);

        mytest();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED))
        {
            //this.finish();
        }
    }

    private void mytest()
    {
        /*
        RuleProfileManager ruleProfileManager = RuleProfileManager.getInstance();
        List<FilterData> list = List.of(
                new FilterData(new Program("com.example.notificationapp"), false, InServiceType.USE_BLACKLIST, List.of("white1", "white2", "white3"), List.of(".*通知.*")),
                new FilterData(new Program("fucker"),false, InServiceType.USE_WHITELIST,List.of("baise"),List.of("heise")));
        ruleProfileManager.save(list);

        ProgramSettingManager manager = ProgramSettingManager.getInstance();
        ProgramSetting setting = manager.getProgramSetting();
        setting.setAutoStartWhenBoot(false);
        setting.setRunning(true);
        setting.setLogNotificationVariety(NotificationType.PASSED, true);
        setting.setLogNotificationVariety(NotificationType.INTERCEPTED, true);
        setting.setLogNotificationVariety(NotificationType.UNCHECKED, true);

        setting.setFilterVariety(FilterType.RULE, true);
        setting.setFilterVariety(FilterType.GLOBAL, true);
        */
    }

    @Override
    protected void onDestroy()
    {
        Log.i(TAG, "onDestroy: ");
        stopService(notificationIntent);
        NotificationListener.requestUnbind(new ComponentName(ResourceHolder.getContext(), NotificationListener.class));
        LogManager.getInstance().flush();
        ProgramSettingManager.getInstance().flushProgramSetting();
        super.onDestroy();
    }
}
