package com.lijn.notificationfilter.back.manager.displayservice;

public final class DisplayManager implements IDisplayManager
{
    private static volatile DisplayManager mInstance;

    private DisplayManager() {}

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
    public void doDisplay()
    {

    }
}
