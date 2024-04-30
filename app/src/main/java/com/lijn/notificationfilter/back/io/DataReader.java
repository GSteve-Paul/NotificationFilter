package com.lijn.notificationfilter.back.io;

import java.io.IOException;
import java.io.Reader;

public abstract class DataReader<T>
{
    abstract protected Reader getReader();

    abstract public T read() throws IOException;
}
