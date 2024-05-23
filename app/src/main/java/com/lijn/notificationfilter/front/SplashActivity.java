package com.lijn.notificationfilter.front;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.lijn.notificationfilter.R;

public class SplashActivity extends AppCompatActivity
{

    private static final int SPLASH_DISPLAY_LENGTH = 800; //

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hide the action bar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        // Use a Handler to delay the navigation to MainActivity
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                navigateToMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void navigateToMainActivity()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the splash activity
    }
}