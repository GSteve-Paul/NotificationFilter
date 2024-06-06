package com.lijn.notificationfilter.back.entity.programsetting;

import java.util.Objects;

public class Variety<T extends Enum>
{
    Integer variety;

    public Variety() {variety = 0;}

    public Variety(Variety<T> variety) {this.variety = variety.variety;}

    public void setVariety(T t)
    {
        variety = variety | (1 << t.ordinal());
    }

    public void clearVariety(T t)
    {
        variety = variety & (~(1 << t.ordinal()));
    }

    public boolean getVariety(T t)
    {
        return ((variety >> t.ordinal()) & 1) == 1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Variety<?> variety1)) return false;
        return Objects.equals(variety, variety1.variety);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(variety);
    }
}
