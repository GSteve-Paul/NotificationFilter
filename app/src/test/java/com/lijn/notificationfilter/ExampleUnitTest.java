package com.lijn.notificationfilter;

import com.lijn.notificationfilter.back.entity.cache.LRUCache;
import com.lijn.notificationfilter.back.entity.cache.adt.DoublyLinkedList;
import com.lijn.notificationfilter.back.entity.cache.adt.DoublyLinkedListNode;
import com.lijn.notificationfilter.back.entity.FilterData;
import com.lijn.notificationfilter.back.entity.Program;
import org.junit.Test;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void DoublyLinkedListTest()
    {
        DoublyLinkedList list = new DoublyLinkedList();
        DoublyLinkedListNode node1 = new DoublyLinkedListNode(new Program("1"));
        list.pushBack(node1);
        assert node1 == list.begin && node1 == list.end;
        DoublyLinkedListNode node2 = new DoublyLinkedListNode(new Program("2"));
        list.pushBack(node2);
        assert node1 == list.begin;
        assert node2 == list.end;
        DoublyLinkedListNode node3 = new DoublyLinkedListNode(new Program("3"));
        list.pushBack(node3);
        assert node3 == list.end;
        assert node2.getNext() == node3;
        assert node2.getLast() == node1;
        assert node1.getNext() == node2;
        assert node1.getLast() == null;
        assert node3.getNext() == null;
        assert node3.getLast() == node2;
        list.takeFromListToEnd(node2);
        assert node2.getNext() == null;
        assert node2.getLast() == node3;
        assert node1.getNext() == node3;
        assert node1.getLast() == null;
        assert node3.getNext() == node2;
        assert node3.getLast() == node1;
        assert node1 == list.begin;
        assert node2 == list.end;

        list.popFront();
        assert node3 == list.begin;
    }

    @Test
    public void LRUCacheTest() throws IOException
    {
        LRUCache cache = new LRUCache();
        FilterData filterData;
        filterData = cache.getFilterDataFromCache(new Program("1"));
        assert filterData == null;
        filterData = new FilterData();
        filterData.setProgram(new Program("2"));
        cache.setFilterDataIntoCache(filterData);
        filterData = cache.getFilterDataFromCache(new Program("1"));
        assert filterData == null;
        filterData = cache.getFilterDataFromCache(new Program("2"));
        assert filterData != null && filterData.getProgram().equals(new Program("2"));
        filterData = new FilterData();
        filterData.setProgram(new Program("1"));
        cache.setFilterDataIntoCache(filterData);

        for (int i = 3; i <= 20; i++)
        {
            filterData = new FilterData();
            filterData.setProgram(new Program(String.valueOf(i)));
            cache.setFilterDataIntoCache(filterData);
        }
        filterData = cache.getFilterDataFromCache(new Program("1"));
        assert filterData != null;
        filterData = cache.getFilterDataFromCache(new Program("2"));
        assert filterData != null;

        filterData = new FilterData();
        filterData.setProgram(new Program("21"));
        cache.setFilterDataIntoCache(filterData);

        filterData = cache.getFilterDataFromCache(new Program("3"));
        assert filterData == null;

        filterData = new FilterData();
        filterData.setProgram(new Program("9"));
        cache.setFilterDataIntoCache(filterData);

        filterData = new FilterData();
        filterData.setProgram(new Program("22"));
        cache.setFilterDataIntoCache(filterData);

        filterData = cache.getFilterDataFromCache(new Program("4"));
        assert filterData == null;

        filterData = cache.getFilterDataFromCache(new Program("5"));
        assert filterData != null && filterData.getProgram().getPackageName().equals("5");
    }
}