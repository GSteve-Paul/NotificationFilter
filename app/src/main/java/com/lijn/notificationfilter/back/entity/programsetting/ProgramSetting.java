package com.lijn.notificationfilter.back.entity.programsetting;


public class ProgramSetting
{
    private Variety<NotificationType> logNotificationVariety;
    private Variety<FilterType> filterVariety;
    private Boolean autoStartWhenBoot;
    private Boolean running;

    public ProgramSetting()
    {
        logNotificationVariety = new Variety<>();
        filterVariety = new Variety<>();
        autoStartWhenBoot = false;
        running = false;
    }

    public boolean getLogNotificationVariety(NotificationType notificationType)
    {
        return logNotificationVariety.getVariety(notificationType);
    }

    public void setLogNotificationVariety(NotificationType notificationType)
    {
        this.logNotificationVariety.setVariety(notificationType);
    }

    public boolean getFilterVariety(FilterType filterType)
    {
        return filterVariety.getVariety(filterType);
    }

    public void setFilterVariety(FilterType filterType)
    {
        this.filterVariety.setVariety(filterType);
    }

    public boolean getAutoStartWhenBoot()
    {
        return autoStartWhenBoot;
    }

    public void setAutoStartWhenBoot(boolean autoStartWhenBoot)
    {
        this.autoStartWhenBoot = autoStartWhenBoot;
    }

    public boolean getRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }
}
