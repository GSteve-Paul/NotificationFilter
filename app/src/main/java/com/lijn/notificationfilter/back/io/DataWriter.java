package com.lijn.notificationfilter.back.io;

import java.io.IOException;
import java.io.Writer;

public abstract class DataWriter<T>
{
    abstract protected Writer getWriter();

    abstract public void write(T data) throws IOException;
}
