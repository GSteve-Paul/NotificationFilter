package com.lijn.notificationfilter.back.entity.programsetting;


import java.util.Objects;

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

    public ProgramSetting(ProgramSetting setting)
    {
        logNotificationVariety = new Variety<>(setting.logNotificationVariety);
        filterVariety = new Variety<>(setting.filterVariety);
        autoStartWhenBoot = setting.autoStartWhenBoot;
        running = setting.running;
    }

    public boolean getLogNotificationVariety(NotificationType notificationType)
    {
        return logNotificationVariety.getVariety(notificationType);
    }

    public void setLogNotificationVariety(NotificationType notificationType, boolean val)
    {
        if (val)
        {
            this.logNotificationVariety.setVariety(notificationType);
        }
        else
        {
            this.logNotificationVariety.clearVariety(notificationType);
        }
    }

    public boolean getFilterVariety(FilterType filterType)
    {
        return filterVariety.getVariety(filterType);
    }

    public void setFilterVariety(FilterType filterType, boolean val)
    {
        if (val)
        {
            this.filterVariety.setVariety(filterType);
        }
        else
        {
            this.filterVariety.clearVariety(filterType);
        }
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ProgramSetting that)) return false;
        return Objects.equals(logNotificationVariety, that.logNotificationVariety)
                && Objects.equals(filterVariety, that.filterVariety)
                && Objects.equals(autoStartWhenBoot, that.autoStartWhenBoot)
                && Objects.equals(running, that.running);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(logNotificationVariety, filterVariety, autoStartWhenBoot, running);
    }
}
