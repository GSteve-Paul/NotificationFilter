package com.lijn.notificationfilter.back.entity;

import java.util.Objects;

public class Program
{
    String packageName;
    public String getPackageName() {
        return packageName;
    }

    public Program(){}

    public Program(String packageName)
    {
        this.packageName = packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return this.packageName.equals(program.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName);
    }
}
