package com.lijn.notificationfilter.back.io;

import android.content.Context;
import com.lijn.notificationfilter.global.ResourceHolder;

import java.io.File;
import java.io.IOException;

public class GlobalProfileReader
{
    private static volatile GlobalProfileReader mInstance;

    private GlobalProfileReader() {}

    public static GlobalProfileReader getInstance()
    {
        if(mInstance == null)
        {
            synchronized (GlobalProfileReader.class)
            {
                if(mInstance == null)
                {
                    mInstance = new GlobalProfileReader();
                }
            }
        }
        return mInstance;
    }

    private void initializeFile() throws IOException
    {
        Context context = ResourceHolder.getContext();
        File file = new File(context.getFilesDir(), "global_profile.json");
    }
}
