package com.lijn.notificationfilter.back.entity.cache;

import com.lijn.notificationfilter.back.entity.cache.adt.DoublyLinkedList;
import com.lijn.notificationfilter.back.entity.cache.adt.DoublyLinkedListNode;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;

import java.util.HashMap;
import java.util.Map;

public class LRUCache
{
    final Integer capacity = 50;
    DoublyLinkedList linkedList;
    Map<Program, DoublyLinkedListNode> programToNodeMapper;
    Map<Program, FilterData> programToDataMapper;

    public LRUCache()
    {
        programToNodeMapper = new HashMap<>();
        programToDataMapper = new HashMap<>();
        linkedList = new DoublyLinkedList();
    }

    DoublyLinkedListNode isProgramInCache(Program program)
    {
        return programToNodeMapper.get(program);
    }

    public FilterData getFilterDataFromCache(Program program)
    {
        synchronized (this)
        {
            //this program is in cache now
            DoublyLinkedListNode temp;
            if ((temp = isProgramInCache(program)) != null)
            {
                linkedList.takeFromListToEnd(temp);
                programToNodeMapper.put(program, linkedList.end);
                return programToDataMapper.get(program);
            }
            return null;
        }
    }

    public void setFilterDataIntoCache(FilterData data)
    {
        synchronized (this)
        {
            Program program = data.getProgram();
            //this program is in cache now
            DoublyLinkedListNode temp;
            if ((temp = isProgramInCache(program)) != null)
            {
                linkedList.takeFromListToEnd(temp);
                programToNodeMapper.put(program, linkedList.end);
                programToDataMapper.put(program, data);
                return;
            }
            //this program is not in cache now
            int programToDataMapperSize = programToDataMapper.size();
            //adding it can't overflow
            if (programToDataMapperSize + 1 <= capacity)
            {
                linkedList.pushBack(program);
                programToNodeMapper.put(program, linkedList.end);
                programToDataMapper.put(program, data);
                return;
            }
            //adding it can lead to overflow
            linkedList.pushBack(program);
            programToNodeMapper.put(program, linkedList.end);
            programToDataMapper.put(program, data);
            removeFilterDataFromCache();
        }
    }

    private void removeFilterDataFromCache()
    {
        DoublyLinkedListNode node = linkedList.begin;
        Program program = node.getProgram();
        programToDataMapper.remove(program);
        programToNodeMapper.remove(program);
        linkedList.popFront();
    }
}
