package com.lijn.notificationfilter.back.cache.adt;

import com.lijn.notificationfilter.back.entity.Program;

public class DoublyLinkedList
{
    public DoublyLinkedListNode begin;
    public DoublyLinkedListNode end;

    public DoublyLinkedList() {}

    boolean empty()
    {
        return begin == null || end == null;
    }

    public void takeFromListToEnd(DoublyLinkedListNode node)
    {
        if (this.empty())
        {
            begin = end = node;
            return;
        }
        if (node == end)
        {
            return;
        }
        if (node == begin)
        {
            this.begin = node.next;
            node.next.last = null;

            node.last = this.end;
            this.end.next = node;

            node.next = null;
            this.end = node;
            return;
        }
        DoublyLinkedListNode originNext = node.next;
        DoublyLinkedListNode originLast = node.last;

        originLast.next = originNext;
        originNext.last = originLast;

        this.end.next = node;
        node.last = this.end;

        node.next = null;
        end = node;
    }

    public void pushBack(DoublyLinkedListNode node)
    {
        if(this.empty())
        {
            begin = end = node;
            return;
        }

        this.end.next = node;
        node.last = this.end;
        node.next = null;
        this.end = node;
    }

    public void pushBack(Program program)
    {
        DoublyLinkedListNode node = new DoublyLinkedListNode(program);
        this.pushBack(node);
    }

    public void popFront()
    {
        if(this.empty())
        {
            throw new RuntimeException("do popFront to an empty DoublyLinkedList");
        }
        DoublyLinkedListNode beginNext = this.begin.next;
        beginNext.last = null;
        this.begin.next = this.begin.last = null;
        this.begin = beginNext;
    }
}
