package com.lijn.notificationfilter.back.entity.programsetting;

public class Variety<T extends Enum>
{
    Integer variety;

    public Variety() {variety = 0;}

    public void setVariety(T t)
    {
        variety = variety | (1 << t.ordinal());
    }

    public boolean getVariety(T t)
    {
        return ((variety >> t.ordinal()) & 1) == 1;
    }
}
