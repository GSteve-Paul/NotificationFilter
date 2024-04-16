package com.lijn.notificationfilter.front;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lijn.notificationfilter.R;
import com.lijn.notificationfilter.back.NotificationListener;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        //
        NavController navController = Navigation.findNavController
                (this, R.id.nav_host);

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        Intent intentRight = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivity(intentRight);

        Intent intent = new Intent(this, NotificationListener.class);
        startService(intent);

    }
}
