package MyLibrary.MyDeque;

import java.util.function.Consumer;

import MyLibrary.MyQueue.*;

import java.util.function.BiConsumer;


// This is the solution to Assign04_02

public class MyDequeList<T> extends MyDequeBase<T> {
    
    private class Node {
        T value;
        Node next;
        Node prev;

        Node(T val) {
            value = val;
            this.next = null;
            this.prev = null;
        }
    }



    private int nitm = 0;
    private Node head = null;
    private Node tail = null;

    public MyDequeList() {
        
    }

    public int size() {
        return nitm;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return nitm == 0;
    }


    public T fpeek$raw() {
        return head.value;
    }

    public T fpeek$opt() {
        if (isEmpty()) return null;
        return fpeek$raw();
    }

    public T fpeek$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return fpeek$raw();
    }



    public T rpeek$raw() {
        return tail.value;
    }

    public T rpeek$opt() {
        if (isEmpty()) return null;
        return rpeek$raw();
    }

    public T  rpeek$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return rpeek$raw();
    }



    public void fenque$raw(T itm) {
        Node node = new Node(itm);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        nitm ++;
    }

    public boolean fenque$opt(T itm) {
        if (isFull()) {
            return false;
        } else {
            fenque$raw(itm);
            return true;
        }
    }

    public void fenque$exn(T itm) {
        
        if (!fenque$opt(itm)) throw new MyDequeFullExn(); else return;
        
    }


    public void renque$raw(T itm) {
        Node node = new Node(itm);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        nitm ++;
    }

    public boolean renque$opt(T itm) {
        if (isFull()) {
            return false;
        } else {
                renque$raw(itm);
                return true;
        }
    }

    public void renque$exn(T itm) {
        if (!renque$opt(itm)) throw new MyDequeFullExn(); else return;
    }


    public T fdeque$raw() {
        T val = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        nitm --;
        return val;
    }

    public T fdeque$opt() {
        if (isEmpty()) return null;
        return fdeque$raw();
    }

    public T fdeque$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return fdeque$raw();
    }


    public T rdeque$raw() {
        T val = tail.value;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        nitm --;
        return val;
    }

    public T rdeque$opt() {
        if (isEmpty()) return null;
        return rdeque$raw();
    }

    public T rdeque$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return rdeque$raw();
    }



    public T top$raw() {
        return fpeek$raw();
    }

    public T top$opt() {
        return fpeek$opt();
    }

    public T top$exn() {
        return fpeek$raw();
    }


    public T deque$raw() {
        return fdeque$raw();
    }

    public T deque$opt() {
        return fdeque$opt();
    }

    public T deque$exn() {
        return fdeque$exn();
    }


     public void enque$raw(T itm) {
        renque$raw(itm);
    }

    public void enque$exn(T itm) throws MyQueueFullExn {
        renque$raw(itm);
    }

    public boolean enque$opt(T itm) {
        renque$raw(itm);
        return true;
    }


     public void System$out$print() {
        System.out.print("DequeList(");
        Node current = head;
        while (current != null) {
            System.out.print(current.value);
            current = current.next;
            if (current != null) {System.out.print(", ");}
        }
        System.out.println(")");
    }


    public void foritm(Consumer<? super T> action) {
        Node current = head;
        while (current != null) {
            action.accept(current.value);
            current = current.next;
        }
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
        Node current = head;
        int index = 0;
        while (current != null) {
            action.accept(index, current.value);
            current = current.next;
            index ++;
        }
    }

    public void rforitm(Consumer<? super T> action) {
        Node current = tail;
        while (current != null) {
            action.accept(current.value);
            current = current.prev;
        }
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
        Node current = tail;
        int index = nitm - 1;
        while (current != null) {
            action.accept(index, current.value);
            current = current.prev;
            index --;
        }
    }
}