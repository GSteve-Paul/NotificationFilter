package com.lijn.notificationfilter.back.cache.adt;

import com.lijn.notificationfilter.back.entity.Program;

public class DoublyLinkedListNode
{
    Program program;
    DoublyLinkedListNode last;
    DoublyLinkedListNode next;

    public DoublyLinkedListNode() {}

    public DoublyLinkedListNode(Program program)
    {
        this.program = program;
    }

    public DoublyLinkedListNode(Program program, DoublyLinkedListNode last, DoublyLinkedListNode next)
    {
        this.program = program;
        this.last = last;
        this.next = next;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public DoublyLinkedListNode getLast() {
        return last;
    }

    public void setLast(DoublyLinkedListNode last) {
        this.last = last;
    }

    public DoublyLinkedListNode getNext() {
        return next;
    }

    public void setNext(DoublyLinkedListNode next) {
        this.next = next;
    }
}
