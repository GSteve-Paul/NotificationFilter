package com.lijn.notificationfilter.back.io.profileio;

import android.content.Context;
import com.google.gson.Gson;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.io.DataReader;
import com.lijn.notificationfilter.back.util.ResourceHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RuleProfileReader extends DataReader<List<FilterData>>
{
    private volatile static RuleProfileReader mInstance = null;

    private RuleProfileReader()
    {
        Context context = ResourceHolder.getContext();
        File file = context.getFilesDir();

        Path path = Paths.get(file.getAbsolutePath());
        path = path.resolve(ResourceHolder.RuleProfileFileName);

        file = path.toFile();
        try
        {
            List<FilterData> list = new ArrayList<>();
            if (!file.exists())
            {
                file.createNewFile();
                RuleProfileWriter.getInstance().write(list);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static RuleProfileReader getInstance()
    {
        if (mInstance == null)
        {
            synchronized (RuleProfileReader.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RuleProfileReader();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected Reader getReader()
    {
        Context context = ResourceHolder.getContext();
        InputStreamReader inputStreamReader = null;
        try
        {
            FileInputStream fileInputStream =
                    context.openFileInput(ResourceHolder.RuleProfileFileName);
            inputStreamReader = new InputStreamReader(fileInputStream
                    , StandardCharsets.UTF_8);
            return inputStreamReader;
        }
        catch (IOException ioException)
        {
            return null;
        }
    }

    @Override
    public List<FilterData> read() throws IOException
    {
        Gson gson = new Gson();
        Reader reader = getReader();
        List<FilterData> filterDataList = gson.fromJson(reader, List.class);
        reader.close();
        return filterDataList;
    }
}
