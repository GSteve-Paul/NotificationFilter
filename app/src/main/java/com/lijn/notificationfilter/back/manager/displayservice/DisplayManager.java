package com.lijn.notificationfilter.back.manager.displayservice;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.util.Locale;

public final class DisplayManager implements IDisplayManager, TextToSpeech.OnInitListener
{
    private static final String TAG = DisplayManager.class.getSimpleName();
    private static volatile DisplayManager mInstance;
    private TextToSpeech tts = null;

    private DisplayManager()
    {
        tts = new TextToSpeech(ResourceHolder.getContext(), this);
    }

    public static DisplayManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (DisplayManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new DisplayManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onInit(int status)
    {
        tts.setLanguage(Locale.CHINA);
    }

    @Override
    public void doDisplay(String str)
    {
        tts.speak(str, TextToSpeech.QUEUE_FLUSH, null,null);
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.finalize();
    }
}
